package com.example.edistynytmobiili.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.R
import com.example.edistynytmobiili.components.AddNewListing
import com.example.edistynytmobiili.components.ActionOptionsSheet
import com.example.edistynytmobiili.components.ExpandableListingItem
import com.example.edistynytmobiili.viewmodel.RentalItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalItemsScreen (
    onRentComplete: (Int) -> Unit,
    goToAddItem:(Int) -> Unit,
    goToEditItem: (Int) -> Unit,
    onBack: () -> Unit) {

    val vm: RentalItemsViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = vm.rentalItemsState.value.errorMsg) {
        vm.rentalItemsState.value.errorMsg?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            vm.clearError()
        }
    }
    LaunchedEffect(key1 = vm.rentalState.value.rentItemId) {
        if (vm.rentalState.value.rentItemId > 0) {
            onRentComplete(vm.rentalState.value.rentItemId)
        }
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text(vm.rentalItemsState.value.categoryName) },
            navigationIcon = { IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back_button))
            } }, actions = {
                    IconButton(onClick = { goToAddItem(vm.categoryId)}) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_category))
                    }
                }
        ) }
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            when {
                vm.rentalItemsState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                else ->
                    LazyColumn(
                        contentPadding = PaddingValues(10.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .wrapContentSize()
                    ) {
                        items(vm.rentalItemsState.value.list) {item ->
                            Spacer(modifier = Modifier.height(5.dp))
                            Box(contentAlignment = Alignment.BottomEnd) {

                                ExpandableListingItem(
                                    name = item.name,
                                    onOpen = { vm.setOpenItem(item) },
                                    onClose = { vm.setOpenItem() },
                                    optionMenu = { vm.setSelectedItem(item.name, item.id) },
                                    isExpanded = vm.isOpenItem(item.id),
                                    isAvailable = item.isFree)
                                Button(
                                    modifier = Modifier.padding(bottom = 5.dp, end = 10.dp),
                                    onClick = {vm.rentItem(item.id)},
                                    shape = RoundedCornerShape(5.dp),
                                    enabled = item.isFree) {
                                    Text(stringResource(R.string.rent))
                                }
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                            AddNewListing(name = stringResource(R.string.add_a_new_item), onClick = { goToAddItem(vm.categoryId) })
                            Spacer(modifier = Modifier.height(5.dp))
                        }

                    }
            }
            if(vm.deleteRentalItemState.value.id > 0) ConfirmRentalItemDelete(
                onConfirm = { vm.deleteItemById() },
                onCancel = { vm.setItemForRemoval() },
                clearError = { vm.clearDeletionError() },
                name = vm.rentalState.value.selectedItem.name,
                errorString = vm.deleteRentalItemState.value.errorMsg
                )
            if(vm.rentalState.value.selectedItem.name != "")
                Row (){
                    ActionOptionsSheet(
                        name = vm.rentalState.value.selectedItem.name,
                        onOpen = {
                            vm.setOpenItem(vm.rentalState.value.selectedItem)
                            vm.setSelectedItem()
                        },
                        onEdit = { goToEditItem(vm.rentalState.value.selectedItem.id) },
                        onDelete = { vm.setItemForRemoval(vm.rentalState.value.selectedItem.id) },
                        onClose = { vm.setSelectedItem() }

                    )
                }
        }
    }
}
@Composable
fun ConfirmRentalItemDelete(
    onConfirm : () -> Unit, onCancel: () -> Unit,
    clearError: () -> Unit, name: String,
    errorString: String?){

    val context = LocalContext.current
    LaunchedEffect(key1 = errorString) {
        errorString?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            clearError()
        }
    }

    AlertDialog(
        title = {Text(stringResource(R.string.are_you_sure))},
        text = { Column {
            Text(stringResource(R.string.are_you_sure_you_want_to_delete))
            Row {
                Text("\"$name\"", fontWeight = FontWeight.Medium)
                Text(stringResource(R.string.question_mark))
            }
        } },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(stringResource(R.string.delete))
            }
        }, dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
