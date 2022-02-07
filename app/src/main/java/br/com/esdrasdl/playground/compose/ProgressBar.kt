package br.com.esdrasdl.playground.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    percent: Float
) {
    val configuration = LocalConfiguration.current

    val width = remember { configuration.screenWidthDp }
    val backgroundColor = remember { Color(0xFFDEE4E9) }
    val foregroundColor = remember { Color(0xFF1A93DA) }
    Surface(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(2.dp))
                .height(4.dp)
                .background(backgroundColor)
                .width(width.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(2.dp))
                    .height(4.dp)
                    .background(foregroundColor)
                    .width(width.dp * percent)
            )
        }
    }
}