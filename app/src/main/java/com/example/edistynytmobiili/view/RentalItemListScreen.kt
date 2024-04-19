package com.example.edistynytmobiili.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.components.ListingItem
import com.example.edistynytmobiili.viewmodel.RentalItemListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalItemListScreen (onBack: () -> Unit, goToEditItem: (Int) -> Unit) {
    val vm: RentalItemListViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("-> category name <-") },
                //Yläpalkin ikonia painettaessa launchataan coroutine, joka avaa tai sulkee drawerin sen tilasta riippuen.
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }

            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Category")
            }
        }
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            /*
            when {
                vm.categoriesState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                //Erroreiden tapauksessa printataan näytölle error message.
                vm.categoriesState.value.errorMsg != null ->
                    Text("Error: ${vm.categoriesState.value.errorMsg}")

                vm.deleteCategoryState.value.id > 0 -> ConfirmCategoryDelete(
                    onConfirm = { vm.deleteCategoryById() },
                    onCancel = { vm.verifyCategoryRemoval(0) },
                    clearError = { vm.clearDeletionError() },
                    errorString = vm.deleteCategoryState.value.errorMsg
                )
                vm.categoriesState.value.showAddDialog -> AddCategoryDialog(
                    onConfirm = { vm.addCategory() },
                    onCancel = { vm.toggleAddDialog(false) },
                    name = vm.addCategoryState.value.name,
                    setName = { newName -> vm.setName(newName)},
                    clearError = { vm.clearAdditionError() },
                    errorString = vm.addCategoryState.value.errorMsg,

                    )
                //LazyColumn piirtää vain näytöllä näkyvät asiat, eikä siis tuhlaa resursseja ylimääräisten näytön ulkopuolisten asioiden piirtämiseen.
                else -> LazyColumn() {
                    items(vm.categoriesState.value.list) {item ->
                        ListingItem(
                            name = item.name,
                            onEdit = { goToEditCategory(item.id) },
                            onDelete = { vm.verifyCategoryRemoval(item.id) },
                            onOpen = {}
                        )






                    }
                }
            }

             */
        }
    }
}