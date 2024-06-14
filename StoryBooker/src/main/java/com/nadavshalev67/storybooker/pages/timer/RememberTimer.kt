package com.nadavshalev67.storybooker.pages.timer

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import com.nadavshalev67.storybooker.effects.OnLifecycleEvent
import com.nadavshalev67.storybooker.pages.StoryPageState


@Composable
fun rememberTimer(
    currentPage: Int,
): TimerHandler {
    var state by remember {
        mutableStateOf(
            StoryPageState(
                percent = Animatable(0f),
                shouldPause = true,
                lifecycleEvent = Lifecycle.Event.ON_RESUME,
            )
        )
    }

    LaunchedEffect(state.percent.value) {
        Log.d("nadav555", "[${currentPage}] - prec - ${state.percent.value}")
    }

    fun pause() {
        Log.d("nadav", "[${currentPage}] - pause()")
        state = state.copy(
            shouldPause = true,
        )
    }

    fun resume() {
        Log.d("nadav", "[${currentPage}] - resume()")
        state = state.copy(
            shouldPause = false,
        )
    }

    fun restart() {
        Log.d("nadav", "[${currentPage}] - restart()")
        state = state.copy(
            percent = Animatable(0f),
            shouldPause = false,
        )
    }

    fun reset() {
        Log.d("nadav", "[${currentPage}] - reset()")
        state = state.copy(
            percent = Animatable(0f)
        )
    }

    fun onResume() {
        Log.d("nadav", "[${currentPage}] - onResume()")
        state = state.copy(
            lifecycleEvent = Lifecycle.Event.ON_RESUME
        )
        resume()
    }

    fun onPause() {
        Log.d("nadav", "[${currentPage}] - onPause()")
        state = state.copy(
            lifecycleEvent = Lifecycle.Event.ON_PAUSE
        )
        pause()
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (state.lifecycleEvent != Lifecycle.Event.ON_RESUME) {
                    onResume()
                }
            }

            Lifecycle.Event.ON_PAUSE -> {
                onPause()
            }

            else -> Unit
        }
    }

    return TimerHandler(
        state = state,
        pause = ::pause,
        resume = ::resume,
        restart = ::restart,
        reset = ::reset,
    )

}