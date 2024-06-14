package com.nadavshalev67.storybooker.thumbnails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThumbnailGallery(
    modifier: Modifier = Modifier,
    thumbnailItems: List<ThumbnailData>,
    thumbnailStyle: ThumbnailStyle = ThumbnailStyle(),
    onThumbnailClick: (Int, ThumbnailData) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(thumbnailItems) { index, item ->
            Thumbnail(
                thumbnailData = item,
                onThumbnailClick = { onThumbnailClick(index, item) },
                thumbnailStyle = thumbnailStyle
            )
        }
    }
}