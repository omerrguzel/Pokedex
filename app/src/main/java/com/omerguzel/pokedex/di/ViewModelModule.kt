package com.omerguzel.pokedex.di

import android.content.Context
import com.omerguzel.pokedex.data.remote.network.PokeApi
import com.omerguzel.pokedex.data.remote.network.common.NetworkServiceErrorResolver
import com.omerguzel.pokedex.data.remote.repository.PokemonRepository
import com.omerguzel.pokedex.data.remote.repository.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @ViewModelScoped
    @Provides
    fun providePokemonRepository(pokeApi: PokeApi) : PokemonRepository =
        PokemonRepositoryImpl(pokeApi=pokeApi)

    @ViewModelScoped
    @Provides
    fun provideNetworkServiceErrorResolver(@ApplicationContext context: Context) =
        NetworkServiceErrorResolver(context)
}
