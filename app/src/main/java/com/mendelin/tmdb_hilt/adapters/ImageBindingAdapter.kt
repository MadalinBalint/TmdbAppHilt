package com.mendelin.tmdb_hilt.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mendelin.tmdb_hilt.R

@BindingAdapter("imageUrl")
/* Binding adapter for the cat image in breeds list */
fun ImageView.setImageUrl(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 6f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        Glide.with(context)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .disallowHardwareConfig()
            )
            .load("https://image.tmdb.org/t/p/w500$imageUrl")
            .placeholder(circularProgressDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.ic_photo)
            .into(this)
    }
}