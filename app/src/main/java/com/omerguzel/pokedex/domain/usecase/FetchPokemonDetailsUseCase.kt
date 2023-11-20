package com.omerguzel.pokedex.domain.usecase

import com.omerguzel.pokedex.data.remote.network.common.CompositeException
import com.omerguzel.pokedex.data.remote.network.common.NetworkResult
import com.omerguzel.pokedex.data.remote.network.common.NetworkServiceErrorResolver
import com.omerguzel.pokedex.data.remote.network.common.ServiceError
import com.omerguzel.pokedex.data.remote.network.response.Pokemon
import com.omerguzel.pokedex.data.remote.network.response.PokemonList
import com.omerguzel.pokedex.data.remote.repository.PokemonRepository
import com.omerguzel.pokedex.domain.mapper.PokemonMapper
import com.omerguzel.pokedex.domain.model.PokemonUIList
import com.omerguzel.pokedex.util.AggregatedResource
import javax.inject.Inject

class FetchPokemonDetailsUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val pokemonMapper: PokemonMapper
) {
    suspend operator fun invoke(pokemonList: PokemonList): AggregatedResource<PokemonUIList> {
        val pokemonItemList = mutableListOf<Pokemon>()
        val errors = mutableListOf<ServiceError>()

        pokemonList.results?.forEach { result ->
            result?.url?.let { url ->
                when (val response = pokemonRepository.fetchPokemonInfo(url)) {
                    is NetworkResult.Success -> response.response?.let { pokemonItemList.add(it) }
                    is NetworkResult.Failure -> errors.add(response.error)
                }
            }
        }

        val pokemonUIList = pokemonMapper.mapToPokemonUIList(pokemonList,pokemonItemList)

        return when {
            errors.isEmpty() -> AggregatedResource.Success(pokemonUIList)
            pokemonUIList.results?.isNotEmpty() == true -> AggregatedResource.PartialSuccess(pokemonUIList, errors)
            else -> AggregatedResource.Error("Unknown error occurred")
        }
    }
}

