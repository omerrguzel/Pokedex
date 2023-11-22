package com.omerguzel.pokedex.extensions

import android.content.Context
import android.view.View
import android.widget.ProgressBar

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

/** Convenient method that chooses between View.visible() or View.invisible() methods */
fun View.visibleOrInvisible(show: Boolean) {
    if (show) {
        visible()
    } else {
        invisible()
    }
}

/** Convenient method that chooses between View.visible() or View.gone() methods */
fun View.visibleOrGone(show: Boolean) {
    if (show) {
        visible()
    } else {
        gone()
    }
}

fun ProgressBar.initPagingLoadingBar(context: Context) {
    val drawable = context.drawable(com.omerguzel.pokedex.R.drawable.loading_bar)
    val screenWidth = android.content.res.Resources.getSystem().displayMetrics.widthPixels
    drawable?.setBounds(0, 0, screenWidth * 2, height)
    indeterminateDrawable = drawable

    val anim = android.animation.ObjectAnimator.ofFloat(
        this,
        "translationX",
        -screenWidth.toFloat(),
        screenWidth.toFloat()
    )
    anim.duration = 800
    anim.repeatMode = android.animation.ValueAnimator.REVERSE
    anim.repeatCount = android.animation.ValueAnimator.INFINITE
    anim.start()
}

