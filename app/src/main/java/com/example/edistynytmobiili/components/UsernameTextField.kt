package com.example.edistynytmobiili.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun UsernameTextField(
    username: String,
    onUsernameChange: (String) -> Unit,
    isError: Boolean = false,
    errorColor: Color = MaterialTheme.colorScheme.error,
    textFieldLabel: String = "Username",
    errorMsg: String? = "Username not valid"
) {
    OutlinedTextField(
        label = { Text(textFieldLabel) },
        value = username,
        onValueChange = { newValue ->
            onUsernameChange(newValue)
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
            //.fillMaxWidth()
           .padding(bottom = 8.dp)
    )
}