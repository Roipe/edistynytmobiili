package com.example.edistynytmobiili.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.edistynytmobiili.R
import com.example.edistynytmobiili.WindowSizeInfo
import com.example.edistynytmobiili.WindowType

@Composable
fun ActionOptionsSheet (
    name: String,
    onOpen: () -> Unit,
    onEdit : () -> Unit,
    onDelete : () -> Unit,
    onClose: () -> Unit,
    windowSizeInfo: WindowSizeInfo,
) {
    val optionColor = MaterialTheme.colorScheme.onBackground
    Box(
        modifier = Modifier
            .pointerInput(onClose) { detectTapGestures { onClose() } }
            .semantics(mergeDescendants = true) {
                onClick {
                    onClose()
                    true
                }
            }
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.70f)),
        contentAlignment = Alignment.Center
    )
    {
        if (windowSizeInfo.widthInfo is WindowType.Compact || windowSizeInfo.widthInfo is WindowType.Medium)
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(10.dp)
            ){

                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center) {
                    Box (modifier = Modifier.fillMaxWidth(0.6f)){
                        ListingItem(name = name, onOpen = { onOpen() })
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            FilledIconButton( onClick = {onClose()},
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = stringResource(R.string.close),
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                        }
                    }
                }

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
                        Icon(imageVector = Icons.Default.OpenInNew, contentDescription = stringResource(
                            R.string.open
                        ),
                            tint = optionColor
                        )
                        (Text(stringResource(
                            R.string.open),
                            color = optionColor
                        ))
                    }
                    TextButton(onClick = { onEdit() }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(R.string.edit_name),
                            tint = optionColor
                        )
                        (Text(stringResource(R.string.edit_name),
                            color = optionColor
                        ))
                    }
                    TextButton(onClick = { onDelete() }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete),
                            tint = optionColor
                        )
                        (Text(stringResource(R.string.delete),
                            color = optionColor
                        ))
                    }

                }

            }
        else Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ){

            Box() {
                Box (modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.25f)){
                    ListingItem(name = name, onOpen = { onOpen() })
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FilledIconButton( onClick = {onClose()},
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.close),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))
            Column (
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 10.dp),
                //verticalAlignment = Alignment.CenterVertically,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                TextButton(onClick = { onOpen() }) {
                    Icon(imageVector = Icons.Default.OpenInNew, contentDescription = stringResource(
                        R.string.open
                    ),
                        tint = optionColor
                    )
                    (Text(stringResource(
                        R.string.open),
                        color = optionColor
                    ))
                }
                TextButton(onClick = { onEdit() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(R.string.edit_name),
                        tint = optionColor
                    )
                    (Text(stringResource(R.string.edit_name),
                        color = optionColor
                    ))
                }
                TextButton(onClick = { onDelete() }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete),
                        tint = optionColor
                    )
                    (Text(stringResource(R.string.delete),
                        color = optionColor
                    ))
                }

            }

        }

    }

}