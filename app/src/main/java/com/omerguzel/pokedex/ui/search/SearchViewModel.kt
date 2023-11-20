package com.omerguzel.pokedex.ui.search

import androidx.lifecycle.viewModelScope
import com.omerguzel.pokedex.data.remote.network.response.Pokemon
import com.omerguzel.pokedex.data.remote.network.response.PokemonList
import com.omerguzel.pokedex.domain.model.PokemonUIList
import com.omerguzel.pokedex.domain.usecase.FetchPokemonDetailsUseCase
import com.omerguzel.pokedex.domain.usecase.FetchPokemonListUseCase
import com.omerguzel.pokedex.ui.base.BaseViewModel
import com.omerguzel.pokedex.util.AggregatedResource
import com.omerguzel.pokedex.util.Event
import com.omerguzel.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fetchPokemonListUseCase: FetchPokemonListUseCase,
    private val fetchPokemonDetailsUseCase: FetchPokemonDetailsUseCase
) : BaseViewModel() {


    private val _pokemonListState =
        MutableStateFlow<Event<Resource<PokemonList?>?>>(Event(null))
    val pokemonListState = _pokemonListState.asStateFlow()

    private val _pokemonDetailsState =
        MutableStateFlow<Event<AggregatedResource<PokemonUIList>?>>(Event(null))
    val pokemonDetailsState = _pokemonDetailsState.asStateFlow()


    init {
        fetchPokemonList(20, 0)
    }

    private fun fetchPokemonList(limit: Int, offset: Int) {
        viewModelScope.launch {
            fetchPokemonListUseCase(limit, offset).collect { resource ->
                _pokemonListState.emit(Event(resource))
                if (resource is Resource.Success && resource.data != null) {
                    fetchPokemonDetails(resource.data)
                }
            }
        }
    }

    private fun fetchPokemonDetails(pokemonList: PokemonList) {
        viewModelScope.launch {
            val result = fetchPokemonDetailsUseCase(pokemonList)
            _pokemonDetailsState.emit(Event(result))
        }
    }
}
