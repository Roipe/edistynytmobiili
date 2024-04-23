package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.rentalServices
import com.example.edistynytmobiili.model.RentalSummaryState
import kotlinx.coroutines.launch

class RentalSummaryViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _itemId = savedStateHandle.get<String>("itemId")?.toIntOrNull() ?: 0

    private val _rentalSummaryState = mutableStateOf(RentalSummaryState())
    val rentalSummaryState: State<RentalSummaryState> = _rentalSummaryState

    init {
        getItem()
    }

    private fun getItem() {
        viewModelScope.launch {
            try {
                _rentalSummaryState.value = _rentalSummaryState.value.copy(loading = true)
                val response = rentalServices.getItem(_itemId)
                _rentalSummaryState.value = _rentalSummaryState.value.copy(
                    itemName = response.name,
                    categoryName = response.category.name
                )
            } catch(e: Exception) {
                _rentalSummaryState.value = _rentalSummaryState.value.copy(errorMsg = e.message)
            } finally {
                _rentalSummaryState.value = _rentalSummaryState.value.copy(loading = false)
            }

        }
    }
}