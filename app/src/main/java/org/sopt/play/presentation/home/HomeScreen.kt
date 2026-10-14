package org.sopt.play.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.designsystem.component.productcard.model.ProductModel
import org.sopt.play.core.designsystem.component.topbar.TopBar
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.data.dummy.BannerDummyData
import org.sopt.play.data.dummy.ProductDummyData
import org.sopt.play.presentation.home.component.BannerSection
import org.sopt.play.presentation.home.component.CategorySection
import org.sopt.play.presentation.home.component.MenuSection
import org.sopt.play.presentation.home.component.ProductRow
import org.sopt.play.presentation.home.model.HomeCategory
import org.sopt.play.presentation.home.model.HomeMenu

@Composable
fun HomeScreen(
    user: String,
    paddingValues: PaddingValues,
    products: List<ProductModel>,
    favorites: Set<String>,
    onFavoriteClick: (ProductModel) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedMenu by remember { mutableStateOf(HomeMenu.HOME) }
    var selectedCategory by remember { mutableStateOf(HomeCategory.WHOLE) }

    val productRows = remember(products) { products.chunked(2) }

    Column(
        modifier = modifier.padding(paddingValues)
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            onSearchClick = onSearchClick,
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PlaySoptTheme.colors.white)
        ) {
            item {
                MenuSection(
                    selectedMenu = selectedMenu,
                    onMenuClick = { selectedMenu = it },
                )
            }

            item {
                BannerSection(
                    banners = BannerDummyData.banners,
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item {
                Text(
                    text = stringResource(R.string.home_recommend_title, user),
                    style = PlaySoptTheme.typography.m18,
                    color = PlaySoptTheme.colors.black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {
                CategorySection(
                    selectedCategory = selectedCategory,
                    onCategoryClick = { selectedCategory = it },
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            items(
                items = productRows,
                key = { rowItems -> rowItems.first().id },
                contentType = { "product_row" },
            ) { rowItems ->
                ProductRow(
                    rowItems = rowItems,
                    favorites = favorites,
                    onFavoriteClick = onFavoriteClick,
                    modifier = Modifier.padding(bottom = 15.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(96.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    PlaySoptTheme {
        HomeScreen(
            user = "김솝트",
            paddingValues = PaddingValues(),
            products = ProductDummyData.products,
            favorites = setOf(ProductDummyData.products.first().id),
            onFavoriteClick = {},
            onSearchClick = {},
        )
    }
}
