package com.omerguzel.pokedex.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.omerguzel.pokedex.data.remote.network.response.Species
import com.omerguzel.pokedex.domain.model.PokemonUIItem
import com.omerguzel.pokedex.domain.usecase.FetchPokemonDescriptionUseCase
import com.omerguzel.pokedex.extensions.getOrThrow
import com.omerguzel.pokedex.ui.base.BaseViewModel
import com.omerguzel.pokedex.util.Event
import com.omerguzel.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchPokemonDescriptionUseCase: FetchPokemonDescriptionUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val selectedPokemonItem =
        savedStateHandle.getOrThrow<PokemonUIItem>(ARG_DETAIL_POKEMON_ITEM)

    private val _pokemonDescriptionState = MutableStateFlow<Event<Resource<Species?>?>>(Event(null))
    val pokemonDescription = _pokemonDescriptionState.asStateFlow()

    init {
        selectedPokemonItem.speciesUrl?.let { url ->
            fetchPokemonDescription(url)
        }
    }

    private fun fetchPokemonDescription(url: String) {
        viewModelScope.launch {
            fetchPokemonDescriptionUseCase(url).collect { resource ->
                _pokemonDescriptionState.emit(Event(resource))
                when (resource) {
                    is Resource.Error -> {
                        //TODO
                    }

                    is Resource.Loading -> {
                        Unit
                    }

                    is Resource.Success -> {
                        Log.d("mylog","DescVM ${resource.data?.descriptionEntries?.get(0)?.text}")
                        _pokemonDescriptionState.emit(
                            Event(resource)
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val ARG_DETAIL_POKEMON_ITEM = "pokemonItem"
    }
}
