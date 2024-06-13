package com.nadavshalev67.storybooker.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarRow(
    items: List<LoadingData>,
    currentPage: Int,
    fillPercentage: Float,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(48.dp)
            .padding(24.dp, 0.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (index in items.indices) {
            SingleProgressBar(
                modifier = Modifier.weight(1f),
                fillPercentage = when (index) {
                    currentPage -> fillPercentage
                    in 0..currentPage -> 1f
                    else -> 0f
                },
            )
        }
    }
}

