package com.example.edistynytmobiili.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp

import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.layout.offset

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreHoriz

import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
@Composable
fun ListingItem(
    name: String,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier) {

    Column(modifier = modifier
        .clip(RoundedCornerShape(14.dp))
        .background(MaterialTheme.colorScheme.background)
        .wrapContentWidth()
        .padding(8.dp)
        .clickable(onClick = { onOpen() }),
        horizontalAlignment = Alignment.CenterHorizontally) {
        BiggerRandomImage()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )


        }


    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectableListingItem(
    name: String,
    onSelect : () -> Unit,
    onOpen : () -> Unit,
    modifier: Modifier = Modifier,
    isSelected : Boolean = false,
    isAvailable : Boolean = true,

) {
    //var isSelected by remember { mutableStateOf(false) }
    val transition = updateTransition(isSelected, label = "selectionTransition")
    val borderDp by transition.animateDp(label = "borderSizeTransition") {
        if (it) 3.dp else 0.dp
    }
    val borderColor by transition.animateColor(label = "borderColorTransition") {
        if (it) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    }
    val haptics = LocalHapticFeedback.current
    val roundedCornerShape = RoundedCornerShape(16.dp)





    Box {

    Row(modifier = modifier
        .clip(roundedCornerShape)
        .background(MaterialTheme.colorScheme.background)
        .combinedClickable(
            onClick = {
                if (!isSelected) onOpen() else onSelect()
            },
            onLongClick = {
                onSelect()
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            },
            onDoubleClick = {
                onSelect()
            },
            enabled = isAvailable
        )
        .border(borderDp, borderColor, roundedCornerShape)
        .padding(8.dp)) {

        RandomImage()

        /*
        Box (contentAlignment = Alignment.TopStart) {
            RandomImage()
            IconButton(
                onClick = { onSelect() },
                modifier = Modifier
                    .offset(x = (-5).dp, y = -10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreHoriz, contentDescription = "Action options",
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                )

            }
        }

         */


            /*
            androidx.compose.animation.AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn(),
                exit = fadeOut()
            ){
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

             */


        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 37.dp)
            )
            if (!isAvailable) Text( modifier = Modifier
                //.fillMaxWidth()
                //.background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.7f))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(2.dp),
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Medium,
                text = "Unavailable")
        }

    }
        if (!isAvailable) Box (
            modifier = Modifier
                .matchParentSize()
                .clip(roundedCornerShape)
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.30f))){
        }
        Row (horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()) {
            FilledIconButton(onClick = { onSelect() },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ){
                Icon(imageVector = Icons.Filled.MoreHoriz,
                    contentDescription = "Action options",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }


    }


}
@Composable
fun AddNewListing(
    name: String,
    onClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.background)
        .clickable(onClick = { onClick() })
        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
        .fillMaxSize()
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {

        Box {

            Icon(imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(10.dp)
                    .size(30.dp))
            //RandomImage()
            /*
            androidx.compose.animation.AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn(),
                exit = fadeOut()
            ){
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

             */


        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }


    }

}

@Composable
fun RandomImage(modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://picsum.photos/300")
            .build(),
        contentDescription = "Random image",
        modifier = modifier.clip(RoundedCornerShape(16.dp))
    )
}

@Composable
fun BiggerRandomImage(modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://picsum.photos/300")
            .build(),
        contentDescription = "Random image",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()

    )
}



