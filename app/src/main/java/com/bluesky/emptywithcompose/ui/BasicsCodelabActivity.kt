package com.bluesky.emptywithcompose.ui

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bluesky.emptywithcompose.ui.theme.Purple40
import com.bluesky.emptywithcompose.ui.theme.Purple80
import com.bluesky.emptywithcompose.ui.ui.theme.EmptyWithComposeTheme

class BasicsCodelabActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            EmptyWithComposeTheme (dynamicColor = false){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Surface(
            color = MaterialTheme.colorScheme.primary, modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {

            Text(
                text = "Hello $name!",
                modifier = modifier
            )
        }
        Surface(
            color = Color(0xFF6650a4), modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {

        }
        Surface(
            color = Color(0xFFD0BCFF), modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {

        }
        Surface(
            color = Purple40, modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EmptyWithComposeTheme (dynamicColor = false){
        Greeting("Android")
    }
}