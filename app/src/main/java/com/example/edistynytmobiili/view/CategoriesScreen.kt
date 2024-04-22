package com.example.edistynytmobiili.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.edistynytmobiili.components.AddNewListing
import com.example.edistynytmobiili.components.ActionOptionsSheet

import com.example.edistynytmobiili.components.SelectableListingItem
import com.example.edistynytmobiili.viewmodel.CategoriesViewModel


@Composable
fun ConfirmCategoryDelete(onConfirm : () -> Unit, onCancel: () -> Unit, clearError: () -> Unit, errorString: String?){

    val context = LocalContext.current

    LaunchedEffect(key1 = errorString) {
        //letin avulla voidaan suorittaa koodiblock mikäli nullable-arvo on jotain muuta kuin null.
        errorString?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            clearError()
        }
    }

    AlertDialog(
        title = {Text("Are you sure?")},
        text = { Text("Are you sure you want to delete the category?") },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Delete")
        }
    }, dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onMenuClick: () -> Unit,
    goToEditCategory: (Int) -> Unit,
    goToAddCategory: () -> Unit,
    goToRentalItems: (Int) -> Unit,
) {
    val vm: CategoriesViewModel = viewModel()

    //Scaffoldilla hallitaan erilaisia UI:n osia, kuten app bareja ja floating action buttoneita
    Scaffold(
        topBar = { TopAppBar(
            title = { Text("Categories")},
                //Yläpalkin ikonia painettaessa launchataan coroutine, joka avaa tai sulkee drawerin sen tilasta riippuen.
                navigationIcon = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { goToAddCategory() }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Category")
                    }
                }

            )
        }
        //floatingActionButtonPosition = FabPosition.Center,
        //floatingActionButton = {
        //    FloatingActionButton(onClick = { vm.toggleAddDialog(true) }) {
        //        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Category")
        //    }
        //}
    ){

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            when {
                vm.categoriesState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                //Erroreiden tapauksessa printataan näytölle error message.
                vm.categoriesState.value.errorMsg != null ->
                    Text("Error: ${vm.categoriesState.value.errorMsg}")

                vm.deleteCategoryState.value.id > 0 -> ConfirmCategoryDelete(
                    onConfirm = { vm.deleteCategoryById() },
                    onCancel = { vm.setCategoryForRemoval(0) },
                    clearError = { vm.clearDeletionError() },
                    errorString = vm.deleteCategoryState.value.errorMsg
                )

                /*
                vm.categoriesState.value.showAddDialog -> AddCategoryDialog(
                    onConfirm = { vm.addCategory() },
                    onCancel = { vm.toggleAddDialog(false) },
                    name = vm.addCategoryState.value.name,
                    setName = { newName -> vm.setName(newName)},
                    clearError = { vm.clearAdditionError() },
                    errorString = vm.addCategoryState.value.errorMsg,

                )

                 */
                }
                //LazyColumn piirtää vain näytöllä näkyvät asiat, eikä siis tuhlaa resursseja ylimääräisten näytön ulkopuolisten asioiden piirtämiseen.
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
                        AddNewListing(name = "Add a new category", onClick = { goToAddCategory() })
                        Spacer(modifier = Modifier.height(5.dp))
                    }

                }

                if (vm.categoriesState.value.selectedItem.name != "") {
                    Row (){

                        ActionOptionsSheet(
                            name = vm.categoriesState.value.selectedItem.name,
                            onOpen = { goToRentalItems(vm.categoriesState.value.selectedItem.id) },
                            onEdit = { goToEditCategory(vm.categoriesState.value.selectedItem.id) },
                            onDelete = { vm.setCategoryForRemoval(vm.categoriesState.value.selectedItem.id) },
                            onClose = { vm.setSelectedItem() }

                        )
                    }

                }

        }
    }
}