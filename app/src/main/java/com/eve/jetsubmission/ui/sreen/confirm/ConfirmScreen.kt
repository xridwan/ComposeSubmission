package com.eve.jetsubmission.ui.sreen.confirm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eve.jetsubmission.R
import com.eve.jetsubmission.ui.components.CustomTextField
import com.eve.jetsubmission.ui.components.PayButton
import com.eve.jetsubmission.ui.theme.JetSubmissionTheme

@Composable
fun ConfirmScreen(
    onBackClick: () -> Unit,
    onPayOrderClicked: (String) -> Unit
) {
    ConfirmContent(
        onBackClicked = onBackClick,
        onPayOrderClicked = onPayOrderClicked
    )
}

@Composable
fun ConfirmContent(
    onBackClicked: () -> Unit,
    onPayOrderClicked: (String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var noRek by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val successMessage = stringResource(id = R.string.success_order)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
                    .clickable {
                        onBackClicked()
                    }
            )
            Text(
                text = stringResource(id = R.string.title_confirm),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                ),
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(1f)
        ) {
            CustomTextField(
                value = name,
                title = stringResource(id = R.string.title_name),
                keyboardType = KeyboardType.Text,
                onValueChange = { name = it }
            )
            CustomTextField(
                value = noRek,
                title = stringResource(id = R.string.title_no_rek),
                keyboardType = KeyboardType.Number,
                onValueChange = { noRek = it }
            )
            CustomTextField(
                value = email,
                title = stringResource(id = R.string.title_email),
                keyboardType = KeyboardType.Email,
                onValueChange = { email = it }
            )
        }
        PayButton(
            enable = name.isNotBlank() && noRek.isNotBlank() && email.isNotBlank(),
            onPayOrderClicked = {
                onPayOrderClicked(successMessage)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmPreview() {
    JetSubmissionTheme {
        ConfirmContent(
            onBackClicked = {},
            onPayOrderClicked = {}
        )
    }
}

