package com.omerguzel.pokedex.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int? = 0,
    val effort: Int? = 0,
    val stat: StatItem? = StatItem()
)
