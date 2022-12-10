package com.eve.jetsubmission.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eve.jetsubmission.R
import com.eve.jetsubmission.ui.theme.JetSubmissionTheme
import com.eve.jetsubmission.utils.convertToRupiah

@Composable
fun ResultLayout(
    text: Long,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .shadow(10.dp)
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.total),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = convertToRupiah(text),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.SemiBold, color = MaterialTheme.colors.secondary
                )
            )
        }
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier
                .height(40.dp)
                .semantics(mergeDescendants = true) {
                    contentDescription = "order_description"
                },
        ) {
            Text(
                text = stringResource(id = R.string.title_confirm),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun ResultPreview() {
    JetSubmissionTheme {
        ResultLayout(text = 1000L, onClick = {})
    }
}