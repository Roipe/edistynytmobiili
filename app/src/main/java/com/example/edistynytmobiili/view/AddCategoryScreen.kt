package com.example.edistynytmobiili.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

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
import com.example.edistynytmobiili.components.NameTextField
import com.example.edistynytmobiili.viewmodel.AddCategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(onDone : () -> Unit, onCancel : () -> Unit) {
    val vm : AddCategoryViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = vm.addCategoryState.value.done) {
        if (vm.addCategoryState.value.done) {
            onDone()
        }
    }
    LaunchedEffect(key1 = vm.addCategoryState.value.errorMsg) {
        vm.addCategoryState.value.errorMsg?.let {
            Toast.makeText(context, vm.addCategoryState.value.errorMsg, Toast.LENGTH_LONG).show()
        }
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Add a new category") },
            navigationIcon = { IconButton(onClick = { onCancel() }) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back button"
                )}
            }
        )}
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            when {
                vm.addCategoryState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> {
                    Column( horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxHeight(0.5f)) {
                        Row (modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly){
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Filled.AddPhotoAlternate,
                                    contentDescription = "Select picture",
                                    modifier = Modifier.size(30.dp))
                            }
                            Text("Select picture")
                        }

                        NameTextField(name = vm.addCategoryState.value.name,
                            onNameChange = { newName ->
                                vm.setName(newName)
                            },
                            textFieldLabel = "Category name",
                            isError = !vm.addCategoryState.value.errorMsg.isNullOrBlank(),
                            errorMsg = vm.addCategoryState.value.errorMsg
                        )
                        Row (modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly){
                            Button(onClick = { onCancel() }) {
                                Text("Cancel")
                            }
                            Button(
                                enabled = vm.addCategoryState.value.name != "",
                                onClick = { vm.addCategory() }
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