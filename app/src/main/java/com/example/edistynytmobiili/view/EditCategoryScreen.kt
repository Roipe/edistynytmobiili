package com.example.edistynytmobiili.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.viewmodel.CategoryViewModel

@Composable
fun EditCategoryScreen() {
    val categoryVm: CategoryViewModel = viewModel()
    Column {
        Text("EDIT IKKUNA")
    }
}