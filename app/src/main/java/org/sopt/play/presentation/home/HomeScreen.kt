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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.designsystem.preview.FontScalePreviews
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.home.component.HomeTopBar
import org.sopt.play.presentation.home.dummy.BannerDummyData
import org.sopt.play.presentation.home.dummy.ProductDummyData
import org.sopt.play.presentation.home.model.BannerModel
import org.sopt.play.presentation.home.model.HomeCategory
import org.sopt.play.presentation.home.model.HomeMenu
import org.sopt.play.domain.model.ProductModel
import org.sopt.play.presentation.home.section.HomeBannerSection
import org.sopt.play.presentation.home.section.HomeCategorySection
import org.sopt.play.presentation.home.section.HomeMenuSection
import org.sopt.play.presentation.home.section.HomeProductRow

@Composable
fun HomeScreen(
    userName: String,
    paddingValues: PaddingValues,
    banners: List<BannerModel>,
    products: List<ProductModel>,
    favorites: Set<String>,
    onFavoriteClick: (id: String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedMenu by rememberSaveable { mutableStateOf(HomeMenu.HOME) }
    var selectedCategory by rememberSaveable { mutableStateOf(HomeCategory.WHOLE) }

    val productRows = remember(products) { products.chunked(2) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PlaySoptTheme.colors.white)
            .padding(paddingValues)
    ) {
        HomeTopBar(
            modifier = Modifier.fillMaxWidth(),
            onSearchClick = onSearchClick,
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp),
        ) {
            item {
                HomeMenuSection(
                    selectedMenu = selectedMenu,
                    onMenuClick = { selectedMenu = it },
                )
            }

            item {
                HomeBannerSection(
                    banners = banners,
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item {
                Text(
                    text = stringResource(R.string.home_recommend_title, userName),
                    style = PlaySoptTheme.typography.m18,
                    color = PlaySoptTheme.colors.black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {
                HomeCategorySection(
                    selectedCategory = selectedCategory,
                    onCategoryClick = { selectedCategory = it },
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            itemsIndexed(
                items = productRows,
                key = { _, rowItems -> rowItems.first().id },
                contentType = { _, _ -> "product_row" },
            ) { index, rowItems ->
                HomeProductRow(
                    rowItems = rowItems,
                    favorites = favorites,
                    onFavoriteClick = onFavoriteClick,
                )
            }
        }
    }
}

@FontScalePreviews
@Composable
private fun HomeScreenPreview() {
    PlaySoptTheme {
        HomeScreen(
            userName = "김솝트",
            paddingValues = PaddingValues(),
            banners = BannerDummyData.banners,
            products = ProductDummyData.products,
            favorites = setOf(ProductDummyData.products.first().id),
            onFavoriteClick = {},
            onSearchClick = {},
        )
    }
}
