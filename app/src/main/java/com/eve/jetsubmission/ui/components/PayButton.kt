package com.eve.jetsubmission.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eve.jetsubmission.R
import com.eve.jetsubmission.ui.theme.JetSubmissionTheme

@Composable
fun PayButton(
    enable: Boolean = true,
    onPayOrderClicked: () -> Unit
) {
    Button(
        onClick = onPayOrderClicked,
        enabled = enable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(52.dp),
    ) {
        Text(
            text = stringResource(id = R.string.pay),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PayButtonPreview() {
    JetSubmissionTheme {
        PayButton(
            enable = true,
            onPayOrderClicked = {}
        )
    }
}