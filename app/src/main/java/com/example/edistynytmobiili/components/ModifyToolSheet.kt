package com.example.edistynytmobiili.components

import android.widget.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyToolSheet(
    onDismissRequest : () -> Unit,
) {
    val optionColor = MaterialTheme.colorScheme.onBackground
    //val colors = ButtonDefaults.buttonColors(contentColor = optionColor)

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            TextButton(onClick = {}) {
                Icon(imageVector = Icons.Default.OpenInNew, contentDescription = "Open", tint = optionColor)
                (Text("Open", color = optionColor))
            }
            TextButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit name", tint = optionColor)
                (Text("Edit name", color = optionColor))
            }
            TextButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = optionColor)
                (Text("Delete", color = optionColor))
            }

        }
    }

}