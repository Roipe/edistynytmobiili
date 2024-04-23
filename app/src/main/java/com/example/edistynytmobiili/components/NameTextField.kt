package com.example.edistynytmobiili.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun NameTextField(
    name: String,
    onNameChange: (String) -> Unit,
    isError: Boolean = false,
    errorColor: Color = MaterialTheme.colorScheme.error,
    textFieldLabel: String = "Name",
    errorMsg: String? = "Name not valid"
) {
    OutlinedTextField(
        label = { Text(textFieldLabel) },
        value = name,
        onValueChange = { newValue ->
            onNameChange(newValue)
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "$errorMsg",
                    color = errorColor
                )
            }
        },
        modifier = Modifier
    )
}