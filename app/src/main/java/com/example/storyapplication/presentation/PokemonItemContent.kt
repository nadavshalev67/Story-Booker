package com.example.storyapplication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.storyapplication.room.PokemonEntity


@Composable
fun LabelText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 3.dp,
            )
        )
    }
}

@Composable
fun PokemonItemContent(
    item: PokemonEntity,
    onLoaded: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,

        ) {
        LabelText(
            modifier = Modifier.padding(
                start = 16.dp,
                bottom = 10.dp
            ),
            text = item.pokemonName,
        )
        SubcomposeAsyncImage(
            model = item.imageUrl,
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                )
            } else {
                onLoaded()
                SubcomposeAsyncImageContent()
            }
        }
    }
}