package com.example.edistynytmobiili.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryScreen(backToCategories: () -> Unit, goToCategories: () -> Unit) {
    val vm: CategoryViewModel = viewModel()
    //LaunchedEffectillä voidaan kuunnella ja suorittaa koodiblockeja kuunneltavan muuttujan arvon muuttuessa.
    LaunchedEffect(key1 = vm.categoryState.value.ok) {
        if (vm.categoryState.value.ok) {
            vm.setOk(false)
            goToCategories()
        }
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("${vm.categoryState.value.item.name}") },
                navigationIcon = {
                    IconButton(onClick = { backToCategories() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back to categories"
                        )
                    }
            })

        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                vm.categoryState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                vm.categoryState.value.errorMsg != null -> Text("Error: ${vm.categoryState.value.errorMsg}")
                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = vm.categoryState.value.item.name,
                            onValueChange = { newValue ->
                                vm.setName(newValue)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                vm.saveCategory()
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