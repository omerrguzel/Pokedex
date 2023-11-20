package com.omerguzel.pokedex.data.remote.repository

import com.omerguzel.pokedex.data.remote.network.PokeApi
import com.omerguzel.pokedex.data.remote.network.common.NetworkResult
import com.omerguzel.pokedex.data.remote.network.response.Pokemon
import com.omerguzel.pokedex.data.remote.network.response.PokemonList

class PokemonRepositoryImpl(
    private val pokeApi: PokeApi
) : PokemonRepository {
    override suspend fun fetchPokemonList(limit: Int, offset: Int): NetworkResult<PokemonList> {
        return pokeApi.fetchPokemonList(limit = limit, offset = offset)
    }

    override suspend fun fetchPokemonInfo(url: String): NetworkResult<Pokemon> {
        return pokeApi.fetchPokemonInfo(url = url)
    }
}
