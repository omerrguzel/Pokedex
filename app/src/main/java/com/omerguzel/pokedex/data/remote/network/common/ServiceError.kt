package com.omerguzel.pokedex.data.remote.network.common

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

enum class BaseServiceError {
    UNKNOWN, MISSING_PARAMETER, CORRUPTED_DATA, CONNECTION,
    PRECONDITION_FAILED, TIMEOUT, UNAUTHORIZED
}

sealed class ServiceError(
    open val message: String? = null,
    open val code: String? = null
) {
    data class Default(
        val reason: BaseServiceError,
        override val message: String? = null,
        override val code: String? = null
    ) : ServiceError(message, code)

    companion object {
        fun fromException(t: Throwable): ServiceError = when (t) {
            is UnknownHostException, is ConnectException, is SocketException -> Default(BaseServiceError.CONNECTION)
            is TimeoutException, is SocketTimeoutException -> Default(BaseServiceError.TIMEOUT)
            is JsonSyntaxException -> Default(BaseServiceError.CORRUPTED_DATA)
            else -> Default(BaseServiceError.UNKNOWN)
        }

        fun <T> fromResponse(response: Response<T>): ServiceError {
            return if (response.isSuccessful) {
                Default(BaseServiceError.CORRUPTED_DATA)
            } else {
                val errorMessage = response.errorBody()?.string()
                val reason = when (response.code()) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> BaseServiceError.UNAUTHORIZED
                    HttpURLConnection.HTTP_PRECON_FAILED -> BaseServiceError.PRECONDITION_FAILED
                    else -> BaseServiceError.CORRUPTED_DATA
                }
                Default(reason, errorMessage)
            }
        }
    }
}
