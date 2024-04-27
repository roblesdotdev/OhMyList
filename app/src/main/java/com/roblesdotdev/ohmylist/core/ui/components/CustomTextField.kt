package com.roblesdotdev.ohmylist.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roblesdotdev.ohmylist.core.ui.theme.OhMyListTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit,
    placeholder: String = "",
    enabled: Boolean = true,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onChange,
            placeholder = {
                Text(text = placeholder)
            },
            enabled = enabled,
            isError = !errorMessage.isNullOrBlank(),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                ),
        )
        errorMessage?.let { err ->
            Text(
                text = err,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 12.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTextFieldPreview() {
    OhMyListTheme {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            CustomTextField(
                value = "Demo",
                onChange = {},
                placeholder = "Custom text field",
            )
        }
    }
}
