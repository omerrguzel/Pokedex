package com.omerguzel.pokedex.extensions

fun String?.capitalizeFirstLetter(): String? {
    return this?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

fun String.replaceSpecialCharactersWithSpace(): String {
    return this.replace(Regex("\\s+"), " ")
}

