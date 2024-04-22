package com.example.edistynytmobiili.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.components.AddNewListing
import com.example.edistynytmobiili.components.SelectableListingItem
import com.example.edistynytmobiili.viewmodel.RentalItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//onBack: () -> Unit, goToEditItem: (Int) -> Unit
fun RentalItemsScreen () {

    val vm: RentalItemsViewModel = viewModel()

    Scaffold(
        topBar = { TopAppBar(
            title = { Text(vm.rentalItemsState.value.categoryName) },
                //Yläpalkin ikonia painettaessa launchataan coroutine, joka avaa tai sulkee drawerin sen tilasta riippuen.
                navigationIcon = {
                    IconButton(onClick = {
                        //onBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }

            )
        }
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            when {
                vm.rentalItemsState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                //Erroreiden tapauksessa printataan näytölle error message.
                vm.rentalItemsState.value.errorMsg != null ->
                    Text("Error: ${vm.rentalItemsState.value.errorMsg}")
                /*
                vm.deleteRentalState.value.id > 0 -> ConfirmCategoryDelete(
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
                    items(vm.rentalItemsState.value.list) {item ->
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(contentAlignment = Alignment.BottomEnd) {
                            SelectableListingItem(
                                name = item.name,
                                onSelect = {vm.setSelectedItem(item.name, item.id)},
                                onOpen = {},
                                isSelected = vm.isSelectedItem(item.id)
                            )
                            Button(
                                modifier = Modifier.padding(bottom = 5.dp, end = 10.dp),
                                onClick = { /*TODO*/ },
                                shape = RoundedCornerShape(5.dp)) {
                                Text("Rent")
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                    }
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                        AddNewListing(name = "Add a new item", onClick = { })
                        Spacer(modifier = Modifier.height(5.dp))
                    }




                }



        }
    }
}