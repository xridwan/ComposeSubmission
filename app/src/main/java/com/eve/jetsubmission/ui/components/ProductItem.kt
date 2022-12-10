package com.eve.jetsubmission.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
fun ProductItem(
    image: Int,
    title: String,
    price: Long,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(Shapes.medium)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = convertToRupiah(price),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.paddingFromBaseline(bottom = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    JetSubmissionTheme {
        ProductItem(
            image = 0,
            title = "Native",
            price = 2000000
        )
    }
}