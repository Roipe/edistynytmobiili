package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.DbProvider.db
import com.example.edistynytmobiili.api.rentalServices
import com.example.edistynytmobiili.model.AddRentalItemReq
import com.example.edistynytmobiili.model.AddRentalItemState
import kotlinx.coroutines.launch

class AddRentalItemViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    val categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0

    private val _addRentalItemState = mutableStateOf(AddRentalItemState())
    val addRentalItemState: State<AddRentalItemState> = _addRentalItemState



    fun setName(newName: String) {
        _addRentalItemState.value = _addRentalItemState.value.copy(name = newName)
    }

    fun addRentalItem() {
        viewModelScope.launch {
            try {
                clearError()
                _addRentalItemState.value = _addRentalItemState.value.copy(loading = true)
                val accessToken = db.accountDao().getToken()?: ""
                rentalServices.createItem(
                    id = categoryId,
                    bearerToken = "Bearer $accessToken",
                    AddRentalItemReq(name = _addRentalItemState.value.name)
                )
                _addRentalItemState.value = _addRentalItemState.value.copy(done = true)

            } catch (e: Exception) {
                _addRentalItemState.value = _addRentalItemState.value.copy(errorMsg = e.message)
            } finally {
                _addRentalItemState.value = _addRentalItemState.value.copy(loading = false)
            }
        }
    }

    fun clearError() {
        _addRentalItemState.value = _addRentalItemState.value.copy(errorMsg = null)
    }
}