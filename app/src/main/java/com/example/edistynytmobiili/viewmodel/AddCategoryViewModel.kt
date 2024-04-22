package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.categoriesService
import com.example.edistynytmobiili.model.AddCategoryReq
import com.example.edistynytmobiili.model.AddCategoryState

import kotlinx.coroutines.launch

class AddCategoryViewModel : ViewModel() {
    private val _addCategoryState = mutableStateOf(AddCategoryState())
    val addCategoryState: State<AddCategoryState> = _addCategoryState

    fun setName(newName: String) {
        _addCategoryState.value = _addCategoryState.value.copy(name = newName)
    }

    fun addCategory() {
        viewModelScope.launch {
            try {
                clearError()
                _addCategoryState.value = _addCategoryState.value.copy(loading = true)
                categoriesService.createCategory(AddCategoryReq(_addCategoryState.value.name))
                _addCategoryState.value = _addCategoryState.value.copy(done = true)

            } catch (e: Exception) {
                _addCategoryState.value = _addCategoryState.value.copy(errorMsg = e.message)
            } finally {
                _addCategoryState.value = _addCategoryState.value.copy(loading = false)
            }
        }
    }
    fun clearError() {
        _addCategoryState.value = _addCategoryState.value.copy(errorMsg = null)
    }

}