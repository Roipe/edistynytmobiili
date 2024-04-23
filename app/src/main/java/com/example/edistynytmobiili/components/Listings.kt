package com.example.edistynytmobiili.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp

import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.layout.wrapContentWidth

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.MoreHoriz


import androidx.compose.material3.FilledIconButton

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.edistynytmobiili.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpandableListingItem(
    name: String,
    onOpen : () -> Unit,
    onClose: () -> Unit,
    optionMenu: () -> Unit,
    modifier: Modifier = Modifier,
    isExpanded : Boolean = false,
    isAvailable : Boolean = true,

    ) {
    val transition = updateTransition(isExpanded, label = "selectionTransition")
    val borderDp by transition.animateDp(label = "borderSizeTransition") {
        if (it) 3.dp else 0.dp
    }
    val borderColor by transition.animateColor(label = "borderColorTransition") {
        if (it) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    }
    val haptics = LocalHapticFeedback.current
    val roundedCornerShape = RoundedCornerShape(16.dp)

    Box {
        Column(modifier = modifier
            .clip(roundedCornerShape)
            .background(MaterialTheme.colorScheme.background)
            .combinedClickable(
                onClick = {
                    if (!isExpanded) onOpen() else onClose()
                },
                onLongClick = {
                    optionMenu()
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                onDoubleClick = {
                    optionMenu()
                }
            )
            .border(borderDp, borderColor, roundedCornerShape)
            .padding(8.dp)
        ) {
            Row {


            RandomImage()


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
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(2.dp),
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.Medium,
                    text = stringResource(R.string.unavailable)
                )
            }

            }
            AnimatedVisibility(visible = isExpanded, enter = expandVertically(), exit = shrinkVertically()) {
                Column (modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp),
                    horizontalAlignment = Alignment.Start) {
                    Row {
                        Text(stringResource(R.string.features), fontWeight = FontWeight.Medium)
                    }
                    Text(stringResource(R.string.lorem))
                    Row (horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()){
                        IconButton(onClick = { onClose() }) {
                            Icon(imageVector = Icons.Filled.ExpandLess, contentDescription = stringResource(
                                R.string.show_less
                            )
                            )
                        }
                    }
                }
            }
        }
        if(!isAvailable && !isExpanded)
            Box(modifier = Modifier
                .matchParentSize()
                .clip(roundedCornerShape)
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.25f)))

        Row (horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()) {
            FilledIconButton(onClick = { optionMenu() },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ){
                Icon(imageVector = Icons.Filled.MoreHoriz,
                    contentDescription = stringResource(R.string.action_options),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }


    }


}
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

){
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
                }
            )
            .border(borderDp, borderColor, roundedCornerShape)
            .padding(8.dp)
        ){

            RandomImage()
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
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(2.dp),
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.Medium,
                    text = stringResource(R.string.unavailable))
            }

        }
            if (!isAvailable) Box (
                modifier = Modifier
                    .matchParentSize()
                    .clip(roundedCornerShape)
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.25f))){
            }
            Row (horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()) {
                FilledIconButton(onClick = { onSelect() },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ){
                    Icon(imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = stringResource(R.string.action_options),
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
    Row(modifier = modifier
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.background)
        .clickable(onClick = { onClick() })
        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
        .fillMaxSize()
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {

            Box {
                Icon(imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
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
        contentDescription = stringResource(R.string.random_image),
        modifier = modifier.clip(RoundedCornerShape(16.dp))
    )
}

@Composable
fun BiggerRandomImage(modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://picsum.photos/300")
            .build(),
        contentDescription = stringResource(R.string.random_image),
        contentScale = ContentScale.Fit,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()

    )
}



