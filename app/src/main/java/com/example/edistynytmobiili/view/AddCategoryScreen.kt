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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.MainActivity
import com.example.edistynytmobiili.R
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
        topBar = { TopAppBar(title = { Text(stringResource(R.string.add_a_new_category)) },
            navigationIcon = { IconButton(onClick = { onCancel() }) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
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
                                    contentDescription = stringResource(R.string.select_picture),
                                    modifier = Modifier.size(30.dp))
                            }
                            Text(stringResource(R.string.select_picture))
                        }
                        NameTextField(name = vm.addCategoryState.value.name,
                            onNameChange = { newName ->
                                vm.setName(newName)
                            },
                            textFieldLabel = stringResource(R.string.category_name),
                            isError = !vm.addCategoryState.value.errorMsg.isNullOrBlank(),
                            errorMsg = vm.addCategoryState.value.errorMsg
                        )
                        Row (modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly){
                            Button(onClick = { onCancel() }) {
                                Text(stringResource(R.string.cancel))
                            }
                            Button(
                                enabled = vm.addCategoryState.value.name != "",
                                onClick = { vm.addCategory() }
                            ) {
                                Text(stringResource(R.string.save))
                            }
                        }
                    }
                }
            }
        }
    }
}