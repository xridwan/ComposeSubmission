package com.eve.jetsubmission.ui.sreen.product

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eve.jetsubmission.ViewModelFactory
import com.eve.jetsubmission.di.Injection
import com.eve.jetsubmission.model.OrderProduct
import com.eve.jetsubmission.ui.common.UiState
import com.eve.jetsubmission.ui.components.ProductItem

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (Int) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllProduct()
            }
            is UiState.Success -> {
                ProductContent(
                    orderProduct = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {
                Log.e("ERROR", "ProductScreen: ${uiState.errorMessage}")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductContent(
    orderProduct: List<OrderProduct>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(orderProduct) { data ->
            ProductItem(
                image = data.product.image,
                title = data.product.name,
                price = data.product.price,
                modifier = Modifier.clickable {
                    navigateToDetail(data.product.id)
                }
            )
        }
    }
}