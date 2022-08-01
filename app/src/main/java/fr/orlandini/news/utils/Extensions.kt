package fr.orlandini.news.utils

import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso

object Extensions {

    /*
     This extension permet de charger une image directement dans l'imageView
     à l'aide de la librairie Picasso
     */
    fun AppCompatImageView.loadImage(urlToImage: String?) {
        if (urlToImage != null)
            Picasso.get()
                .load(urlToImage)
                .fit()
                .centerCrop()
                .into(this)
    }
}