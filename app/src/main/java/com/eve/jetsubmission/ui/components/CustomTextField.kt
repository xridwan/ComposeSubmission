package com.eve.jetsubmission.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eve.jetsubmission.ui.theme.JetSubmissionTheme

@Composable
fun CustomTextField(
    value: String, title: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        value = value,
        label = {
            Text(text = title)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = onValueChange
    )
}

@Preview(showBackground = true)
@Composable
fun CustomPreview() {
    JetSubmissionTheme {
        CustomTextField(
            value = "Compose",
            title = "Name",
            keyboardType = KeyboardType.Number,
            onValueChange = {}
        )
    }
}