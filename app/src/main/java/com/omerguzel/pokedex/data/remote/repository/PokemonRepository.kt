package com.omerguzel.pokedex.data.remote.repository

import com.omerguzel.pokedex.data.remote.network.common.NetworkResult
import com.omerguzel.pokedex.data.remote.network.response.PokemonList

interface PokemonRepository {

    suspend fun fetchPokemonList(limit:Int, offset:Int) : NetworkResult<PokemonList>
}
