package com.bluesky.emptywithcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bluesky.emptywithcompose.ui.theme.EmptyWithComposeTheme

/**
 *
 * @author BlueSky
 * @date 25.2.19
 * Description:
 */
@Composable
fun SurfaceDemo() {
    Surface(
        shape = RoundedCornerShape(8.dp),
        //tonalElevation = 8.dp,//色调海拔
        shadowElevation = 10.dp,//阴影海拔
        modifier = Modifier
            .width(300.dp)
            .height(100.dp)
    ) {
        Row(modifier = Modifier.clickable { }) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.padding(horizontal = 12.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Liratie", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.padding(vertical = 8.dp))
                Text(text = "礼谙")
            }
        }
    }
}

@Composable
fun SpacerDemo() {
    Row {
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Red))
        Spacer(Modifier.width(20.dp))
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Magenta))
        Spacer(Modifier.weight(1f))
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Black))
    }
}

@Preview
@Composable
fun PreViewSurface() {
    EmptyWithComposeTheme {
        Column {
            SurfaceDemo()
            SpacerDemo()
        }
    }
}