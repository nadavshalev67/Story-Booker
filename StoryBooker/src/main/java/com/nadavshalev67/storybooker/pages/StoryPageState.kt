package com.nadavshalev67.storybooker.pages

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.lifecycle.Lifecycle

data class StoryPageState(
    val percent: Animatable<Float, AnimationVector1D>,
    val shouldPause: Boolean,
    val lifecycleEvent : Lifecycle.Event,
)