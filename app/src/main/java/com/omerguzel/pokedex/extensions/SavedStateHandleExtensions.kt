package com.omerguzel.pokedex.extensions

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getOrThrow(argKey: String, exception: Throwable = IllegalArgumentException()): T {
    return get<T>(argKey) ?: throw exception
}
