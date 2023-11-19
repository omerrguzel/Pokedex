package com.omerguzel.pokedex.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("other")
    val other: Other? = Other(),
)
