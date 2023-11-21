package com.omerguzel.pokedex.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import tr.com.allianz.digitall.extensions.viewbinding.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val binding by viewBinding(ActivityMainBinding::inflate)

    override val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
