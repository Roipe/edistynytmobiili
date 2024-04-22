package com.example.edistynytmobiili.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.categoriesService
import com.example.edistynytmobiili.api.rentalServices
import com.example.edistynytmobiili.model.DeleteRentalItemState
import com.example.edistynytmobiili.model.RentalItem
import com.example.edistynytmobiili.model.RentalItemsState
import kotlinx.coroutines.launch

class RentalItemsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0



    private val _rentalItemsState = mutableStateOf(RentalItemsState())
    val rentalItemsState: State<RentalItemsState> = _rentalItemsState

    private val _deleteRentalState = mutableStateOf(DeleteRentalItemState())
    val deleteRentalState: State<DeleteRentalItemState> = _deleteRentalState

    //private val _addRentalState = mutableStateOf(AddRentalItemState())
    //val addRentalState: State<AddRentalItemState> = _addRentalState

    init {
        getRentalItems()
    }
    fun setSelectedItem (name: String = "", id: Int = 0) {
        var newItem = RentalItem(name = name, id = id)
        //tarkistetaan onko item jo valittuna, tyhjennetään tarvittaessa
        if (newItem == _rentalItemsState.value.selectedItem) newItem = RentalItem()
        _rentalItemsState.value = _rentalItemsState.value.copy(selectedItem = newItem)
    }
    fun isSelectedItem (id: Int) : Boolean {
        return _rentalItemsState.value.selectedItem.id == id
    }
    private fun getRentalItems() {
        viewModelScope.launch {
            try {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = true)
                val categoryResponse = categoriesService.getCategory(_categoryId).category.name
                val itemsResponse = rentalServices.getItems(_categoryId).items
                _rentalItemsState.value = _rentalItemsState.value.copy(
                    categoryName = categoryResponse, list = itemsResponse)
            } catch(e: Exception) {
                _rentalItemsState.value = _rentalItemsState.value.copy(errorMsg = e.message)
            } finally {
                //Asetetaan sivu pois lataustilasta
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = false)
            }
        }
    }
}