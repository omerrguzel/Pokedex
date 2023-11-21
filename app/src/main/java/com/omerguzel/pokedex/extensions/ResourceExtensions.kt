package com.omerguzel.pokedex.extensions

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun Float.dpFromPx(context: Context): Float {
    return this / context.resources.displayMetrics.density
}

fun Float.pxFromDp(context: Context): Float {
    return this * context.resources.displayMetrics.density
}

fun Context.color(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.colorWithReducedAlpha(@ColorRes colorRes: Int): Int {
    val originalColor = ContextCompat.getColor(this, colorRes)
    val reducedAlpha = (Color.alpha(originalColor) * 0.25).toInt()
    return Color.argb(reducedAlpha, Color.red(originalColor), Color.green(originalColor), Color.blue(originalColor))
}

fun Context.drawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)
fun Context.font(@FontRes id: Int) = ResourcesCompat.getFont(this, id)
