package com.bluesky.emptywithcompose

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bluesky.emptywithcompose.ui.theme.EmptyWithComposeTheme

/**
 *
 * @author BlueSky
 * @date 2025/2/17
 * Description:
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowDemo() {
    val filters =
        listOf("Washer/Dryer", "Ramp access", "Garden", "Cats OK", "Dogs OK", "Smoke-free")
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        filters.forEach {
            var selected by remember { mutableStateOf(false) }
            val leadingIcon: @Composable () -> Unit = { Icon(Icons.Default.Check, null) }
            FilterChip(//这个组件主要用于过滤内容。该组件能修改label，leadingIcon，trailingIcon，shape，border等方式来自定义选中与否的不同效果
                selected,
                onClick = { selected = !selected },
                label = { Text(it) },
                leadingIcon = if (selected) leadingIcon else null
            )

        }
    }
}

@Composable
fun ColumnDemo() {
    Column(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .size(150.dp),
        verticalArrangement = Arrangement.Center

    ) {
        Text(text = "Hello,World!", modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(text = "Jetpack Compose")
    }
}

@Composable
fun RowDemo() {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        tonalElevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "Jetpack Compose是什么？",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.padding(vertical = 5.dp))
            Text(text = "Jetpack Compose 是一个用于构建 Android UI 的现代工具包。它可以简化并加快Android上的界面开发，使用更少的代码、强大的工具和直观的Kotlin API，快速让应用生动而精彩。")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Favorite, null)
                }
                IconButton(onClick = {}) {
                    Icon(painterResource(R.drawable.chat), null)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Share, null)
                }
            }
        }
    }
}

@Preview(name = "light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun RowDemoPreview() {
    EmptyWithComposeTheme {
        Column {
            RowDemo()
            ColumnDemo()
            FlowDemo()
        }
    }

}