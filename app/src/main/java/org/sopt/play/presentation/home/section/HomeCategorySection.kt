package org.sopt.play.presentation.home.section

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.common.extension.blockPagerSwipe
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.home.component.HomeChip
import org.sopt.play.presentation.home.model.HomeCategory

@Composable
fun HomeCategorySection(
    selectedCategory: HomeCategory,
    onCategoryClick: (HomeCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .blockPagerSwipe()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        HomeCategory.entries.forEach { category ->
            HomeChip(
                category = stringResource(category.title),
                isSelected = selectedCategory == category,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeCategorySectionPreview() {
    PlaySoptTheme {
        HomeCategorySection(
            selectedCategory = HomeCategory.WHOLE,
            onCategoryClick = {},
        )
    }
}
