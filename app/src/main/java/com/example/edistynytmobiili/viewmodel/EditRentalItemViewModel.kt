package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.DbProvider.db
import com.example.edistynytmobiili.api.rentalServices
import com.example.edistynytmobiili.model.EditRentalItemReq
import com.example.edistynytmobiili.model.EditRentalItemState
import com.example.edistynytmobiili.model.RentalItem
import kotlinx.coroutines.launch

class EditRentalItemViewModel(
    savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _itemId = savedStateHandle.get<String>("itemId")?.toIntOrNull() ?: 0
    private val _editRentalItemState = mutableStateOf(EditRentalItemState())
    val editRentalItemState: State<EditRentalItemState> = _editRentalItemState

    init {
        getItem()
    }
    private fun getItem() {
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

    fun editItem() {
        viewModelScope.launch {
            try {
                clearError()
                _editRentalItemState.value = _editRentalItemState.value.copy(loading = true)
                val accessToken = db.accountDao().getToken()?: ""
                val response =
                    rentalServices.editItem(_itemId, "Bearer $accessToken", EditRentalItemReq(name = _editRentalItemState.value.item.name))
                _editRentalItemState.value = _editRentalItemState.value.copy(categoryId = response.category.id, done = true)
            } catch(e: Exception) {
                _editRentalItemState.value = _editRentalItemState.value.copy(errorMsg = e.message)
            } finally {
                _editRentalItemState.value = _editRentalItemState.value.copy(loading = false)
            }
        }
    }




    private fun clearError() {
        _editRentalItemState.value = _editRentalItemState.value.copy(errorMsg = null)
    }

}
