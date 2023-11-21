package com.omerguzel.pokedex.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.ActivityMainBinding
import com.omerguzel.pokedex.extensions.color
import com.omerguzel.pokedex.ui.base.StatusBarColorChanger
import dagger.hilt.android.AndroidEntryPoint
import tr.com.allianz.digitall.extensions.viewbinding.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity(), StatusBarColorChanger {

    val binding by viewBinding(ActivityMainBinding::inflate)

    override val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun changeStatusBarColor(colorResId: Int) {
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = this.color(colorResId)
    }
}
