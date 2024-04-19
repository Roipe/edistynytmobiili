package com.example.edistynytmobiili.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListingItem(
    name: String,
    onEdit : () -> Unit,
    onDelete : () -> Unit,
    onLongPress : () -> Unit,
    onOpen : () -> Unit,
    onUnselect : () -> Unit,
    isSelected : Boolean = false
) {
    //var isSelected by remember { mutableStateOf(false) }
    var showModifyOptions by remember { mutableStateOf(false)}
    var bgColor: Color
    if (isSelected) bgColor  = Color.Red
    else bgColor = MaterialTheme.colorScheme.background
    Column(modifier = Modifier
        .fillMaxWidth()
        .combinedClickable(
            onClick = { if (!isSelected) onOpen() else onUnselect() },
            onLongClick = { onLongPress() }
        )
        .background(color = bgColor)
    ) {
        Row (
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            RandomImage()
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                /*
                Row {
                    IconButton(onClick = {
                        onEdit()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )
                    }
                    IconButton(onClick = { onDelete() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }

                 */

            }
        }

    }
}
/*
@Composable
fun ModifyOptionsVisibilityToggle(
    showModifyOptions: Boolean,
    onToggleModifyOptionsVisibility: () -> Unit
) {

}

 */
@Composable
fun RandomImage() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://picsum.photos/300")
            .build(),
        contentDescription = "Random image"
    )
}



