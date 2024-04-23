package com.example.edistynytmobiili.view

import android.util.Log
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
import com.example.edistynytmobiili.R
import com.example.edistynytmobiili.components.NameTextField
import com.example.edistynytmobiili.viewmodel.AddRentalItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRentalItemScreen(onDone : (Int) -> Unit, onCancel : () -> Unit) {
    val vm : AddRentalItemViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = vm.addRentalItemState.value.done) {
        if (vm.addRentalItemState.value.done) {
            Log.d("id täs", "${ vm.categoryId }")
            onDone(vm.categoryId)
        }
    }
    LaunchedEffect(key1 = vm.addRentalItemState.value.errorMsg) {
        vm.addRentalItemState.value.errorMsg?.let {
            Toast.makeText(context, vm.addRentalItemState.value.errorMsg, Toast.LENGTH_LONG).show()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.add_a_new_item)) },
                navigationIcon = {
                    IconButton(onClick = { onCancel() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )

        }
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            when {
                vm.addRentalItemState.value.loading -> CircularProgressIndicator(
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

                        NameTextField(name = vm.addRentalItemState.value.name,
                            onNameChange = { newName -> vm.setName(newName) },
                            textFieldLabel = stringResource(R.string.item_name),
                            isError = !vm.addRentalItemState.value.errorMsg.isNullOrBlank(),
                            errorMsg = vm.addRentalItemState.value.errorMsg
                        )
                        Row (modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly){
                            Button(onClick = { onCancel() }) {
                                Text(stringResource(R.string.cancel))
                            }
                            Button(
                                enabled = vm.addRentalItemState.value.name != "",
                                onClick = { vm.addRentalItem() }
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