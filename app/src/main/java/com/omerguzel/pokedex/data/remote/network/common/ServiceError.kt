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
        fun fromException(t: Throwable): ServiceError {
            return when (t) {
                is UnknownHostException, is ConnectException, is SocketException -> Default(BaseServiceError.CONNECTION, message = t.message)
                is TimeoutException, is SocketTimeoutException -> Default(BaseServiceError.TIMEOUT, message = t.message)
                is JsonSyntaxException -> Default(BaseServiceError.CORRUPTED_DATA, message = t.message)
                else -> Default(BaseServiceError.UNKNOWN, message = t.message)
            }
        }
    }
}

