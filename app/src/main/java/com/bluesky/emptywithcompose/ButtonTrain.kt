package com.bluesky.emptywithcompose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 *
 * @author BlueSky
 * @date 2025/2/14
 * Description:
 */

data class ButtonState(var text: String, var textColor: Color, var buttonColor: Color)

@Composable
fun StateButtonDemo() {
    val interactionState = remember { MutableInteractionSource() }
    val (text, textColor, buttonColor) = when {
        interactionState.collectIsPressedAsState().value -> ButtonState(
            "Just Pressed",
            Color.Red,
            Color.Black
        )

        else -> ButtonState("Just Button", Color.White, Color.Red)
    }
    Button(
        onClick = {},
        interactionSource = interactionState,
        elevation = null,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = textColor
        ),
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min)
    ) {

        Text(
            text = text,
            color = textColor
        )
    }

}

@Composable
fun ButtonDemo() {
    Button(onClick = {}) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("确认")
    }
}

@Preview
@Composable
fun ButtonDemoPreview() {
    Column {
        ButtonDemo()
        Spacer(Modifier.height(50.dp))
        StateButtonDemo()
    }
}