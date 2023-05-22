package com.eve.jetsubmission.ui.screen.detail

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eve.jetsubmission.R
import com.eve.jetsubmission.ViewModelFactory
import com.eve.jetsubmission.di.Injection
import com.eve.jetsubmission.ui.common.UiState
import com.eve.jetsubmission.ui.components.OrderButton
import com.eve.jetsubmission.ui.components.ProductCounter
import com.eve.jetsubmission.ui.theme.JetSubmissionTheme
import com.eve.jetsubmission.utils.convertToRupiah

@Composable
fun DetailScreen(
    productId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigationBack: () -> Unit,
    navigationToCart: () -> Unit,
    onShareClicked: (String) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getProductById(productId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.product.image,
                    title = data.product.name,
                    basePrice = data.product.price,
                    desc = data.product.desc,
                    count = data.count,
                    onBackClick = navigationBack,
                    onAddToCart = { count ->
                        viewModel.addToCart(data.product, count)
                        navigationToCart()
                    },
                    onShareClicked = onShareClicked
                )
            }
            is UiState.Error -> {
                Log.e("ERROR", "DetailScreen: ${uiState.errorMessage}")
            }
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    basePrice: Long,
    desc: String,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    onShareClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var totalPoint by rememberSaveable { mutableStateOf(0L) }
    var orderCount by rememberSaveable { mutableStateOf(count) }
    val shareMessage = stringResource(id = R.string.success_order)

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            onBackClick()
                        }
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = convertToRupiah(basePrice),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colors.secondary
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Specifications : ",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                    letterSpacing = 1.sp
                )
            }
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProductCounter(
                orderId = 1,
                orderCount = orderCount,
                onProductIncreased = { orderCount++ },
                onProductDecreased = { if (orderCount > 0) orderCount-- },
                onShareClicked = { onShareClicked(shareMessage) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalPoint = basePrice * orderCount
            OrderButton(
                text = totalPoint,
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    JetSubmissionTheme {
        DetailContent(
            0,
            "Guitar Stratocaster",
            7500,
            "Native",
            count = 1,
            onBackClick = {},
            onAddToCart = {},
            onShareClicked = {}
        )
    }
}