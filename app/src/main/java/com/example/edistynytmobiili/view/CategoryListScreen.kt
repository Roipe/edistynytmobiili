package com.example.edistynytmobiili.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.components.DeleteDialog
import com.example.edistynytmobiili.components.ListingItem
import com.example.edistynytmobiili.components.ModifyDialog
import com.example.edistynytmobiili.viewmodel.CategoriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(onMenuClick: () -> Unit, goToEditCategory : (Int) -> Unit) {
    val vm: CategoriesViewModel = viewModel()
    when {
        vm.categoriesState.value.loading -> CircularProgressIndicator()
        vm.editCategoryState.value.status -> {
            ModifyDialog(
                dialogTitle = "Edit category",
                textFieldLabel = "Category name",
                name = vm.editCategoryState.value.item.name,
                onValueChange = { newValue -> vm.setEditName(newValue) },
                onCancel = { vm.toggleEditStatus(false) },
                onConfirm = { vm.submitEdit() },
                clearError = { vm.clearEditError() },
                errorString = vm.editCategoryState.value.errorMsg
            )
        }
        vm.addCategoryState.value.status -> {
            ModifyDialog(
                dialogTitle = "Add a new category",
                textFieldLabel = "Category name",
                name = vm.addCategoryState.value.name,
                onValueChange = { newValue -> vm.setAddName(newValue) },
                onCancel = { vm.toggleAddStatus(false) },
                onConfirm = { vm.addCategory() },
                clearError = { vm.clearAdditionError() },
                errorString = vm.addCategoryState.value.errorMsg
            )
        }

        vm.deleteCategoryState.value.status -> {
            DeleteDialog(
                dialogTitle = "Are you sure?",
                itemName = vm.categoriesState.value.selectedList[0].name,
                itemUnit = "category",
                amount = vm.categoriesState.value.selectedList.size,
                onCancel = { vm.toggleDeleteStatus(false) },
                onConfirm = { vm.deleteSelectedCategories() },
                clearError = { vm.clearDeletionError() },
                errorString = vm.deleteCategoryState.value.errorMsg
            )
        }
    }



    //Scaffoldilla hallitaan erilaisia UI:n osia, kuten app bareja ja floating action buttoneita
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
                //Yläpalkin ikonia painettaessa launchataan coroutine, joka avaa tai sulkee drawerin sen tilasta riippuen.
                navigationIcon = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    /*
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Add, contentDescription = "Add button")
                    }

                     */
                    IconButton(onClick = {
                        vm.setEditItem()
                        vm.toggleEditStatus(true) },
                        enabled = vm.categoriesState.value.selectedList.count() == 1) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit button")
                    }
                    IconButton(onClick = { vm.toggleDeleteStatus(true)},
                        enabled = vm.categoriesState.value.selectedList.isNotEmpty()) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete button")
                    }

                }

            )
        }
        ,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = { vm.toggleAddStatus(true) },
                modifier = Modifier
                    //.fillMaxHeight(0.2f)
                    //.fillMaxWidth(0.25f)
                    .padding(15.dp)
            )

            {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Category")
            }
        }


    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
                LazyColumn() {
                    items(vm.categoriesState.value.list) {item ->
                        ListingItem(
                            name = item.name,
                            onEdit = { goToEditCategory(item.id) },
                            onDelete = { vm.verifyCategoryRemoval(item.id) },
                            onLongPress = {vm.addToSelectedCategories(item.name, item.id)},
                            onOpen = {},
                            onUnselect = {vm.removeFromSelectedCategories(item.id)},
                            isSelected = vm.checkSelection(item.name, item.id)
                        )
                    }
                }

        }
    }
}