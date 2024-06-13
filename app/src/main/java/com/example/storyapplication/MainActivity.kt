package com.example.storyapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.storyapplication.ui.theme.StoryApplicationTheme
import com.nadavshalev67.storybooker.events.StoryEvents
import com.nadavshalev67.storybooker.pages.LoadingData
import com.nadavshalev67.storybooker.pages.StoryGallery
import com.nadavshalev67.storybooker.pages.StoryGalleryData
import com.nadavshalev67.storybooker.thumbnails.ImageSource
import com.nadavshalev67.storybooker.thumbnails.ThumbnailData
import com.nadavshalev67.storybooker.thumbnails.ThumbnailGallery

const val listSize = 30

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoryApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    var isThumbnailClicked by remember {
                        mutableStateOf(false)
                    }

                    val thumbnailItems = createThumbnailsMockData()
                    BackHandler(enabled = isThumbnailClicked) {
                        isThumbnailClicked = false
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        val storyPageList = remember {
                            mutableStateMapOf(
                                0 to createMapStoriesMockData(),
                                1 to createMapStoriesMockData(),
                                2 to createMapStoriesMockData(),
                            )
                        }

                        if (isThumbnailClicked) {
                            StoryGallery(
                                modifier = Modifier.padding(it),
                                storyGalleryData = StoryGalleryData(
                                    pages = storyPageList,
                                    progressBarDurationMillis = 2000

                                ),
                                storyEvents = object : StoryEvents() {
                                    override fun onOuterPageImpression(outerPageNumber: Int) {
                                        Log.d("nadav", "onOuterPageImpression - [$outerPageNumber]")
                                    }

                                    override fun onInnerPageImpression(
                                        outerPageNumber: Int,
                                        innerPageNumber: Int,
                                    ) {
                                        Log.d(
                                            "nadav",
                                            "onInnerPageImpression - [$outerPageNumber-${innerPageNumber}]"
                                        )
                                    }

                                    override fun onComplete() {
                                        Log.d(
                                            "nadav",
                                            "onComplete"
                                        )
                                        isThumbnailClicked = false
                                    }
                                }
                            ) { page, storyData ->
                                StoryItemContent(storyData.data) {
                                    val currentPageList = storyPageList[page]
                                    if (currentPageList != null) {
                                        val element = currentPageList.getOrNull(storyData.innerPage)
                                        if (element != null) {
                                            storyPageList[page] = currentPageList.toMutableList()
                                                .apply {
                                                    set(
                                                        storyData.innerPage,
                                                        element.copy(isLoaded = true)
                                                    )
                                                }
                                        }
                                    }
                                }
                            }
                        } else {
                            ThumbnailGallery(
                                modifier = Modifier.padding(
                                    top = 10.dp
                                ),
                                thumbnailItems = thumbnailItems,
                                onThumbnailClick = { index, item ->
                                    isThumbnailClicked = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }


    private fun createMapStoriesMockData(): List<StoryItem> {
        return listOf(
            StoryItem(url = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8UGVvcGxlfGVufDB8fDB8fHww"),
            StoryItem(url = "https://i.pinimg.com/originals/07/b2/40/07b240561cb656aec289df602e603bee.png"),
            StoryItem(url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTyxblgZC7_8xfyJ4ttyw0kvzcDqLsu4qIf7Q14QY7zMQGie3B1QJKFByOExQjdxRtxgg&usqp=CAU"),
        )

    }

    private fun createThumbnailsMockData(): List<ThumbnailData> {
        return List(2) { index ->
            ThumbnailData(
                imageSource = ImageSource.DrawableRes(
                    when (index % 3) { // Example logic to cycle through 3 different drawable resources
                        0 -> R.drawable.first
                        1 -> R.drawable.second
                        2 -> R.drawable.third
                        else -> R.drawable.first
                    }
                ),
                text = "Image $index"
            )
        }.union(
            List(2) { index ->
                ThumbnailData(
                    imageSource = ImageSource.Url(
                        url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTyxblgZC7_8xfyJ4ttyw0kvzcDqLsu4qIf7Q14QY7zMQGie3B1QJKFByOExQjdxRtxgg&usqp=CAU",
                        contentScale = ContentScale.Fit,
                    ),
                    text = "Image $index"
                )
            }
        ).toList()
    }
}


data class StoryItem(
    val url: String,
    override val isLoaded: Boolean = false,
) : LoadingData

@Composable
fun StoryItemContent(
    item: StoryItem,
    onLoaded: () -> Unit,
) {
    SubcomposeAsyncImage(
        model = item.url,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
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


