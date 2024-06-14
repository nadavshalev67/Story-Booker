package com.nadavshalev67.storybooker.pages.timer

import com.nadavshalev67.storybooker.pages.StoryPageState

data class TimerHandler(
    val state: StoryPageState,
    val pause: () -> Unit,
    val resume: () -> Unit,
    val restart: () -> Unit,
    val reset: () -> Unit,
)