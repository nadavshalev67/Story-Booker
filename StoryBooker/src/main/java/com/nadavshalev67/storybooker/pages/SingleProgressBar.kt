package com.nadavshalev67.storybooker.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SingleProgressBar(
    modifier: Modifier = Modifier,
    fillPercentage: Float,
) {
    Row(
        modifier = modifier
            .height(4.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.4f))
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxHeight()
                .fillMaxWidth(fillPercentage),
        )
    }
}

@Preview
@Composable()
fun SingleProgressBarPreview() {
    SingleProgressBar(
        fillPercentage = 0.5f
    )
}