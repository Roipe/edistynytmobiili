package com.example.edistynytmobiili.view

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.components.ListingItem
import com.example.edistynytmobiili.components.NameTextField
import com.example.edistynytmobiili.viewmodel.CategoryViewModel
import com.example.edistynytmobiili.viewmodel.EditCategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryScreen(goToCategories: () -> Unit, onCancel: () -> Unit) {
    val vm: EditCategoryViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = vm.editCategoryState.value.done) {
        if (vm.editCategoryState.value.done) {
            goToCategories()
        }
    }
    LaunchedEffect(key1 = vm.editCategoryState.value.errorMsg) {
        vm.editCategoryState.value.errorMsg?.let {
            Toast.makeText(context, vm.editCategoryState.value.errorMsg, Toast.LENGTH_LONG).show()
        }

    }
    /*
    LaunchedEffect(key1 = vm.editCategoryState.value.isError) {
        if (vm.editCategoryState.value.isError) {
            Toast.makeText(context, vm.editCategoryState.value.errorMsg, Toast.LENGTH_LONG).show()
        }
    }

     */

    Scaffold( topBar = {
        TopAppBar(
            title = { Text("Edit category")},
            navigationIcon = {
                IconButton(onClick = { onCancel() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back button")
                }
            }
        )
    } ){
        Box(
            modifier = Modifier
                //.fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            when {
                vm.editCategoryState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        //verticalArrangement = Arrangement.SpaceEvenly,
                        )
                    {
                        Box (
                            modifier = Modifier.fillMaxWidth(0.7f)
                        ){
                            ListingItem(name = vm.editCategoryState.value.item.name, onOpen = {})
                        }
                        NameTextField(name = vm.editCategoryState.value.item.name,
                            onNameChange = { newName ->
                                vm.setName(newName)
                            },
                            textFieldLabel = "Category name",
                            isError = !vm.editCategoryState.value.errorMsg.isNullOrBlank(),
                            errorMsg = vm.editCategoryState.value.errorMsg
                        )
                        Spacer(Modifier.height(10.dp))
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly){
                            Button(
                                onClick = {
                                    onCancel()
                                }
                            ) {
                                Text("Cancel")
                            }
                            Button(
                                enabled = vm.editCategoryState.value.item.name != "",
                                onClick = {
                                    vm.editCategory()
                                }
                            ) {
                                Text("Save")
                            }
                        }

                    }
                }
            }
        }
    }
}