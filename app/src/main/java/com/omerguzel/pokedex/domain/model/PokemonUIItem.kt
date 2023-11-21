package com.omerguzel.pokedex.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonUIItem(
    val id: Int? = 0,
    val name: String? = "",
    val abilities: List<String?> = listOf(),
    val weight: Int? = 0,
    val height: Int? = 0,
    val image: String? = null,
    val stats: List<Pair<String?, Int?>>? = null,
    val types: List<String?>? = listOf(),
    val speciesUrl: String? = null
) :Parcelable
