package com.bluesky.emptywithcompose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.PositionalThreshold
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefreshIndicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 * @author BlueSky
 * @date 2025/2/26
 * Description:
 */

@Composable
fun PagerDemo() {
    val pagerState = rememberPagerState(pageCount = { 10 })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.background(color = Color.Gray)
    ) { page ->
        Text(
            "Page:$page",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
    val coroutineScope = rememberCoroutineScope()
    Button(onClick = {
        coroutineScope.launch {
            //pagerState.scrollToPage(5)
            pagerState.animateScrollToPage(5)
        }
    }) {
        Text("Jump to Page 5")
    }

    //相当于开启一个存在于Composable函数作用域内的协程。
    LaunchedEffect(pagerState) {
        //将Compose的state对象转换为kotlin的Flow（流）
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Log.d("Page change", "Page changed to $page")

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun PullToRefreshBoxDemo() {
    val list = remember {
        List(4) { "Item $it" }.toMutableStateList()
    }
    var refreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    //新版本的下拉刷新组件。
    PullToRefreshBox(
        isRefreshing = refreshing,
        onRefresh = {
            scope.launch {
                refreshing = true
                delay(1000)
                list += "Item ${list.size + 1}"
                refreshing = false
            }
        },
        modifier = Modifier
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(list) {
                ListItem({ Text(text = it) })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshCustomIndicatorSample(
    items: List<String>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state,
        indicator = {
            MyCustomIndicator(
                state = state,
                isRefreshing = isRefreshing,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(items) {
                ListItem({ Text(text = it) })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCustomIndicator(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.pullToRefreshIndicator(
            state = state,
            isRefreshing = isRefreshing,
            containerColor = PullToRefreshDefaults.containerColor,
            threshold = PositionalThreshold
        ),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = isRefreshing,
            animationSpec = tween(durationMillis = 2000 /*CROSSFADE_DURATION_MILLIS*/),
            modifier = Modifier.align(Alignment.Center)
        ) { refreshing ->
            if (refreshing) {
                CircularProgressIndicator(Modifier.size(50.dp/*SPINNER_SIZE*/))
            } else {
                val distanceFraction = { state.distanceFraction.coerceIn(0f, 1f) }
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Refresh",
                    modifier = Modifier
                        .size(18.dp)
                        .graphicsLayer {
                            val progress = distanceFraction()
                            this.alpha = progress
                            this.scaleX = progress
                            this.scaleY = progress
                        }
                )
            }
        }
    }
}
