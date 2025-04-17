package com.bluesky.emptywithcompose

import SmartSwipeRefresh
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bluesky.emptywithcompose.ui.theme.EmptyWithComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()//这个方法的作用是使应用的界面能够显示在屏幕的边缘到边缘，
        // 即使是在系统栏（如状态栏和导航栏）的背后。这样可以让应用的内容完全利用设备的显示区域，
        // 提供更为流畅和沉浸式的用户体验
        setContent {
            EmptyWithComposeTheme {
                //MessageCard(Message("Jetpack compose", "Let we learn it!"))
                //Conversation(MsgData.messages)
                //OpenDialogButton()
                //ScaffoldDemo()
                //SurfaceDemo()
                //MBSLDemo2()
                //DraggableDemo()
                //SwipeableDemo()
                //TransformerDemo()
                Surface {
                    SmartSwipeRefreshDemo()
                }
            }
        }
    }
}

@Preview
@Composable
fun SmartSwipeRefreshDemo() {
    val sentences = remember {
        mutableStateListOf(
            "铁轨总得创死一个人，要不就创死你吧",
            "嘉然，我真的好喜欢你啊，为了你我要听猫中毒",
            "你们平时都不看的吗",
            "我真的怀疑有些人闲的程度",
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        SmartSwipeRefresh(
            onRefresh = {
                delay(2000)
                if (sentences.size == 4) {
                    sentences.add(0, "骂谁罕见，骂谁罕见")
                    sentences.add(0, "真的绝绝子，好喝到翘jiojio")
                    sentences.add(0, "乃琳你带我走吧")
                }
            },
            loadingIndicator = {
                Box(modifier = Modifier.padding(10.dp)) {
                    CircularProgressIndicator(Modifier.size(20.dp))
                }
            }
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(sentences.size) {
                    val currentSentence = sentences[it]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(text = currentSentence, fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OpenDialog() {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = "开启位置服务",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = "这将意味着，我们会给您提供精准的位置服务，并且您将接受关于您订阅的位置信息",
                    fontSize = 16.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(
                        text = "确认", fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(
                        "取消",
                        fontWeight = FontWeight.W700,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

        )
    }
}

/*涉及material2的*/

/*@Composable
fun OpenDialogButton() {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(

            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = "开启位置服务",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = "这将意味着，我们会给您提供精准的位置服务，并且您将接受关于您订阅的位置信息",
                    fontSize = 16.sp
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center

                ) {

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { openDialog.value = false }) {
                        Text("必须接受!")
                    }
                }
            }
        )
    }
}*/

@Composable
fun BaseDialog() {
    var flag by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = { flag = true }) { Text("弹窗") }
    }
    if (flag) {
        Dialog(onDismissRequest = { flag = false }) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    LinearProgressIndicator()
                    Text("加载中 ing...")
                }
            }
        }
    }
}


data class Message(val author: String, val body: String)


@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    EmptyWithComposeTheme {
        Conversation(MsgData.messages)
    }
}


@Composable
fun MessageCard(msg: Message) {

    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(
        targetValue = if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    )

    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = 5.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded },
        color = surfaceColor

    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painterResource(R.drawable.avatar),
                null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Spacer(Modifier.padding(horizontal = 8.dp))
            Column {
                Text(
                    msg.author,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.padding(vertical = 4.dp))
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }
}

@Preview(name = "Light Mode"/*Todo 这里的light mode只是给这个预览命名,由于指定了一个预览模式,那么就不会自动使用默认的深色主题而已*/)
@Preview(name = "明亮主题", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun PreviewMessageCard() {
    EmptyWithComposeTheme {/**/
        Surface {
            MessageCard(msg = Message("Jetpack compose", "Let we learn it!"))
        }
    }
}



