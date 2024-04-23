package com.example.edistynytmobiili.components


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.edistynytmobiili.R

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isError: Boolean = false,
    errorColor: Color = MaterialTheme.colorScheme.error,
    textFieldLabel: String = stringResource(R.string.password),
    errorMsg: String? = stringResource(R.string.password_not_valid)
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        label = { Text(textFieldLabel) },
        value = password,
        onValueChange = { newValue ->
            onPasswordChange(newValue)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (showPassword) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            PasswordVisibilityToggleIcon(
                showPassword = showPassword,
                //Annetaan parametrinä lambda-argumentti, jolla ikonin funktio togglettaa tämän composablen salasanan näkyvyyttä
                onTogglePasswordVisibility = {showPassword = !showPassword}
            )
        },
        isError = isError,
        supportingText = {
            if (isError) Text(text = "$errorMsg", color = errorColor)
        },
        modifier = Modifier
    )
}
@Composable
fun PasswordVisibilityToggleIcon(
    showPassword: Boolean,
    onTogglePasswordVisibility: () -> Unit
) {
    val image =
        if (showPassword) Icons.Filled.Visibility
        else Icons.Filled.VisibilityOff

    val contentDescription =
        if (showPassword) stringResource(R.string.hide_password_icon)
        else stringResource(R.string.show_password_icon)

    IconButton(onClick = { onTogglePasswordVisibility() }) {
        Icon(imageVector = image, contentDescription = contentDescription)
    }
}