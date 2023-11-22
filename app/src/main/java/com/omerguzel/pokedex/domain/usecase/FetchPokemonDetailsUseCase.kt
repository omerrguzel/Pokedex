package com.omerguzel.pokedex.domain.usecase

import com.omerguzel.pokedex.data.remote.network.common.NetworkResult
import com.omerguzel.pokedex.data.remote.network.common.ServiceError
import com.omerguzel.pokedex.data.remote.network.response.Pokemon
import com.omerguzel.pokedex.data.remote.network.response.PokemonList
import com.omerguzel.pokedex.data.remote.repository.PokemonRepository
import com.omerguzel.pokedex.domain.mapper.PokemonMapper
import com.omerguzel.pokedex.domain.model.PokemonUIList
import com.omerguzel.pokedex.util.AggregatedResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchPokemonDetailsUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val pokemonMapper: PokemonMapper
) {
    suspend operator fun invoke(pokemonList: PokemonList, filterQuery: String? = null): Flow<AggregatedResource<PokemonUIList>> = flow {
        emit(AggregatedResource.Loading)

        val filteredOrAllResults = withContext(Dispatchers.Default) {
            if (filterQuery != null) {
                pokemonList.results?.filter { it?.name?.contains(filterQuery, ignoreCase = true) == true }
            } else {
                pokemonList.results
            }
        } ?: emptyList()

        val pokemonItemList = mutableListOf<Pokemon>()
        val errors = mutableListOf<ServiceError>()

        for (result in filteredOrAllResults) {
            result?.url?.let { url ->
                when (val response = pokemonRepository.fetchPokemonInfo(url)) {
                    is NetworkResult.Success -> response.response?.let { pokemonItemList.add(it) }
                    is NetworkResult.Failure -> errors.add(response.error)
                }
            }
        }

        val pokemonUIList = pokemonMapper.mapToPokemonUIList(pokemonList.copy(results = filteredOrAllResults), pokemonItemList)

        emit(
            when {
                errors.isEmpty() -> AggregatedResource.Success(pokemonUIList)
                pokemonUIList.results?.isNotEmpty() == true -> AggregatedResource.PartialSuccess(pokemonUIList, errors)
                else -> AggregatedResource.Error("Unknown error occurred")
            }
        )
    }
}




