package com.omerguzel.pokedex.ui.base

import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections

abstract class BaseFragment(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    private val navigationListener by lazy(LazyThreadSafetyMode.NONE) {
        activity as? NavigationListener
    }

    protected fun nav(directions: NavDirections) {
        navigationListener?.navigate(directions)
    }

    protected fun navPopBackStack(directions: NavDirections, @IdRes destinationId: Int) {
        navigationListener?.navigatePopBackStack(directions, destinationId)
    }

    protected fun navBack() {
        navigationListener?.navigateBack()
    }

    protected fun navigateHome() {
        navigationListener?.navigateHome()
    }
}
