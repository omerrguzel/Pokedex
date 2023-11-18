package com.omerguzel.pokedex.ui.base

import androidx.lifecycle.ViewModel
import com.omerguzel.pokedex.data.remote.network.common.NetworkServiceErrorResolver
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var networkServiceErrorResolver: NetworkServiceErrorResolver
}
