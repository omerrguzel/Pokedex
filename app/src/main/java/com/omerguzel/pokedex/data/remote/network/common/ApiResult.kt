package com.omerguzel.pokedex.data.remote.network.common

import androidx.annotation.Keep
import okhttp3.Headers
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Keep
sealed class NetworkResult<out T> {
    data class Success<T>(val response: T?, val headers: Headers? = null) : NetworkResult<T>()
    data class Failure<T>(val error: ServiceError, val response: T?) : NetworkResult<T>()
}

@Keep
abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}

@Keep
class NetworkResultCall<T>(proxy: Call<T>) :
    CallDelegate<T, NetworkResult<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<NetworkResult<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val headers = response.headers()
                val result = if (response.isSuccessful) {
                    NetworkResult.Success(body, headers)
                } else {
                    NetworkResult.Failure(ServiceError.fromResponse(response), body)
                }
                callback.onResponse(this@NetworkResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = NetworkResult.Failure(ServiceError.fromException(t), null)
                callback.onResponse(this@NetworkResultCall, Response.success(result))
            }
        })

    override fun cloneImpl() = NetworkResultCall(proxy.clone())
    override fun timeout(): Timeout = proxy.timeout()
}

@Keep
class NetworkResultAdapter(
    private val type: Type
) : CallAdapter<Type, Call<NetworkResult<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<NetworkResult<Type>> =
        NetworkResultCall(call)
}

@Keep
class NetworkCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                NetworkResult::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    NetworkResultAdapter(resultType)
                }

                else -> {
                    throw IllegalStateException(
                        "NetworkResult must have generic type (e.g., NetworkResult<ResponseBody>)"
                    )
                }
            }
        }

        else -> null
    }
}
