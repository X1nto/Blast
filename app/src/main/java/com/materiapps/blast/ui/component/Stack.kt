package com.materiapps.blast.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

enum class StackSwipeValue {
    None,
    Left,
    Right
}


class StackSwipeableState(
    initialValue: StackSwipeValue,
    confirmStateChange: (StackSwipeValue) -> Boolean = { true },
    val onStateChange: (StackSwipeValue) -> Unit,
) : SwipeableState<StackSwipeValue>(
    initialValue = initialValue,
    animationSpec = tween(250, easing = FastOutSlowInEasing),
    confirmStateChange = confirmStateChange
) {

    suspend fun swipeToLeft() {
        animateTo(StackSwipeValue.Left)
    }

    suspend fun swipeToRight() {
        animateTo(StackSwipeValue.Right)
    }

    companion object {
        fun Saver(
            onStateChange: (StackSwipeValue) -> Unit,
            confirmStateChange: (StackSwipeValue) -> Boolean
        ) = Saver<SwipeableState<StackSwipeValue>, StackSwipeValue>(
            save = { it.currentValue },
            restore = { StackSwipeableState(it, confirmStateChange, onStateChange) }
        )
    }

}


@Composable
fun Stack(
    modifier: Modifier = Modifier,
    state: StackSwipeableState,
    placeholder: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val maxValue = maxWidth.value * 3

        val anchors = mapOf(
            -maxValue to StackSwipeValue.Left,
            0f to StackSwipeValue.None,
            maxValue to StackSwipeValue.Right
        )

        val nextCardScale = remember { Animatable(0.9f) }
        val nextCardRotation = remember { Animatable(180f) }

        LaunchedEffect(state.currentValue) {
            if (state.currentValue != StackSwipeValue.None) {
                nextCardScale.animateTo(1f)
                state.onStateChange(state.currentValue)
                nextCardRotation.animateTo(0f, animationSpec = tween(400))
                state.snapTo(StackSwipeValue.None)
                nextCardScale.snapTo(0.9f)
                nextCardRotation.snapTo(180f)
            }
        }

        Box(
            modifier = Modifier.graphicsLayer {
                rotationY = nextCardRotation.value
                scaleX = nextCardScale.value
                scaleY = nextCardScale.value
            }
        ) {
            if (nextCardRotation.value > 90f) {
                placeholder()
            } else {
                content()
            }
        }
        val offset = state.offset.value
        Box(
            modifier = Modifier
                .offset { IntOffset(x = offset.roundToInt(), y = 0) }
                .rotate(offset / 100f)
                .swipeable(
                    state = state,
                    orientation = Orientation.Horizontal,
                    anchors = anchors,
                )
        ) {
            content()
        }
    }
}

@Composable
fun rememberStackState(
    initialValue: StackSwipeValue = StackSwipeValue.None,
    confirmStateChange: (StackSwipeValue) -> Boolean = { true },
    onStateChange: (StackSwipeValue) -> Unit
): StackSwipeableState {
    return remember {
        StackSwipeableState(initialValue, confirmStateChange, onStateChange)
    }
}