package com.omerguzel.pokedex.di

import com.google.gson.Gson
import com.omerguzel.pokedex.data.remote.network.PokeApi
import com.omerguzel.pokedex.data.remote.network.common.NetworkCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        //TODO for future interceptors
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        endPoint: EndPoint,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(endPoint.url)
            .addCallAdapterFactory(NetworkCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideEndPoint(): EndPoint {
        return EndPoint("https://pokeapi.co/api/v2/")
    }

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): PokeApi {
        return retrofit.create(PokeApi::class.java)
    }
}

data class EndPoint(val url: String)

