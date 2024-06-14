package com.nadavshalev67.storybooker.pages

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.Lifecycle
import com.nadavshalev67.storybooker.effects.OnLifecycleEvent
import com.nadavshalev67.storybooker.events.StoryEvents
import kotlinx.coroutines.launch

data class StoryInnerData<T : LoadingData>(
    val innerPage: Int,
    val data: T,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : LoadingData> StorySinglePage(
    modifier: Modifier = Modifier,
    pageNumber: Int,
    outerPagerState: PagerState,
    items: List<T>,
    progressBarDurationMillis: Int,
    onNextPageRequested: () -> Unit,
    onPrevPageRequested: () -> Unit,
    storyEvents: StoryEvents<T>? = null,
    content: @Composable (StoryInnerData<T>) -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = { items.size }
    )

    val isTransitioning by remember { derivedStateOf { outerPagerState.isScrollInProgress } }
    var lastNumberPageImpression by remember { mutableIntStateOf(Integer.MIN_VALUE) }


    val scope = rememberCoroutineScope()

    var state by remember {
        mutableStateOf(
            StoryPageState(
                percent = Animatable(0f),
                shouldPause = true,
                lifecycleEvent = Lifecycle.Event.ON_RESUME,
            )
        )
    }

    fun pause() {
        state = state.copy(
            shouldPause = true,
        )
    }

    fun resume() {
        state = state.copy(
            shouldPause = false,
        )
    }

    LaunchedEffect(isTransitioning) {
        if (isTransitioning) {
            pause()
        } else {
            if (pageNumber == outerPagerState.currentPage) {
                resume()
            }
        }
    }

    fun restart() {
        state = state.copy(
            percent = Animatable(0f),
            shouldPause = false,
        )
    }

    fun nextItem() {
        if (pagerState.currentPage < items.size - 1) {
            scope.launch {
                pagerState.scrollToPage(pagerState.currentPage + 1)
                state = state.copy(
                    percent = Animatable(0f)
                )
            }
        } else {
            onNextPageRequested()
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (outerPagerState.currentPage == pageNumber) {
            restart()
        } else {
            pause()
        }
    }

    LaunchedEffect(pagerState.currentPage, outerPagerState.currentPage) {
        if (outerPagerState.currentPage == pageNumber) {
            if (lastNumberPageImpression != pagerState.currentPage) {
                lastNumberPageImpression = pagerState.currentPage
                storyEvents?.onInnerPageImpression(
                    outerPageNumber = pageNumber,
                    innerPageNumber = pagerState.currentPage,
                    item = items[pagerState.currentPage],
                )
            }
        }
    }

    fun prevItem() {
        if (pagerState.currentPage > 0) {
            scope.launch {
                pagerState.scrollToPage(pagerState.currentPage - 1)
                state = state.copy(
                    percent = Animatable(0f)
                )
            }
        } else {
            onPrevPageRequested()
        }
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (state.lifecycleEvent != Lifecycle.Event.ON_RESUME) {
                    state = state.copy(
                        lifecycleEvent = Lifecycle.Event.ON_RESUME
                    )
                    resume()
                }
            }

            Lifecycle.Event.ON_PAUSE -> {
                state = state.copy(
                    lifecycleEvent = Lifecycle.Event.ON_PAUSE
                )
                pause()
            }

            else -> Unit
        }
    }

    LaunchedEffect(state.shouldPause, items[pagerState.currentPage]) {
        if (state.shouldPause || items[pagerState.currentPage].isLoaded.not()) {
            state.percent.stop()
        } else {
            val result = state.percent.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = (progressBarDurationMillis * (1f - state.percent.value)).toInt(),
                    easing = LinearEasing
                ),
            )
            if (result.endReason == AnimationEndReason.Finished) {
                nextItem()
                state = state.copy(
                    percent = Animatable(0f)
                )
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                val maxWidth = this.size.width
                detectTapGestures(
                    onPress = {
                        if (!isTransitioning) {
                            pause()
                            val pressStartTime = System.currentTimeMillis()
                            this.tryAwaitRelease()
                            if (!isTransitioning) {
                                if (System.currentTimeMillis() - pressStartTime < 200) {
                                    val isClickOnRightSide = (it.x > (maxWidth / 2))
                                    if (isClickOnRightSide) {
                                        nextItem()
                                    } else {
                                        prevItem()
                                    }
                                }
                            }
                            state = state.copy(
                                shouldPause = false,
                            )
                        }
                    },
                )
            }
    ) {
        HorizontalPager(
            userScrollEnabled = false,
            state = pagerState,
            beyondBoundsPageCount = 2,
        ) { currentPage ->
            content(StoryInnerData(currentPage, items[currentPage]))
        }

        ProgressBarRow(
            items = items,
            currentPage = pagerState.currentPage,
            fillPercentage = state.percent.value
        )

        if (items[pagerState.currentPage].isLoaded.not()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
