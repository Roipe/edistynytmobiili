package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.categoriesService
import com.example.edistynytmobiili.model.AddCategoryState
import com.example.edistynytmobiili.model.AddRentalItemState
import com.example.edistynytmobiili.model.CategoriesState
import com.example.edistynytmobiili.model.DeleteCategoryState
import com.example.edistynytmobiili.model.DeleteRentalItemState
import com.example.edistynytmobiili.model.RentalListState
import kotlinx.coroutines.launch

class RentalItemListViewModel : ViewModel() {
    private val _rentalListState = mutableStateOf(RentalListState())
    val rentalListState: State<RentalListState> = _rentalListState

    private val _deleteRentalState = mutableStateOf(DeleteRentalItemState())
    val deleteRentalState: State<DeleteRentalItemState> = _deleteRentalState

    private val _addRentalState = mutableStateOf(AddRentalItemState())
    val addRentalState: State<AddRentalItemState> = _addRentalState

    init {
        getRentals()
    }
    private fun getRentals() {
        viewModelScope.launch {
            try {
                //Asetetaan sovelluksen sivu lataustilaan
                _rentalListState.value = _rentalListState.value.copy(loading = true)
                //Tallennetaan muuttujaan Api-pyynnöstä saatava vastaus
                //val response = categoriesService.getCategories()
                //_rentalListState.value = _rentalListState.value.copy(list = response.categories)
            } catch(e: Exception) {
                _rentalListState.value = _rentalListState.value.copy(errorMsg = e.message)
            } finally {
                //Asetetaan sivu pois lataustilasta
                _rentalListState.value = _rentalListState.value.copy(loading = false)
            }
        }
    }
}