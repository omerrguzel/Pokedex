package com.omerguzel.pokedex.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.omerguzel.pokedex.ui.base.NavOptionsBuilder
import com.omerguzel.pokedex.ui.base.NavigationListener
import com.omerguzel.pokedex.ui.base.navigatePopBackStack
import com.omerguzel.pokedex.ui.base.navigateSafe

abstract class BaseActivity : AppCompatActivity(), NavigationListener {
    abstract val navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun navigate(directions: NavDirections) {
        navController.navigateSafe(directions = directions)
    }

    override fun navigateHome() {
        navController.navigate(
            navController.graph.startDestinationId,
            null,
            NavOptionsBuilder.build()
        )
    }

    override fun navigatePopBackStack(directions: NavDirections, @IdRes destinationId: Int) {
        navController.navigatePopBackStack(directions, destinationId)
    }

    override fun navigateBack() {
        navController.navigateUp()
    }
}
