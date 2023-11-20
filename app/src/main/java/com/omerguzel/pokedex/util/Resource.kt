package com.omerguzel.pokedex.util

import com.omerguzel.pokedex.data.remote.network.common.ServiceError

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(
        val message: String
    ) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

sealed class AggregatedResource<out T> {
    data class Success<T>(val data: T) : AggregatedResource<T>()
    data class PartialSuccess<T>(val data: T, val partialErrors: List<ServiceError>) : AggregatedResource<T>()
    data class Error(val message: String) : AggregatedResource<Nothing>()
}

