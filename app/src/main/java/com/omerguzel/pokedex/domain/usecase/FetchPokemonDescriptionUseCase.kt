package com.omerguzel.pokedex.domain.usecase

import com.omerguzel.pokedex.data.remote.network.common.NetworkResult
import com.omerguzel.pokedex.data.remote.network.common.NetworkServiceErrorResolver
import com.omerguzel.pokedex.data.remote.network.response.Species
import com.omerguzel.pokedex.data.remote.repository.PokemonRepository
import com.omerguzel.pokedex.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FetchPokemonDescriptionUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val networkServiceErrorResolver: NetworkServiceErrorResolver
) {
    suspend operator fun invoke(url: String): Flow<Resource<Species?>> = flow {
        emit(Resource.Loading)

        val result = pokemonRepository.fetchPokemonDescription(url)

        emit(
            when (result) {
                is NetworkResult.Failure -> {
                    Resource.Error(networkServiceErrorResolver.resolveNetworkFailure(result))
                }

                is NetworkResult.Success -> {
                    Resource.Success(result.response)
                }
            }
        )
    }
}
