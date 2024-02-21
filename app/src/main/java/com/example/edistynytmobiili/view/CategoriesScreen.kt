package com.example.edistynytmobiili.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.edistynytmobiili.MainActivity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(onMenuClick: () -> Unit) {
    //Scaffoldilla hallitaan erilaisia UI:n osia, kuten app bareja ja floating action buttoneita
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home")},
                //Yläpalkin ikonia painettaessa launchataan coroutine, joka avaa tai sulkee drawerin sen tilasta riippuen.
                navigationIcon = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }

            )
        }
    ){
        LazyColumn(modifier = Modifier.padding(it)) {}
    }
}