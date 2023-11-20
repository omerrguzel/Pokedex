package com.omerguzel.pokedex.domain.model

data class PokemonUIList(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonUIItem?>? = null
)
