package com.omerguzel.pokedex.extensions

import android.content.Context
import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import com.omerguzel.pokedex.R

fun Int.formatWeight(context: Context): String {
    val weightInKg = this / 10.0
    val unit = context.getString(R.string.kilogram)
    return weightInKg.formatDecimal() + unit
}


fun Int.formatHeight(context: Context): String {
    val heightInMeters = this / 10.0
    val unit = context.getString(R.string.meter)
    return if (heightInMeters % 1.0 == 0.0) {
        "${heightInMeters.toInt()} $unit"
    } else {
        "${heightInMeters.formatDecimal()} $unit"
    }
}



fun Double.formatDecimal(): String {
    val symbols = DecimalFormatSymbols().apply {
        decimalSeparator = ','
    }
    val formatter = DecimalFormat("#.#", symbols)
    return formatter.format(this)
}


