package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.categoriesService
import com.example.edistynytmobiili.api.rentalServices
import com.example.edistynytmobiili.model.EditCategoryReq
import com.example.edistynytmobiili.model.EditRentalItemReq
import com.example.edistynytmobiili.model.EditRentalItemState
import com.example.edistynytmobiili.model.RentalItem
import kotlinx.coroutines.launch

class EditRentalItemViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _editRentalItemState = mutableStateOf(EditRentalItemState())
    val editRentalItemState: State<EditRentalItemState> = _editRentalItemState
    private val _itemId = savedStateHandle.get<String>("itemId")?.toIntOrNull() ?: 0
    init {
        getRentalItem()
    }
    private fun getRentalItem() {
        viewModelScope.launch {
            try {
                _editRentalItemState.value = _editRentalItemState.value.copy(loading = true)
                val response = rentalServices.getItem(_itemId)
                _editRentalItemState.value = _editRentalItemState.value.copy(item = RentalItem(name = response.name, id = _itemId))
            } catch(e: Exception) {
                _editRentalItemState.value = _editRentalItemState.value.copy(errorMsg = e.message)
            } finally {
                _editRentalItemState.value = _editRentalItemState.value.copy(loading = false)
            }

        }
    }
    fun setName(newName: String) {
        val item = _editRentalItemState.value.item.copy(name = newName)
        _editRentalItemState.value = _editRentalItemState.value.copy(item = item)
    }
    fun editRentalItem() {
        viewModelScope.launch {
            try {
                _editRentalItemState.value = _editRentalItemState.value.copy(loading = true)
                val response =
                    rentalServices.editItem(_itemId, EditRentalItemReq(name = _editRentalItemState.value.item.name))
                _editRentalItemState.value = _editRentalItemState.value.copy(categoryId = response.category.id, done = true)
            } catch(e: Exception) {
                _editRentalItemState.value = _editRentalItemState.value.copy(errorMsg = e.message, isError = true)
            } finally {
                _editRentalItemState.value = _editRentalItemState.value.copy(loading = false)
            }
        }
    }
    fun clearError() {
        _editRentalItemState.value = _editRentalItemState.value.copy(errorMsg = null, isError = false)
    }


}
