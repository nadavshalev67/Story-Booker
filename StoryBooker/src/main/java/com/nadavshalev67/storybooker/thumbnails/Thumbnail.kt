package com.nadavshalev67.storybooker.thumbnails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.nadavshalev67.storybooker.effects.shimmerEffect


@Composable
fun Thumbnail(
    thumbnailData: ThumbnailData,
    onThumbnailClick: () -> Unit,
    thumbnailStyle: ThumbnailStyle,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val imageModifier = Modifier
            .size(thumbnailStyle.thumbnailCircleSize)
            .border(
                width = thumbnailStyle.borderSize,
                brush = Brush.linearGradient(
                    if (thumbnailData.isViewedAll) {
                        thumbnailStyle.readThumbnailsColor
                    } else {
                        thumbnailStyle.unreadThumbnailsColor
                    },
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 80f)
                ),
                shape = CircleShape
            )
            .padding(7.dp)
            .clip(CircleShape)
            .clickable(onClick = onThumbnailClick)
        when (val source = thumbnailData.imageSource) {
            is ImageSource.Url -> {
                SubcomposeAsyncImage(
                    model = source.url,
                    contentDescription = null,
                    modifier = imageModifier,
                    contentScale = source.contentScale,
                ) {
                    val state = painter.state
                    if (thumbnailStyle.displayShimmer && (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error)) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .shimmerEffect(),
                        )
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
            }

            is ImageSource.DrawableRes -> {
                Image(
                    painter = painterResource(id = source.resId),
                    contentDescription = null,
                    modifier = imageModifier,
                    contentScale = source.contentScale,
                )
            }

            is ImageSource.SolidColor -> {
                Box(
                    modifier = imageModifier.background(
                        source.color
                    ),
                )
            }
        }
        thumbnailData.text?.let {
            Text(
                text = thumbnailData.text,
                fontWeight = thumbnailStyle.fontWeight,
                fontSize = thumbnailStyle.textSize,
            )
        }
    }
}