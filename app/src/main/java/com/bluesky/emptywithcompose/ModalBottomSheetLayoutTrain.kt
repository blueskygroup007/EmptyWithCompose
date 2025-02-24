package com.bluesky.emptywithcompose

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 *
 * @author BlueSky
 * @date 25.2.23
 * Description:
 */

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MBSLDemo2() {
    val state =
        androidx.compose.material.rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = state,
        //modifier = Modifier.navigationBarsPadding(),
        sheetContent = {
            Column {
                ListItem(text = { Text("选择分享到哪里吧~") })
                ListItem(text = { Text("github") },
                    icon = {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF181717)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.github),
                                null,
                                tint = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    },
                    modifier = Modifier.clickable { })
                ListItem(
                    text = { Text("微信") },
                    icon = {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF07c160)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.github),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.clickable { }
                            )
                        }
                    }

                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                scope.launch { state.show() }
            }) {
                Text("点我展开")
            }
        }
    }

    BackHandler(
        enabled = (state.currentValue == ModalBottomSheetValue.HalfExpanded) || (state.currentValue == ModalBottomSheetValue.Expanded),
        onBack = {
            scope.launch {
                state.hide()
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MBSLDemo() {
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden }
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.navigationBarsPadding(), // 将 navigationBarsPadding() 应用到 ModalBottomSheet
            onDismissRequest = { showBottomSheet = false },
            sheetState = state
        ) {
            Column(modifier = Modifier.padding(bottom = 16.dp)) { // 在内容区域添加 padding
                ListItem(text = { Text("选择分享到哪里吧~") })
                ListItem(text = { Text("github") },
                    icon = {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF181717)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.github),
                                null,
                                tint = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    },
                    modifier = Modifier.clickable { })
                ListItem(
                    text = { Text("微信") },
                    icon = {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF07c160)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.github),
                                contentDescription = null,
                                //tint = Color.White,
                                modifier = Modifier.clickable { }
                            )
                        }
                    }

                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            showBottomSheet = true
        }) {
            Text("点我展开")
        }
    }

    BackHandler(
        enabled = showBottomSheet,
        onBack = {
            scope.launch {
                state.hide()
                showBottomSheet = false
            }
        }
    )
}
