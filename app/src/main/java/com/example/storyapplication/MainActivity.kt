package com.example.storyapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.storyapplication.presentation.PokemonItemContent
import com.example.storyapplication.ui.theme.StoryApplicationTheme
import com.nadavshalev67.storybooker.pages.StoryGallery
import com.nadavshalev67.storybooker.pages.StoryGalleryData
import com.nadavshalev67.storybooker.thumbnails.ThumbnailGallery
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoryApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val viewModel: PokemonViewModel = koinViewModel()
                    val storyUiState by viewModel.uiState.collectAsState()
                    val contentThumbnail by viewModel.pokemonsThumbnailFlow.collectAsState()
                    val contentItems by viewModel.pokemonsItemsFlow.collectAsState()

                    BackHandler(enabled = storyUiState.displayView is DisplayView.Story) {
                        viewModel.showThumbnails()
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        when (val view = storyUiState.displayView) {
                            is DisplayView.Story -> {
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                    StoryGallery(
                                        modifier = Modifier
                                            .padding(it)
                                            .background(Color.Black),
                                        storyGalleryData = StoryGalleryData(
                                            pages = contentItems,
                                            initialPage = view.index,
                                            progressBarDurationMillis = 2000,
                                        ),
                                        storyEvents = viewModel.storyEventsListener
                                    ) { page, storyInnerData ->
                                        PokemonItemContent(
                                            item = storyInnerData.data,
                                            onLoaded = {
                                                viewModel.updateItemLoaded(
                                                    pokemonItem = storyInnerData.data,
                                                )
                                            }
                                        )
                                    }
                                }
                            }

                            DisplayView.Thumbnails -> {
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                    ThumbnailGallery(
                                        modifier = Modifier.padding(
                                            top = 10.dp
                                        ),
                                        thumbnailItems = contentThumbnail,
                                        onThumbnailClick = { index, _ ->
                                            viewModel.onThumbnailClicked(
                                                index = index,
                                            )
                                        },
                                    )
                                    HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}