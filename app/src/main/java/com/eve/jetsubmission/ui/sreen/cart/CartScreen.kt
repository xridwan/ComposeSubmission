package com.eve.jetsubmission.ui.sreen.cart

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eve.jetsubmission.R
import com.eve.jetsubmission.ViewModelFactory
import com.eve.jetsubmission.di.Injection
import com.eve.jetsubmission.ui.common.UiState
import com.eve.jetsubmission.ui.components.CartItem
import com.eve.jetsubmission.ui.components.ResultLayout

@Composable
fun CartScreen(
    viewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
    onShareClicked: (String) -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderProducts()
            }
            is UiState.Success -> {
                CartContent(
                    state = uiState.data,
                    onProductCountChanged = { productId, count ->
                        viewModel.updateOrderProduct(productId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked,
                    navigateToDetail = navigateToDetail,
                    onShareClicked = onShareClicked
                )
            }
            is UiState.Error -> {
                Log.e("ERROR", "CartScreen: ${uiState.errorMessage}")
            }
        }
    }
}

@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Int, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    navigateToDetail: (Int) -> Unit,
    onShareClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val successMessage = stringResource(id = R.string.success_order)
    val shareMessage = stringResource(id = R.string.message_share)

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Text(
                text = stringResource(id = R.string.menu_cart),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                ),
                textAlign = TextAlign.Center
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.orderProduct, key = { it.product.id }) { item ->
                CartItem(
                    productId = item.product.id,
                    image = item.product.image,
                    title = item.product.name,
                    price = item.product.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged,
                    modifier = Modifier.clickable {
                        navigateToDetail(item.product.id)
                    },
                    onShareClicked = {
                        onShareClicked(shareMessage)
                    }
                )
            }
        }
        ResultLayout(
            text = state.totalPrice,
            enabled = state.orderProduct.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(successMessage)
            },
        )
    }
}