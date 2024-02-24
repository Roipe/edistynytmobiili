package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.model.CategoriesState
import com.example.edistynytmobiili.model.CategoryItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState

    //Esimerkiksi rajapintakutsut tehdään init:n sisällä. Tämän sisältämä koodi suoritetaan näytön renderauksen yhteydessä.
    init {
        getCategories()
    }

    private suspend fun waitForCategories() {
        delay(2000)
    }
    private fun getCategories() {
        viewModelScope.launch {
            _categoriesState.value = _categoriesState.value.copy(loading = true)
            waitForCategories()
            _categoriesState.value = categoriesState.value.copy(
                loading = false,
                list = listOf(
                    CategoryItem(id = 1, name = "Kategoria1"),
                    CategoryItem(id = 2, name = "Kategoria2")
                )
            )
        }
    }
}