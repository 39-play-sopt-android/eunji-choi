package org.sopt.play.core.common.extension

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = clickable(
    interactionSource = null,
    indication = null,
    enabled = enabled,
    onClick = onClick,
)

fun Modifier.blockPagerSwipe(): Modifier = this
    .nestedScroll(HorizontalOverscrollConsumer)
    .pointerInput(Unit) {
        detectHorizontalDragGestures { change, _ -> change.consume() }
    }

private val HorizontalOverscrollConsumer = object : NestedScrollConnection {
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource,
    ): Offset = Offset(x = available.x, y = 0f)

    override suspend fun onPostFling(
        consumed: Velocity,
        available: Velocity,
    ): Velocity = Velocity(x = available.x, y = 0f)
}

@OptIn(ExperimentalMaterial3Api::class)
fun Modifier.dragToDismiss(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    positionalThreshold: Dp = 56.dp,
    velocityThreshold: Dp = 125.dp,
): Modifier = composed {
    val density = LocalDensity.current
    var dragOffsetPx by remember { mutableFloatStateOf(0f) }
    val settleOffset = remember { Animatable(0f) }

    this
        .offset {
            val y = if (settleOffset.isRunning) settleOffset.value else dragOffsetPx
            IntOffset(x = 0, y = y.roundToInt())
        }
        .draggable(
            state = rememberDraggableState { delta ->
                dragOffsetPx = (dragOffsetPx + delta).coerceAtLeast(0f)
            },
            orientation = Orientation.Vertical,
            onDragStopped = { velocity ->
                val positionalThresholdPx = with(density) { positionalThreshold.toPx() }
                val velocityThresholdPx = with(density) { velocityThreshold.toPx() }

                if (dragOffsetPx > positionalThresholdPx || velocity > velocityThresholdPx) {
                    try {
                        sheetState.hide()
                    } finally {
                        onDismiss()
                    }
                } else {
                    settleOffset.snapTo(dragOffsetPx)
                    settleOffset.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 250),
                    )
                    dragOffsetPx = 0f
                }
            },
        )
}

fun Modifier.addFocusCleaner(focusManager: FocusManager): Modifier {
    return this.pointerInput(focusManager) {
        detectTapGestures(
            onTap = { focusManager.clearFocus() },
        )
    }
}

fun Modifier.noRippleCombinedClickable(
    enabled: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit,
): Modifier = combinedClickable(
    interactionSource = null,
    indication = null,
    enabled = enabled,
    onLongClick = onLongClick,
    onClick = onClick,
)
