package org.sopt.play.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.common.extension.addFocusCleaner
import org.sopt.play.core.common.extension.dragToDismiss
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    containerColor: Color = PlaySoptTheme.colors.white,
    dragHandle: @Composable (() -> Unit)? = { PlayDragHandle() },
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 24.dp,
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RectangleShape,
        containerColor = Color.Transparent,
        dragHandle = null,
        sheetGesturesEnabled = false,
        contentWindowInsets = { WindowInsets(0.dp) },
    ) {
        PlayBottomSheetContent(
            shape = shape,
            sheetState = sheetState,
            onDismiss = onDismiss,
            modifier = modifier,
            containerColor = containerColor,
            dragHandle = dragHandle,
            content = content,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayBottomSheetContent(
    shape: RoundedCornerShape,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    containerColor: Color,
    modifier: Modifier = Modifier,
    dragHandle: @Composable (() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .dragToDismiss(sheetState = sheetState, onDismiss = onDismiss)
            .fillMaxWidth()
            .background(color = containerColor, shape = shape)
            .navigationBarsPadding()
            .addFocusCleaner(focusManager)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        dragHandle?.invoke()
        content()
    }
}

@Composable
private fun PlayDragHandle(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(top = 12.dp, bottom = 20.dp)
            .size(width = 36.dp, height = 4.dp)
            .background(
                color = PlaySoptTheme.colors.gray3,
                shape = CircleShape,
            ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PlayBottomSheetPreview() {
    PlaySoptTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PlaySoptTheme.colors.black)
                .padding(all = 40.dp),
        ) {
            PlayBottomSheetContent(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                onDismiss = {},
                containerColor = PlaySoptTheme.colors.white,
                dragHandle = { PlayDragHandle() },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(color = Color.Red.copy(alpha = 0.3f)),
                )
            }
        }
    }
}
