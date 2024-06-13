package com.nadavshalev67.storybooker.thumbnails

data class ThumbnailData(
    val imageSource: ImageSource,
    val text: String? = null,
    val isViewedAll: Boolean = false,
)