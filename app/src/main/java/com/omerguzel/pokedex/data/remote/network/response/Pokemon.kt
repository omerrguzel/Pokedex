package com.omerguzel.pokedex.data.remote.network.response

data class Pokemon(
    val abilities: List<Ability>? = listOf(),
    val base_experience: Int? = 0,
    val forms: List<Form>? = listOf(),
    val game_indices: List<GameIndice>? = listOf(),
    val height: Int? = 0,
    val held_items: List<HeldItem>? = listOf(),
    val id: Int? = 0,
    val is_default: Boolean? = false,
    val location_area_encounters: String? = "",
    val moves: List<Move>? = listOf(),
    val name: String? = "",
    val order: Int? = 0,
    val past_abilities: List<Any>? = listOf(),
    val past_types: List<Any>? = listOf(),
    val species: Species? = Species(),
    val stats: List<Stat>? = listOf(),
    val types: List<Type>? = listOf(),
    val weight: Int? = 0
)
