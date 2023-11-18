package com.omerguzel.pokedex.data.remote.network.response

data class Ability(
    val ability: AbilityX? = AbilityX(),
    val is_hidden: Boolean? = false,
    val slot: Int? = 0
)
