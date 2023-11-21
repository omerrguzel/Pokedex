package com.omerguzel.pokedex.domain.mapper

import com.omerguzel.pokedex.data.remote.network.response.Pokemon
import com.omerguzel.pokedex.data.remote.network.response.PokemonList
import com.omerguzel.pokedex.domain.model.PokemonUIItem
import com.omerguzel.pokedex.domain.model.PokemonUIList
import javax.inject.Inject

class PokemonMapper @Inject constructor() {

    private fun mapToPokemonUIItem(pokemon: Pokemon): PokemonUIItem {
        return PokemonUIItem(
            id = pokemon.id,
            name = pokemon.name,
            abilities = pokemon.abilities?.map { it.ability?.name } ?: listOf(),
            weight = pokemon.weight,
            height = pokemon.height,
            image = pokemon.sprites?.other?.officialArtwork?.frontDefault,
            stats = pokemon.stats?.map { stat -> Pair(stat.stat?.name, stat.baseStat) },
            speciesUrl = pokemon.species?.url,
            types = pokemon.types?.map { it.type?.name } ?: listOf()
        )
    }

    fun mapToPokemonUIList(pokemonList: PokemonList,pokemonDetails: List<Pokemon>): PokemonUIList {
        val pokemonUIItems = pokemonDetails.map { mapToPokemonUIItem(it) }
        return PokemonUIList(
            count = pokemonList.count,
            next = pokemonList.next,
            previous = pokemonList.previous,
            results = pokemonUIItems
        )
    }
}
