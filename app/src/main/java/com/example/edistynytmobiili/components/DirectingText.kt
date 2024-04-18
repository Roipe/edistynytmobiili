package com.example.edistynytmobiili.components

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun DirectingText(
    directingText: String,
    textButtonText: String,
    onClick : () -> Unit,
    fontSize: TextUnit = 14.sp

    ) {
    Row (verticalAlignment = Alignment.CenterVertically){
        Text(text = directingText, fontSize = fontSize)
        TextButton(onClick = { onClick() }) {
            Text(text = textButtonText, fontSize = fontSize)
        }
    }
}