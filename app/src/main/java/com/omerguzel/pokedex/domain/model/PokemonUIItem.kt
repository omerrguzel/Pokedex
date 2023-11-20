package com.omerguzel.pokedex.domain.model

data class PokemonUIItem(
    val id: Int? = 0,
    val name: String? = "",
    val abilities: List<String> = listOf(),
    val weight: Int? = 0,
    val height: Int? = 0,
    val image: String? = null,
    val stats: List<Pair<String?, Int?>>? = null,
    val speciesUrl: String? = null
)
