package com.omerguzel.pokedex.data.remote.network.response

data class Move(
    val move: MoveX? = MoveX(),
    val version_group_details: List<VersionGroupDetail>? = listOf()
)
