package com.example.materialhass.customcomponents

import android.util.Log
import android.util.TypedValue
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.materialhass.R
import okio.ByteString.Companion.decodeHex
import java.io.File

@Composable
fun DeviceCircle(
    id: String,
    icon: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp
)
{
    var color = MaterialTheme.colorScheme.primary
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        Color(color.toArgb())
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(color))
        }
        FontIcon(iconName = icon, color = MaterialTheme.colorScheme.onPrimary)
        //Icon(icon!!, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun FontIcon(
    iconName: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val font = Font(R.font.webfont, FontWeight.Normal)
    val fontFamily = FontFamily(font)
    val context = LocalContext.current
    val unicodeChar = CheckEnum(iconName)
    Text(
        text = unicodeChar.toString(),
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        modifier = modifier,
        color = color,
        fontSize = fontSize
    )
}

fun CheckEnum(iconName: String): Char {
    return try {
        val enumValue = IconValue.valueOf(transformString(iconName))
        val number = enumValue.ordinal + 1
        val paddedNumber = String.format("%04d", number) // Padding to at least four digits
        val unicodeChar = (0xF000 + paddedNumber.toInt()).toChar()
        Log.e("ICONS", "${paddedNumber.decodeHex()}")
        unicodeChar
    } catch (e: IllegalArgumentException) {
        Log.e("ICONS", "NOT FOUND: $iconName")
        (0xF000).toChar()
    }
}


fun transformString(input: String): String {
    val capitalized = input.toUpperCase()
    val replaced = capitalized.replace("-", "_")
    val removedPrefix = if (replaced.startsWith("MDI:")) {
        replaced.substring(4)
    } else {
        replaced
    }
    return removedPrefix
}



@Preview
@Composable
fun font_test()
{
    FontIcon(iconName = "access_point", color = MaterialTheme.colorScheme.onPrimary)
}