package com.omerguzel.pokedex.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val abilities: List<Ability>? = listOf(),
    @SerializedName("base_experience")
    val baseExperience: Int? = 0,
    val height: Int? = 0,
    val id: Int? = 0,
    @SerializedName("is_default")
    val isDefault: Boolean? = false,
    val moves: List<Move>? = listOf(),
    val name: String? = "",
    val order: Int? = 0,
    val species : SpeciesItem? =null,
    val sprites: Sprites? = Sprites(),
    val stats: List<Stat>? = listOf(),
    val types: List<Type>? = listOf(),
    val weight: Int? = 0
)
