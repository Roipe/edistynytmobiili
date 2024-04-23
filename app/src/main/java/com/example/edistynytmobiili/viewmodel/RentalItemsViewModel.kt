package com.example.edistynytmobiili.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.DbProvider
import com.example.edistynytmobiili.api.categoriesService
import com.example.edistynytmobiili.api.rentalServices
import com.example.edistynytmobiili.model.DeleteRentalItemState
import com.example.edistynytmobiili.model.RentRentalItemReq
import com.example.edistynytmobiili.model.RentalItem
import com.example.edistynytmobiili.model.RentalItemsState
import com.example.edistynytmobiili.model.RentalState
import kotlinx.coroutines.launch

class RentalItemsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0



    private val _rentalItemsState = mutableStateOf(RentalItemsState())
    val rentalItemsState: State<RentalItemsState> = _rentalItemsState

    private val _deleteRentalItemState = mutableStateOf(DeleteRentalItemState())
    val deleteRentalItemState: State<DeleteRentalItemState> = _deleteRentalItemState

    private val _rentalState = mutableStateOf(RentalState())
    val rentalState: State<RentalState> = _rentalState

    init {
        getItems()
    }
    fun setOpenItem(item: RentalItem = RentalItem()) {
        _rentalState.value = _rentalState.value.copy(openItem = item)
    }
    fun isOpenItem (id: Int) : Boolean {
        return _rentalState.value.openItem.id == id
    }
    fun setSelectedItem (name: String = "", id: Int = 0) {
        var newItem = RentalItem(name = name, id = id)

        if (newItem == _rentalState.value.selectedItem) newItem = RentalItem()
        _rentalState.value = _rentalState.value.copy(selectedItem = newItem)
    }

    private fun getItems() {
        viewModelScope.launch {
            try {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = true)
                val categoryResponse = categoriesService.getCategory(categoryId).category.name
                val itemsResponse = rentalServices.getItems(categoryId).items
                val newItemList: MutableList<RentalItem> = mutableListOf()
                //Jotta saadaan listanäkymään tieto vapaista tavaroista, haetaan jokainen tavara vielä yksitellen backendistä
                itemsResponse.forEach { item ->
                    val rentalStatusResponse = rentalServices.getItem(item.id)
                    var newItem = item
                    if(rentalStatusResponse.rentalStatus.statusName != "free")
                        newItem = item.copy(isFree = false)
                    newItemList.add(newItem)
                }
                _rentalItemsState.value = _rentalItemsState.value.copy(
                    categoryName = categoryResponse, list = newItemList)
            } catch(e: Exception) {
                _rentalItemsState.value = _rentalItemsState.value.copy(errorMsg = e.message)
            } finally {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = false)
            }
        }
    }

    fun rentItem(id: Int) {
        viewModelScope.launch {
            try {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = true)
                val accessToken = DbProvider.db.accountDao().getToken()?: ""
                rentalServices.rentItem(id = id,
                    bearerToken = "Bearer $accessToken",
                    RentRentalItemReq())
                _rentalState.value = _rentalState.value.copy(rentItemId = id)
            } catch(e: Exception) {
                _rentalItemsState.value = _rentalItemsState.value.copy(errorMsg = e.message)
            } finally {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = false)
            }

        }
    }

    fun setItemForRemoval(itemId: Int = 0) {
        _deleteRentalItemState.value = _deleteRentalItemState.value.copy(id = itemId)
    }
    fun clearError() {
        _rentalItemsState.value = _rentalItemsState.value.copy(errorMsg = null)
    }
    fun clearDeletionError() {
        _deleteRentalItemState.value = _deleteRentalItemState.value.copy(errorMsg = null)
    }

    fun deleteItemById() {
        viewModelScope.launch {
            try {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = true)
                rentalServices.removeItem(_deleteRentalItemState.value.id)
                val listOfItems = _rentalItemsState.value.list.filter {
                    _deleteRentalItemState.value.id != it.id
                }
                _rentalItemsState.value = _rentalItemsState.value.copy(list = listOfItems)
                refresh()

            } catch (e: Exception) {
                _deleteRentalItemState.value = _deleteRentalItemState.value.copy(errorMsg = e.message)
            } finally {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = false)
            }
        }
    }
    private fun refresh() {
        getItems()
        setItemForRemoval()
        setSelectedItem()
    }
}