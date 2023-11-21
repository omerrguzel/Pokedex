package com.omerguzel.pokedex.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("flavor_text")
    val text : String? = ""
)
