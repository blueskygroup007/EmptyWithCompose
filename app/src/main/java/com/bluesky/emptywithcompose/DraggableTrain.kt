package com.bluesky.emptywithcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 *
 * @author BlueSky
 * @date 25.3.3
 * Description:
 */
@Composable
fun DraggableDemo() {
    var offsetX by remember {
        mutableStateOf(0f)
    }
    val boxSideLengthDp = 50.dp
    val boxSildeLengthPx = with(LocalDensity.current) {
        boxSideLengthDp.toPx()
    }
    val draggableState = rememberDraggableState {
        offsetX = (offsetX + it).coerceIn(0f, 3 * boxSildeLengthPx)
    }

    Box(
        Modifier
            .width(boxSideLengthDp * 4)
            .height(boxSideLengthDp)
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .size(boxSideLengthDp)
                .offset {
                    IntOffset(offsetX.roundToInt(), 0)
                }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = draggableState
                )
                .background(Color.DarkGray)

        )
    }
}

enum class Status { CLOSE, OPEN }


/*这个函数里面用到了material2的api*/
@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun SwipeableDemo() {
    var blockSize = 48.dp
    var blockSizePx = with(LocalDensity.current) { blockSize.toPx() }
    var swipeableState = rememberSwipeableState(initialValue = Status.CLOSE)
    var anchors = mapOf(0f to Status.CLOSE, blockSizePx to Status.OPEN)

    Box(
        modifier = Modifier
            .size(height = blockSize, width = blockSize * 2)
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.toInt(), 0) }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { from, to ->
                        if (from == Status.CLOSE) {
                            FractionalThreshold(0.3f)
                        } else {
                            FractionalThreshold(0.5f)
                        }
                    },
                    orientation = Orientation.Horizontal
                )
                .size(blockSize)
                .background(Color.DarkGray)
        ) {

        }
    }
}

@Preview
@Composable
fun TransformerDemo() {
    var boxSize = 100.dp
    var offset by remember { mutableStateOf(Offset.Zero) }
    var rotationAngle by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }
    var transformableStatus =
        rememberTransformableState { zoomChange, offsetChange, rotationChange ->
            scale *= zoomChange
            offset += offsetChange
            rotationAngle += rotationChange
        }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Box(
            Modifier
                .size(boxSize)
                .rotate(rotationAngle)
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .scale(scale)
                .background(Color.Green)
                .transformable(
                    state = transformableStatus,
                    lockRotationOnZoomPan = false
                )
        )
    }

}