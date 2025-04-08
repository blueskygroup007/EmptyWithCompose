import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SmartSwipeRefresh(
    onRefresh: suspend () -> Unit, // 刷新时执行的挂起函数
    state: SmartSwipeRefreshState = remember { SmartSwipeRefreshState() }, // 下拉刷新状态
    loadingIndicator: @Composable () -> Unit = { CircularProgressIndicator() }, // 加载指示器
    content: @Composable () -> Unit // 内容区域
) {
    // 使用 SubcomposeSmartSwipeRefresh 获取指示器高度
    SubcomposeSmartSwipeRefresh(indicator = loadingIndicator) { height ->

        // 创建并记住 SmartSwipeRefreshNestedScrollConnection 实例
        val smartSwipeRefreshNestedScrollConnection = remember(state, height) {
            SmartSwipeRefreshNestedScrollConnection(state, height)
        }

        // 使用 Box 布局，并应用嵌套滑动连接
        Box(
            Modifier
                .nestedScroll(smartSwipeRefreshNestedScrollConnection), // 应用嵌套滑动连接
            contentAlignment = Alignment.TopCenter // 内容顶部居中对齐
        ) {
            // 加载指示器，偏移到顶部并根据下拉偏移量调整位置
            Box(Modifier.offset(y = -height + state.indicatorOffset)) {
                loadingIndicator()
            }

            // 内容区域，根据下拉偏移量调整位置
            Box(Modifier.offset(y = state.indicatorOffset)) {
                content()
            }
        }

        // 获取当前密度，用于单位转换
        var density = LocalDensity.current

        // 使用 LaunchedEffect 监听下拉偏移量的变化
        LaunchedEffect(Unit) {
            state.indicatorOffsetFlow.collect {
                // 计算当前偏移量，将 Flow 中的 Float 转换为 Dp
                var currentOffset = with(density) { state.indicatorOffset + it.toDp() }

                // 将偏移量限制在 0 到 height 之间，并设置到状态中
                state.snapToOffset(currentOffset.coerceAtLeast(0.dp).coerceAtMost(height))
            }
        }

        // 使用 LaunchedEffect 监听刷新状态的变化
        LaunchedEffect(state.isRefreshing) {
            if (state.isRefreshing) {
                // 如果刷新状态为 true，则执行刷新操作
                onRefresh()

                // 刷新完成后，将偏移量恢复到 0，并设置刷新状态为 false
                state.animateToOffset(0.dp)
                state.isRefreshing = false
            }
        }
    }
}




@Composable
private fun SubcomposeSmartSwipeRefresh(
    indicator: @Composable () -> Unit, // 加载指示器的可组合函数
    content: @Composable (Dp) -> Unit // 内容区域的可组合函数，接收指示器高度作为参数
) {
    // 使用 SubcomposeLayout 进行布局
    SubcomposeLayout { constraints: Constraints ->

        // 使用 subcompose 函数创建并测量加载指示器
        var indicatorPlaceable = subcompose("indicator", indicator).first().measure(constraints)

        // 使用 subcompose 函数创建并测量内容区域，并将指示器高度传递给内容区域
        var contentPlaceable = subcompose("content") {
            content(indicatorPlaceable.height.toDp()) // 将指示器高度转换为 Dp 并传递给内容区域
        }.map {
            it.measure(constraints) // 测量内容区域
        }.first()

        // 打印指示器高度的日志，用于调试
        Log.d("gzz", "dp: ${indicatorPlaceable.height.toDp()}")

        // 创建布局，并放置内容区域
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0) // 将内容区域放置在 (0, 0) 位置
        }
    }
}


// ... (假设 SmartSwipeRefreshState 和 height 已经在类中定义)
class SmartSwipeRefreshState {

    // 用于确保动画和状态更新的互斥锁，防止并发修改导致状态不一致
    private val mutatorMutex = MutatorMutex()

    // 用于动画显示下拉指示器偏移量的 Animatable 对象
    private val indicatorOffsetAnimatable = Animatable(0.dp, Dp.VectorConverter)

    // 公开只读属性，获取当前下拉指示器的偏移量
    val indicatorOffset get() = indicatorOffsetAnimatable.value

    // 私有的 MutableStateFlow，用于发布下拉偏移量的变化
    private val _indicatorOffsetFlow = MutableStateFlow(0f)

    // 公开只读属性，获取下拉偏移量的 Flow，用于监听偏移量变化
    val indicatorOffsetFlow: Flow<Float> get() = _indicatorOffsetFlow

    // 通过 derivedStateOf 计算属性，判断下拉操作是否正在进行
    val isSwipeInProgress by derivedStateOf { indicatorOffset != 0.dp }

    // 可变状态属性，表示是否正在刷新
    var isRefreshing: Boolean by mutableStateOf(false)

    // 更新下拉偏移量的函数，用于实时更新偏移量
    fun updateOffsetDelta(value: Float) {
        _indicatorOffsetFlow.value = value
    }

    // 立即将下拉指示器偏移量设置为指定值
    suspend fun snapToOffset(value: Dp) {
        mutatorMutex.mutate(MutatePriority.UserInput) {
            indicatorOffsetAnimatable.snapTo(value)
        }
    }

    // 动画方式将下拉指示器偏移量设置为指定值
    suspend fun animateToOffset(value: Dp) {
        mutatorMutex.mutate {
            indicatorOffsetAnimatable.animateTo(value, tween(1000))
        }
    }
}

private class SmartSwipeRefreshNestedScrollConnection(
    val state: SmartSwipeRefreshState, // 传入的 SmartSwipeRefreshState 实例，用于管理下拉刷新状态
    val height: Dp // 下拉刷新指示器的高度
) : NestedScrollConnection {

    // 在滑动发生之前调用，用于处理预滑动事件
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        Log.d("gzz", "onPreScroll") // 打印日志，用于调试

        // 检查滑动源是否为拖拽，并且滑动方向是否为向下（available.y < 0）
        if (source == NestedScrollSource.Drag && available.y < 0) {
            // 更新下拉刷新状态的偏移量
            state.updateOffsetDelta(available.y)

            // 如果下拉操作正在进行，则消耗掉此次滑动事件，否则不消耗
            return if (state.isSwipeInProgress) Offset(x = 0f, y = available.y) else Offset.Zero
        } else {
            // 如果不是拖拽或不是向下滑动，则不消耗滑动事件
            return Offset.Zero
        }
    }

    // 在滑动发生之后调用，用于处理后滑动事件
    override fun onPostScroll(
        consumed: Offset, // 已经被消耗的滑动距离
        available: Offset, // 剩余可用的滑动距离
        source: NestedScrollSource // 滑动源
    ): Offset {
        Log.d("gzz", "onPostScroll") // 打印日志，用于调试

        // 检查滑动源是否为拖拽，并且滑动方向是否为向上（available.y > 0）
        if (source == NestedScrollSource.Drag && available.y > 0) {
            // 更新下拉刷新状态的偏移量
            state.updateOffsetDelta(available.y)

            // 消耗掉此次滑动事件
            return Offset(x = 0f, y = available.y)
        } else {
            // 如果不是拖拽或不是向上滑动，则不消耗滑动事件
            return Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        Log.d("gzz", "onPreFling") // 打印日志，用于调试

        // 检查下拉指示器的偏移量是否超过了高度的一半
        if (state.indicatorOffset > height / 2) {
            // 如果超过一半，则执行刷新操作
            state.animateToOffset(height) // 动画方式将偏移量设置为完整高度
            state.isRefreshing = true // 设置刷新状态为 true
        } else {
            // 如果未超过一半，则将偏移量恢复到 0
            state.animateToOffset(0.dp) // 动画方式将偏移量设置为 0
        }

        // 调用父类的 onPreFling，通常用于处理其他嵌套滑动行为
        return super.onPreFling(available)
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        Log.d("gzz", "onPostFling") // 打印日志，用于调试

        // 调用父类的 onPostFling，通常用于处理其他嵌套滑动行为
        return super.onPostFling(consumed, available)
    }
}