package com.omerguzel.pokedex.extensions

import android.content.Context
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
fun Context.drawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)
fun Context.font(@FontRes id: Int) = ResourcesCompat.getFont(this, id)
