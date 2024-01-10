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
import kotlin.math.min

@Composable
fun DeviceCircle(
    id: String,
    icon: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    fontSize: TextUnit = TextUnit.Unspecified
)
{
    var color = MaterialTheme.colorScheme.primary
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        Color(color.toArgb())
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(color))
        }
        FontIcon(iconName = icon, color = MaterialTheme.colorScheme.onPrimary, fontSize = fontSize)
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
    val context = LocalContext.current
    val font = FontFamily(Font((R.font.webfont)))
    val unicodeChar = CheckEnum(iconName)
    //Log.e("IconFinal", unicodeChar.toString())
    Text(
        text = unicodeChar,
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        modifier = modifier,
        color = color,
        fontSize = fontSize
    )
}



fun CheckEnum(iconName: String): String {
    return try {
        val symbolHex = try {
            IconValue.valueOf(transformString(iconName)).hex
        } catch (e: IllegalArgumentException) {
            try {
                IconValue2.valueOf(transformString(iconName)).hex
            } catch (e: IllegalArgumentException) {
                try {
                    IconValue3.valueOf(transformString(iconName)).hex
                } catch (e: IllegalArgumentException) {
                    IconValue4.valueOf(transformString(iconName)).hex
                }
            }
        }
        val res = hexToUnicode(symbolHex)
        res
    } catch (e: IllegalArgumentException) {
        Log.e("ICONS", e.toString())
        "\u0765"
    }
}

fun hexToUnicode(hex: String): String {
    val codePoint = hex.toInt(radix = 16)
    return String(Character.toChars(codePoint))
}



fun transformString(input: String): String {
    val capitalized = input.uppercase()
    val replaced = capitalized.replace("-", "_")
    val removedPrefix = if (replaced.startsWith("MDI:")) {
        replaced.substring(4)
    } else {
        replaced
    }
    return removedPrefix
}