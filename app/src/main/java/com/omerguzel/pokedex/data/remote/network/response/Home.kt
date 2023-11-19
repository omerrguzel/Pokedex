package com.omerguzel.pokedex.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("front_default")
    val frontDefault: String? = null,
    @SerializedName("front_female")
    val frontFemale: Any? = null,
    @SerializedName("front_shiny")
    val frontShiny: String? = null,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: Any? = null
)
