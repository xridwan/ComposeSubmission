package com.eve.jetsubmission.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eve.jetsubmission.ui.theme.JetSubmissionTheme
import com.eve.jetsubmission.ui.theme.Shapes
import com.eve.jetsubmission.utils.convertToRupiah

@Composable
fun CartItem(
    productId: Int,
    image: Int,
    title: String,
    price: Long,
    count: Int,
    onProductCountChanged: (id: Int, count: Int) -> Unit,
    onShareClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(90.dp)
                        .clip(Shapes.small)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .weight(1.0f)
                ) {
                    Text(
                        text = title,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = convertToRupiah(price),
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
            Divider()
            ProductCounter(
                orderId = productId,
                orderCount = count,
                onProductIncreased = { onProductCountChanged(productId, count + 1) },
                onProductDecreased = { onProductCountChanged(productId, count - 1) },
                modifier = Modifier.padding(8.dp),
                onShareClicked = onShareClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    JetSubmissionTheme {
        CartItem(
            productId = 1,
            image = 0,
            title = "Native",
            price = 1500000,
            count = 0,
            onProductCountChanged = { _, _ -> },
            onShareClicked = {})
    }
}