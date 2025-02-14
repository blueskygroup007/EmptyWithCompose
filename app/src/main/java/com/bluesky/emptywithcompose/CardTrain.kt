package com.bluesky.emptywithcompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bluesky.emptywithcompose.ui.theme.EmptyWithComposeTheme

/**
 *
 * @author BlueSky
 * @date 2025/2/14
 * Description:

 */

@Composable
fun FloatingActionButtonDemo() {
    FloatingActionButton(onClick = {}) {
        Icon(Icons.Filled.Add, contentDescription = "Localized description")
    }
}

@Composable
fun FABWithText() {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
        text = { Text("添加到我喜欢的") },
        onClick = { /* do something */ }
    )
}

@Preview
@Composable
fun CardPreview() {
    EmptyWithComposeTheme {
        Column {
            FloatingActionButtonDemo()
            FABWithText()
            CardDemo()
        }
    }
}

@Composable
fun CardDemo() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(buildAnnotatedString {
                append("欢迎来到 ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.W900,
                        color = Color(0xFF4552B8)
                    )
                ) {
                    append("Jetpack Compose 博物馆")
                }
            })
            Text(buildAnnotatedString {
                append("你现在观看的章节是 ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.W900
                    )
                ) {
                    append("Card")
                }
            })
        }
    }

}
