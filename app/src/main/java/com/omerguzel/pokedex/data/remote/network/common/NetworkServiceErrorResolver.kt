package com.omerguzel.pokedex.data.remote.network.common

import android.content.Context
import com.omerguzel.pokedex.R
import javax.inject.Inject


class NetworkServiceErrorResolver @Inject constructor(
    private val context: Context,
) {

    internal fun resolveNetworkFailure(
        failure: NetworkResult.Failure<*>,
    ): String {
        return when (failure.error) {
            is ServiceError.Default -> when (failure.error.reason) {
                BaseServiceError.UNKNOWN,
                BaseServiceError.MISSING_PARAMETER,
                BaseServiceError.CORRUPTED_DATA,
                BaseServiceError.TIMEOUT,
                BaseServiceError.PRECONDITION_FAILED,
                BaseServiceError.CONNECTION -> failure.error.message

                BaseServiceError.UNAUTHORIZED -> failure.error.message
            }
        }.takeIf { !it.isNullOrEmpty() } ?: context.getString(R.string.unknown_error)
    }
}
