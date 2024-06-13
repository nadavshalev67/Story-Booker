package com.nadavshalev67.storybooker.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.nadavshalev67.storybooker.events.StoryEvents
import com.nadavshalev67.storybooker.tranistions.pagerCubeOutRotationTransition
import kotlinx.coroutines.launch

data class StoryGalleryData<T : LoadingData>(
    val pages: Map<Int, List<T>>,
    val progressBarDurationMillis: Int = 5000,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : LoadingData> StoryGallery(
    modifier: Modifier = Modifier,
    storyGalleryData: StoryGalleryData<T>,
    initialPage: Int = 0,
    storyEvents: StoryEvents? = null,
    content: @Composable (Int, StoryInnerData<T>) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { storyGalleryData.pages.size },
        initialPage = initialPage,
    )

    var lastSettledPage by remember { mutableIntStateOf(-1) }
    val isTransitioning by remember { derivedStateOf { pagerState.isScrollInProgress } }

    LaunchedEffect(isTransitioning) {
        if (!isTransitioning && lastSettledPage != pagerState.currentPage) {
            lastSettledPage = pagerState.currentPage
            storyEvents?.onOuterPageImpression(
                outerPageNumber = pagerState.currentPage,
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        beyondBoundsPageCount = 2,
        modifier = modifier,
    ) { currentPage ->
        Box(
            Modifier
                .pagerCubeOutRotationTransition(currentPage, pagerState)
                .fillMaxSize()
        ) {
            StorySinglePage(
                progressBarDurationMillis = storyGalleryData.progressBarDurationMillis,
                items = storyGalleryData.pages.getValue(currentPage),
                onNextPageRequested = {
                    coroutineScope.launch {
                        if (pagerState.currentPage == pagerState.pageCount - 1) {
                            storyEvents?.onComplete()
                        } else {
                            pagerState.animateScrollToPage(
                                currentPage + 1,
                            )
                        }
                    }
                },
                onPrevPageRequested = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            currentPage - 1,
                        )
                    }
                },
                pageNumber = currentPage,
                outerPagerState = pagerState,
                storyEvents = storyEvents,
            ) { innerData ->
                content(currentPage, innerData)
            }
        }
    }
}
