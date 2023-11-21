package com.omerguzel.pokedex.ui.detail

import com.omerguzel.pokedex.R

enum class StatType(val stringRes: Int) {
    HP(R.string.hp),
    ATTACK(R.string.atk),
    DEFENSE(R.string.def),
    SPECIAL_ATTACK(R.string.satk),
    SPECIAL_DEFENSE(R.string.sdef),
    SPEED(R.string.spd);

    companion object {
        fun fromString(statName: String): StatType? {
            return when (statName) {
                "hp" -> HP
                "attack" -> ATTACK
                "defense" -> DEFENSE
                "special-attack" -> SPECIAL_ATTACK
                "special-defense" -> SPECIAL_DEFENSE
                "speed" -> SPEED
                else -> null
            }
        }
    }
}
