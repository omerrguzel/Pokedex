package com.omerguzel.pokedex.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.omerguzel.pokedex.R

fun ImageView.showImage(imgUrl : String?){
    Glide.with(context)
        .load("$imgUrl")
        .apply (
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.baseline_broken_image_24)
        )
        .into(this)
}
