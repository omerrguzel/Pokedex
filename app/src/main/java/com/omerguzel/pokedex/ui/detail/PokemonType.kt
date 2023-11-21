package com.omerguzel.pokedex.ui.detail

import com.omerguzel.pokedex.R

enum class PokemonType(val colorRes: Int) {
    BUG(R.color.bug),
    DARK(R.color.dark),
    DRAGON(R.color.dragon),
    ELECTRIC(R.color.electric),
    FAIRY(R.color.fairy),
    FIGHTING(R.color.fighting),
    FIRE(R.color.fire),
    FLYING(R.color.flying),
    GHOST(R.color.ghost),
    NORMAL(R.color.normal),
    GRASS(R.color.grass),
    GROUND(R.color.ground),
    ICE(R.color.ice),
    POISON(R.color.poison),
    PSYCHIC(R.color.psychic),
    ROCK(R.color.rock),
    STEEL(R.color.steel),
    WATER(R.color.water);

    companion object {
        fun fromString(typeName: String): PokemonType? {
            return try {
                valueOf(typeName.uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}

