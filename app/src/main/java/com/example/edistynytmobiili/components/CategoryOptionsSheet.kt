package com.example.edistynytmobiili.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun CategoryOptionsSheet (
    name: String,
    onOpen: () -> Unit,
    onEdit : () -> Unit,
    onDelete : () -> Unit,
    onClose: () -> Unit,
) {
    val optionColor = MaterialTheme.colorScheme.onBackground
    var show by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        // handle pointer input
        .pointerInput(onClose) { detectTapGestures { onClose() } }
        // handle accessibility services
        .semantics(mergeDescendants = true) {
            contentDescription = "Close"
            onClick {
                onClose()
                true
            }
        }
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.70f)),
        // = Alignment.Center
    )
    {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ){
                Box() {
                    ListingItem(name = name, onOpen = { onOpen() })
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxWidth()
                        //.padding(end=30.dp)
                    ) {
                        TextButton(
                            onClick = { onClose() },
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)

                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close, contentDescription = "Close",
                                tint = optionColor
                            )
                            (Text(
                                "Close",
                                color = optionColor
                            ))
                        }
                    }

                }


            //ListingItem(name = "kissa")
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 10.dp),
                //verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                TextButton(onClick = { onOpen() }) {
                    Icon(imageVector = Icons.Default.OpenInNew, contentDescription = "Open",
                        tint = optionColor
                    )
                    (Text("Open",
                        color = optionColor
                    ))
                }
                TextButton(onClick = { onEdit() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit name",
                        tint = optionColor
                    )
                    (Text("Edit name",
                        color = optionColor
                    ))
                }
                TextButton(onClick = { onDelete() }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete",
                        tint = optionColor
                    )
                    (Text("Delete",
                        color = optionColor
                    ))
                }

            }


        }

    }

}