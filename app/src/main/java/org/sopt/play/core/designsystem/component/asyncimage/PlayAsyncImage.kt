package org.sopt.play.core.designsystem.component.asyncimage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PlayAsyncImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val emptyColor = PlaySoptTheme.colors.gray2
    val emptyPainter = remember(emptyColor) { ColorPainter(emptyColor) }

    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = emptyPainter,
        error = emptyPainter,
        modifier = modifier,
    )
}
