package com.nadavshalev67.storybooker.thumbnails

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

sealed class ImageSource {
    data class Url(val url: String, val contentScale: ContentScale = ContentScale.Crop) :
        ImageSource()

    data class DrawableRes(val resId: Int, val contentScale: ContentScale = ContentScale.Crop) :
        ImageSource()

    data class SolidColor(val color: Color) : ImageSource()
}