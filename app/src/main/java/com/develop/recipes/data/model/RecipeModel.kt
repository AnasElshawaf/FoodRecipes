package com.develop.recipes.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.develop.recipes.R
import java.io.Serializable

data class RecipeModel(
    val calories: String,
    val carbos: String,
    val description: String,
    val difficulty: Int,
    val fats: String,
    val headline: String,
    val id: String,
    val image: String,
    val name: String,
    val proteins: String,
    val thumb: String,
    val time: String
) : Serializable

@BindingAdapter("loadimage")
fun setImage(image: ImageView, url: String) {
    if (!url.isNullOrEmpty()){
        Glide.with(image.context).load(url).centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(image)
    }
}
