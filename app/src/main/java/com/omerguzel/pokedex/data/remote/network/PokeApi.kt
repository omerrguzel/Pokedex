package com.omerguzel.pokedex.data.remote.network

import com.omerguzel.pokedex.data.remote.network.common.NetworkResult
import com.omerguzel.pokedex.data.remote.network.response.Pokemon
import com.omerguzel.pokedex.data.remote.network.response.PokemonList
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeApi {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit:Int,
        @Query("offset") offset:Int
    ) : NetworkResult<PokemonList>

    @GET
    suspend fun fetchPokemonInfo(
        @Url url: String
    ) : NetworkResult<Pokemon>
}
