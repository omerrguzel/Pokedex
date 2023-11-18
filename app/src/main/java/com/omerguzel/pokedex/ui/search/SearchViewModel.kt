package com.omerguzel.pokedex.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.omerguzel.pokedex.data.remote.network.common.NetworkResult
import com.omerguzel.pokedex.data.remote.repository.PokemonRepository
import com.omerguzel.pokedex.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    init {
        fetchPokemonList(20,0)
    }

    private fun fetchPokemonList(
        limit:Int,
        offset:Int
    ){
        viewModelScope.launch {
            val result = pokemonRepository.fetchPokemonList(limit,offset)
            when(result){
                is NetworkResult.Failure -> TODO()
                is NetworkResult.Success -> {
                    result.response?.let {
                        val firstItem = it.results?.get(0)
                        Log.d("mylog","${firstItem?.name}")
                    }
                }
            }
        }
    }
}
