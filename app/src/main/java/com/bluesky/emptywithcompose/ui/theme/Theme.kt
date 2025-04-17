package com.bluesky.emptywithcompose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


/*Todo 只指定一些主色调的情况下， 自动生成明暗主题中 ColorScheme 的所有颜色值
    *  介绍： Material Theme Builder 是 Google 官方提供的一个 Web 工具，可以帮助你生成 Material Design 3 的主题。
    功能：
    输入主色调： 你只需要输入几个主色调（例如，primary color），Material Theme Builder 就会自动生成完整的浅色和深色主题的颜色值。
    调整： 你可以微调生成的颜色值，以满足你的需求。
    导出： 你可以将生成的主题导出为 Jetpack Compose 代码，直接复制粘贴到你的项目中。
    预览: 你可以预览你的主题。

    *使用方法：
        访问 Material Theme Builder 网站：https://m3.material.io/theme-builder (实际网址:https://material-foundation.github.io/material-theme-builder/)
        在 "Primary color" 区域输入你的主色调。
        在 "Secondary color" 区域输入你的辅助色调。
        在 "Tertiary color" 区域输入你的第三色调。
        在右侧预览区域查看生成的主题。
        在 "Export" 选项卡中，选择 "Android (Compose)"，然后点击 "Download" 下载生成的代码。
        将下载的代码复制粘贴到你的项目中
* */
/*private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)*/

private val DarkColorScheme = darkColorScheme(
    surface = Blue,
    onSurface = Navy,
    primary = Navy,
    onPrimary = Chartreuse
)


private val LightColorScheme = lightColorScheme(
    //primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

        surface = Blue,
        onSurface = Color.White,
        primary = LightBlue,
        onPrimary = Navy

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun EmptyWithComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}