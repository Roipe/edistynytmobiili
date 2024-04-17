package com.example.edistynytmobiili.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.categoriesService
import com.example.edistynytmobiili.model.CategoryState
import com.example.edistynytmobiili.model.EditCategoryReq
import kotlinx.coroutines.launch

//Parametri haetaan savedStateHandlesta, johon navigoinnin yhteydessä on tallennettu dataa
class CategoryViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    // ?: elvis-operaattorilla voidaan typecastin yhteydessä muuntaa mahdollinen null-arvo haluttuun muotoon.
    private val _categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0

    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    init {
        getCategory()
    }

    private fun getCategory() {
        viewModelScope.launch {
            try {
                _categoryState.value = _categoryState.value.copy(loading = true)
                val response = categoriesService.getCategory(_categoryId)
                _categoryState.value = _categoryState.value.copy(item = response.category)
            } catch(e: Exception) {
                _categoryState.value = _categoryState.value.copy(errorMsg = e.message)
            } finally {
                _categoryState.value = _categoryState.value.copy(loading = false)
            }

        }
    }
    fun setName(newName: String) {
        val item = _categoryState.value.item.copy(name = newName)
        _categoryState.value = _categoryState.value.copy(item = item)
    }
    fun saveCategory() {
        viewModelScope.launch {
            try {
                _categoryState.value = _categoryState.value.copy(loading = true)
                categoriesService.editCategory(_categoryId, EditCategoryReq(name = _categoryState.value.item.name))
                //Asetetaan ok-arvo, jolla annetaan Viewille tieto, että requesti onnistui ja se saa suorittaa poisnavigoinnin
                setOk(true)
            } catch(e: Exception) {
                _categoryState.value = _categoryState.value.copy(errorMsg = e.message)
            } finally {
                _categoryState.value = _categoryState.value.copy(loading = false)
            }
        }
    }
    fun setOk(status: Boolean) {
        _categoryState.value = _categoryState.value.copy(ok = status)
    }

}