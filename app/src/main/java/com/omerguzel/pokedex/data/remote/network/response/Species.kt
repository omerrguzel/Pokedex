package com.omerguzel.pokedex.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("flavor_text_entries")
    val descriptionEntries : List<Description?>?=null
)
