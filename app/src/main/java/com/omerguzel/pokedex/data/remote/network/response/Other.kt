package com.omerguzel.pokedex.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld? = null,
    val home: Home? = null,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork? = null
)
