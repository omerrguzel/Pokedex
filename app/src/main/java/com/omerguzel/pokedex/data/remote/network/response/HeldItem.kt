package com.omerguzel.pokedex.data.remote.network.response

data class HeldItem(
    val item: Item? = Item(),
    val version_details: List<VersionDetail>? = listOf()
)
