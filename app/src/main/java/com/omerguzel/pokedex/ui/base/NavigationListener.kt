package com.omerguzel.pokedex.ui.base

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.omerguzel.pokedex.R

interface NavigationListener {

    fun navigate(directions: NavDirections)

    fun onBackPressed()

    fun navigateHome()

    fun navigateBack()

    fun navigatePopBackStack(directions: NavDirections, @IdRes destinationId: Int)
}

fun NavController.navigateSafe(directions: NavDirections, onError: (() -> Unit)? = null) {
    try {
        navigate(directions, NavOptionsBuilder.build())
    } catch (exception: IllegalArgumentException) {
        onError?.invoke()
    }
}

fun NavController.navigatePopBackStack(
    directions: NavDirections,
    @IdRes destinationId: Int,
    onError: (() -> Unit)? = null
) {
    try {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(destinationId, true)
            .build()
        navigate(directions, navOptions)
    } catch (exception: IllegalArgumentException) {
        onError?.invoke()
    }
}

internal val NavOptionsBuilder = NavOptions.Builder().apply {
    setEnterAnim(R.anim.slide_in)
    setExitAnim(R.anim.fade_out)
    setPopEnterAnim(R.anim.fade_in)
    setPopExitAnim(R.anim.slide_out)
}
