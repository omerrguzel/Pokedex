package com.omerguzel.pokedex.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Ability(
    val ability: AbilityItem? = AbilityItem(),
    @SerializedName("is_hidden")
    val isHidden: Boolean? = false,
    val slot: Int? = 0
)
