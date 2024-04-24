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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
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
import com.example.edistynytmobiili.WindowSizeInfo
import com.example.edistynytmobiili.WindowType

import com.example.edistynytmobiili.components.AddNewListing
import com.example.edistynytmobiili.components.ActionOptionsSheet

import com.example.edistynytmobiili.components.SelectableListingItem
import com.example.edistynytmobiili.viewmodel.CategoriesViewModel




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onMenuClick: () -> Unit,
    goToEditCategory: (Int) -> Unit,
    goToAddCategory: () -> Unit,
    goToRentalItems: (Int) -> Unit,
    windowSizeInfo: WindowSizeInfo
) {
    val vm: CategoriesViewModel = viewModel()

    Scaffold(
        topBar = { TopAppBar(
            title = { Text(stringResource(R.string.categories))},
                //Yläpalkin ikonia painettaessa launchataan coroutine, joka avaa tai sulkee drawerin sen tilasta riippuen.
                navigationIcon = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.menu))
                    }
                },
                actions = {
                    IconButton(onClick = { goToAddCategory() }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_category))
                    }
                }

            )
        }
    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            when {
                vm.categoriesState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                vm.categoriesState.value.errorMsg != null ->
                    Text(stringResource(R.string.error) + vm.categoriesState.value.errorMsg)
                else -> 
                    if (windowSizeInfo.widthInfo is WindowType.Compact || windowSizeInfo.widthInfo is WindowType.Medium)
                        LazyColumn(
                        contentPadding = PaddingValues(10.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .wrapContentSize()
                    ) {
                        items(vm.categoriesState.value.list) {item ->
                            Spacer(modifier = Modifier.height(5.dp))
                            SelectableListingItem(
                                name = item.name,
                                onSelect = {vm.setSelectedItem(item.name, item.id)},
                                onOpen = { goToRentalItems(item.id) },
                                isSelected = vm.isSelectedItem(item.id)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                            AddNewListing(name = stringResource(R.string.add_a_new_category), onClick = { goToAddCategory() })
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                    else
                        LazyVerticalGrid(columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(10.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .wrapContentSize()
                        ) {
                            items(vm.categoriesState.value.list) {item ->
                                Spacer(modifier = Modifier.height(5.dp))
                                SelectableListingItem(
                                    name = item.name,
                                    onSelect = {vm.setSelectedItem(item.name, item.id)},
                                    onOpen = { goToRentalItems(item.id) },
                                    isSelected = vm.isSelectedItem(item.id)
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                            item {
                                Spacer(modifier = Modifier.height(5.dp))
                                AddNewListing(name = stringResource(R.string.add_a_new_category), onClick = { goToAddCategory() })
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }

            }
                
            }

            if (vm.deleteCategoryState.value.id > 0)
               ConfirmCategoryDelete(
                onConfirm = { vm.deleteCategoryById() },
                onCancel = { vm.setCategoryForRemoval() },
                clearError = { vm.clearDeletionError() },
                name = vm.categoriesState.value.selectedItem.name,
                errorString = vm.deleteCategoryState.value.errorMsg
                )

            if (vm.categoriesState.value.selectedItem.name != "") {
                ActionOptionsSheet(
                    name = vm.categoriesState.value.selectedItem.name,
                    onOpen = { goToRentalItems(vm.categoriesState.value.selectedItem.id) },
                    onEdit = { goToEditCategory(vm.categoriesState.value.selectedItem.id) },
                    onDelete = { vm.setCategoryForRemoval(vm.categoriesState.value.selectedItem.id) },
                    onClose = { vm.setSelectedItem() },
                    windowSizeInfo = windowSizeInfo
                )
        }
    }

}
@Composable
fun ConfirmCategoryDelete(
    onConfirm : () -> Unit,
    onCancel: () -> Unit,
    clearError: () -> Unit,
    name : String,
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
            } },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}