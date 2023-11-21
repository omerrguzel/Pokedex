package com.omerguzel.pokedex.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class Event<out T>(private val content: T, private val singleCall: Boolean = false) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? = if (singleCall && hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        content
    }

    fun peekContent(): T = content
}

internal fun <T> Flow<Event<T?>>.collectLatestEvent(
    lifecycleOwner: LifecycleOwner,
    collect: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectLatest { event ->
                event.getContentIfNotHandled()?.let {
                    collect.invoke(it)
                }
            }
        }
    }
}

internal fun <T> Flow<Event<T?>>.onEachEvent(action: suspend (T) -> Unit): Flow<T> =
    transform { value ->
        value.getContentIfNotHandled()?.let {
            action(it)
            return@transform emit(it)
        }
    }

internal fun <T> Flow<T?>.collectLatest(
    lifecycleOwner: LifecycleOwner,
    collect: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectLatest {
                it?.let {
                    collect.invoke(it)
                }
            }
        }
    }
}
