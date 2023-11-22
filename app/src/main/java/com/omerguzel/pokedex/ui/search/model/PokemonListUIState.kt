package com.omerguzel.pokedex.ui.search.model

import com.omerguzel.pokedex.domain.model.PokemonUIList

sealed class PokemonListUIState {
    object Loading : PokemonListUIState()
    data class Success(val data: PokemonUIList) : PokemonListUIState()
    data class Error(val message: String) : PokemonListUIState()
}
