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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle

import androidx.compose.material.icons.rounded.CheckCircle

import androidx.compose.material3.Icon

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
@Composable
fun ListingItem(name: String, onOpen: () -> Unit) {

    var mainModifiers = Modifier
        .clip(RoundedCornerShape(14.dp))
        //.clickable(onClick = {onOpen()})
        .pointerInput(onOpen()) { detectTapGestures { onOpen() } }
        // handle accessibility services
        .semantics(mergeDescendants = true) {
            contentDescription = "Open"
            onClick {
                onOpen()
                true
            }
        }
        .background(MaterialTheme.colorScheme.background)
        .wrapContentWidth()
        .padding(8.dp)

    Column(modifier = mainModifiers,
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
    isSelected : Boolean = false
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
    var mainModifiers = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.background)
        .combinedClickable(
            onClick = {
                //isSelected = false
                if (!isSelected) onOpen() else onSelect()
            },
            onLongClick = {
                onSelect()
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                //isSelected = true
            },
            onDoubleClick = {
                onSelect()
                //isSelected = true
            }
        )
        .border(borderDp, borderColor, RoundedCornerShape(16.dp))
        .fillMaxSize()
        .padding(8.dp)




    Row(modifier = mainModifiers) {

        Box {

            RandomImage()
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
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
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
fun AddButtonListing(
    name: String,
    onClick : () -> Unit,
) {
    var mainModifiers = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.background)
        .clickable(onClick = { onClick() })
        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
        .fillMaxSize()
        .padding(8.dp)


    Row(modifier = mainModifiers,
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
fun RandomImage() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://picsum.photos/300")
            .build(),
        contentDescription = "Random image",
        modifier = Modifier.clip(RoundedCornerShape(16.dp))
    )
}

@Composable
fun BiggerRandomImage() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://picsum.photos/300")
            .build(),
        contentDescription = "Random image",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()

    )
}



