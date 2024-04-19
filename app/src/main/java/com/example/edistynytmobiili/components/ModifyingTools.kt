package com.example.edistynytmobiili.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@Composable
fun ModifyDialog(
    dialogTitle : String,
    textFieldLabel : String,
    name : String,
    onValueChange : (String) -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    clearError : () -> Unit,
    errorString : String?
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = errorString) {
        errorString?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            clearError()
        }
    }

    AlertDialog(
        title = { Text(dialogTitle) },
        text = {
            Column() {
                OutlinedTextField(
                    label = { Text(textFieldLabel) },
                    value = name,
                    onValueChange = { newValue ->
                        onValueChange(newValue)
                    }
                )
            }  },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Confirm")
            }
        }, dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text("Cancel")
            }
        }
    )

}
@Composable
fun DeleteDialog(
    dialogTitle : String,
    itemName: String,
    itemUnit: String,
    amount: Int,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    clearError : () -> Unit,
    errorString : String?
) {
    val context = LocalContext.current
    var unit = itemUnit
    val plural = amount > 1
    if(plural) {
        if (itemUnit.last() == 'y') unit = unit.dropLast(1) + "ies"
        else unit += "s"
    }

    LaunchedEffect(key1 = errorString) {
        //letin avulla voidaan suorittaa koodiblock mikäli nullable-arvo on jotain muuta kuin null.
        errorString?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            clearError()
        }
    }

    AlertDialog(
        title = {Text(dialogTitle)},
        text = {
            Column (
                modifier = Modifier.fillMaxWidth()){
                //Text("Are you sure you want to")
                //Spacer(modifier = Modifier.height(10.dp))
                Text("Permanently delete $amount $unit ?", fontSize = 16.sp)
                if(!plural) Text("\"$itemName\"", fontSize = 16.sp, fontWeight = FontWeight.Medium)

            }

               //Text("Are you sure you want to delete the category?")
             /*Text(buildAnnotatedString {
                 append("Are you sure you want to")
                 withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                     append(" delete $amount $unit ?")
                 }
             })

              */
               },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Delete")
            }
        }, dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text("Cancel")
            }
        },

    )
}
