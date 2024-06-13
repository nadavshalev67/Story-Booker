package com.nadavshalev67.storybooker.thumbnails

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ThumbnailStyle(
    val readThumbnailsColor: List<Color> = listOf(Color(0xFFB0B0B0), Color(0xFF808080)),
    val unreadThumbnailsColor: List<Color> = listOf(Color(0xFFFFDC80), Color(0xFFC13584)),
    val thumbnailCircleSize: Dp = 90.dp,
    val borderSize: Dp = 3.dp,
    val displayShimmer: Boolean = true,
    val textSize: TextUnit = 13.sp,
    val fontWeight: FontWeight = FontWeight.Normal,
)