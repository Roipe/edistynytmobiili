package com.example.edistynytmobiili.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.edistynytmobiili.viewmodel.CategoriesViewModel
import kotlinx.coroutines.launch

@Composable
fun RandomImage() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://picsum.photos/300")
            .build(),
        contentDescription = "Random image"
    )
}

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

@Composable
fun AddCategoryDialog(onConfirm: () -> Unit, onCancel: () -> Unit, name: String, setName : (String) -> Unit, clearError: () -> Unit, errorString: String?) {
    val context = LocalContext.current
    LaunchedEffect(key1 = errorString) {
        //letin avulla voidaan suorittaa koodiblock mikäli nullable-arvo on jotain muuta kuin null.
        errorString?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            clearError()
        }
    }

    AlertDialog(
        title = {Text("Add category")},
        text = {
            Column() {
                Text("Category name")
                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        setName(newValue)
                    }
                )
            }  },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Add Category")
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
fun CategoriesScreen(onMenuClick: () -> Unit, goToEditCategory : (Int) -> Unit) {
    val categoriesVm: CategoriesViewModel = viewModel()

    //Scaffoldilla hallitaan erilaisia UI:n osia, kuten app bareja ja floating action buttoneita
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories")},
                //Yläpalkin ikonia painettaessa launchataan coroutine, joka avaa tai sulkee drawerin sen tilasta riippuen.
                navigationIcon = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }

            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = { categoriesVm.toggleAddDialog(true) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Category")
            }
        }
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            when {
                categoriesVm.categoriesState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                //Erroreiden tapauksessa printataan näytölle error message.
                categoriesVm.categoriesState.value.errorMsg != null ->
                    Text("Error: ${categoriesVm.categoriesState.value.errorMsg}")

                categoriesVm.deleteCategoryState.value.id > 0 -> ConfirmCategoryDelete(
                    onConfirm = { categoriesVm.deleteCategoryById() },
                    onCancel = { categoriesVm.verifyCategoryRemoval(0) },
                    clearError = { categoriesVm.clearDeletionError() },
                    errorString = categoriesVm.deleteCategoryState.value.errorMsg
                )
                categoriesVm.categoriesState.value.showAddDialog -> AddCategoryDialog(
                    onConfirm = { categoriesVm.addCategory() },
                    onCancel = { categoriesVm.toggleAddDialog(false) },
                    name = categoriesVm.addCategoryState.value.name,
                    setName = { newName -> categoriesVm.setName(newName)},
                    clearError = { categoriesVm.clearAdditionError() },
                    errorString = categoriesVm.addCategoryState.value.errorMsg,

                )
                //LazyColumn piirtää vain näytöllä näkyvät asiat, eikä siis tuhlaa resursseja ylimääräisten näytön ulkopuolisten asioiden piirtämiseen.
                else -> LazyColumn() {
                    items(categoriesVm.categoriesState.value.list) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row (
                                Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween){
                                RandomImage()
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.headlineSmall,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Row {
                                        IconButton(onClick = {
                                            goToEditCategory(it.id)
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Edit"
                                            )
                                        }
                                        IconButton(onClick = { categoriesVm.verifyCategoryRemoval(it.id) }) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete"
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}