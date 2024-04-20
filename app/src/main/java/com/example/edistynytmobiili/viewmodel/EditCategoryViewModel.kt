package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.categoriesService
import com.example.edistynytmobiili.model.EditCategoryReq
import com.example.edistynytmobiili.model.EditCategoryState
import kotlinx.coroutines.launch

class EditCategoryViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    // ?: elvis-operaattorilla voidaan typecastin yhteydessä muuntaa mahdollinen null-arvo haluttuun muotoon.
    private val _categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0

    private val _editCategoryState = mutableStateOf(EditCategoryState())
    val editCategoryState: State<EditCategoryState> = _editCategoryState
    init {
        getCategory()
    }
    private fun getCategory() {
        viewModelScope.launch {
            try {
                _editCategoryState.value = _editCategoryState.value.copy(loading = true)
                val response = categoriesService.getCategory(_categoryId)
                _editCategoryState.value = _editCategoryState.value.copy(item = response.category)
            } catch(e: Exception) {
                _editCategoryState.value = _editCategoryState.value.copy(errorMsg = e.message)
            } finally {
                _editCategoryState.value = _editCategoryState.value.copy(loading = false)
            }

        }
    }
    fun setName(newName: String) {
        val item = _editCategoryState.value.item.copy(name = newName)
        _editCategoryState.value = _editCategoryState.value.copy(item = item)
    }
    fun editCategory() {
        viewModelScope.launch {
            try {
                _editCategoryState.value = _editCategoryState.value.copy(loading = true)
                categoriesService.editCategory(_categoryId, EditCategoryReq(name = _editCategoryState.value.item.name))
                _editCategoryState.value = _editCategoryState.value.copy(done = true)
            } catch(e: Exception) {
                _editCategoryState.value = _editCategoryState.value.copy(errorMsg = e.message, isError = true)
            } finally {
                _editCategoryState.value = _editCategoryState.value.copy(loading = false)
            }
        }
    }
    fun clearError() {
        _editCategoryState.value = _editCategoryState.value.copy(errorMsg = null, isError = false)
    }


}