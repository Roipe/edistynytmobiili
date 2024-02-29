package com.example.edistynytmobiili.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.edistynytmobiili.model.CategoryState

//Parametri haetaan savedStateHandlesta, johon navigoinnin yhteydessä on tallennettu dataa
class CategoryViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    // ?: elvis-operaattorilla voidaan typecastin yhteydessä muuntaa mahdollinen null-arvo haluttuun muotoon.
    val categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0

    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    init {
        Log.d("halo","$categoryId")
    }
}