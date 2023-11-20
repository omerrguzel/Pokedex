package com.omerguzel.pokedex.data.remote.network.response

data class PokemonList(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Result?>? = null
)
