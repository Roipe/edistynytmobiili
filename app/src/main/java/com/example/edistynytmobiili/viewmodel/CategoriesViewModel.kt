package com.example.edistynytmobiili.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.categoriesService
import com.example.edistynytmobiili.model.AddCategoryReq
import com.example.edistynytmobiili.model.AddCategoryState
import com.example.edistynytmobiili.model.CategoriesState
import com.example.edistynytmobiili.model.CategoryItem

import com.example.edistynytmobiili.model.DeleteCategoryState
import com.example.edistynytmobiili.model.EditCategoryReq
import com.example.edistynytmobiili.model.EditCategoryState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState

    private val _deleteCategoryState = mutableStateOf(DeleteCategoryState())
    val deleteCategoryState: State <DeleteCategoryState> = _deleteCategoryState

    private val _addCategoryState = mutableStateOf(AddCategoryState())
    val addCategoryState: State <AddCategoryState> = _addCategoryState

    private val _editCategoryState = mutableStateOf(EditCategoryState())
    val editCategoryState: State <EditCategoryState> = _editCategoryState

    //Esimerkiksi rajapintakutsut tehdään init:n sisällä. Tämän sisältämä koodi suoritetaan näytön renderauksen yhteydessä.
    init {
        getCategories()
    }

    private suspend fun waitForCategories() {
        delay(2000)
    }
    /*
    fun toggleAddDialog(status: Boolean) {
        _categoriesState.value = _categoriesState.value.copy(showAddDialog = status)
    }

     */
    fun toggleEditStatus(newStatus : Boolean) {
        _editCategoryState.value = _editCategoryState.value.copy(status = newStatus)
    }
    fun setEditItem() {
        val item = _categoriesState.value.selectedList[0]
        _editCategoryState.value = _editCategoryState.value.copy(item = item)
    }
    fun setEditName(newName : String) {
        val item = _editCategoryState.value.item.copy(name = newName)
        _editCategoryState.value = _editCategoryState.value.copy(item = item)
    }

    fun submitEdit() {
        viewModelScope.launch {
            try {
                _categoriesState.value = _categoriesState.value.copy(loading = true)
                categoriesService.editCategory(_editCategoryState.value.item.id, EditCategoryReq(name = _editCategoryState.value.item.name))
                refreshCategories()
            } catch(e: Exception) {
                _editCategoryState.value = _editCategoryState.value.copy(errorMsg = e.message)
            } finally {
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }
        }
    }

    fun clearEditError() {
        _editCategoryState.value = _editCategoryState.value.copy(errorMsg = null)
    }
    fun toggleAddStatus(newStatus: Boolean) {
        _addCategoryState.value = _addCategoryState.value.copy(status = newStatus)
    }

    fun setAddName(newName: String) {
        _addCategoryState.value = _addCategoryState.value.copy(name = newName)
    }
    fun addCategory() {
        viewModelScope.launch {
            try {
                _categoriesState.value = _categoriesState.value.copy(loading = true)
                categoriesService.createCategory(AddCategoryReq(_addCategoryState.value.name))
                _addCategoryState.value = _addCategoryState.value.copy(name = "")
                refreshCategories()

            } catch (e: Exception) {
                _addCategoryState.value = _addCategoryState.value.copy(errorMsg = e.message)
            } finally {
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }
        }
    }
    fun verifyCategoryRemoval(categoryId: Int) {
        _deleteCategoryState.value = _deleteCategoryState.value.copy(id = categoryId)
    }
    fun toggleDeleteStatus(newStatus: Boolean) {
        _deleteCategoryState.value = _deleteCategoryState.value.copy(status = newStatus)
    }
    fun clearAdditionError() {
        _addCategoryState.value = _addCategoryState.value.copy(errorMsg =  null)
    }
    fun clearDeletionError() {
        _deleteCategoryState.value = _deleteCategoryState.value.copy(errorMsg = null)
    }
    fun deleteSelectedCategories() {
        viewModelScope.launch {
            try {
                _categoriesState.value = _categoriesState.value.copy(loading = true)
                _categoriesState.value.selectedList.forEach {
                    categoriesService.removeCategory(it.id)
                }
                refreshCategories()
            } catch (e: Exception) {
                _deleteCategoryState.value = _deleteCategoryState.value.copy(errorMsg = e.message)
            } finally {
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }
        }

    }
    fun deleteCategoryById() {
        viewModelScope.launch {
            try {
                _categoriesState.value = _categoriesState.value.copy(loading = true)
                categoriesService.removeCategory(_deleteCategoryState.value.id)
                //Filterillä suodatetaan määritellyn ehdon mukaisesti uuteen listaan itemeitä
                val listOfCategories = _categoriesState.value.list.filter {
                    //Kun tämä ehto on tosi, lisätään käsiteltävä itemi uuteen listaan
                    _deleteCategoryState.value.id != it.id
                }
                //Asetetaan stateen suodatettu lista, josta on poistettu parametrin id:n mukainen kategoria.
                _categoriesState.value = _categoriesState.value.copy(list = listOfCategories)
                //Nollataan deleteStatesta kategoria-id, koska sitä koskevat toimenpiteet on suoritettu
                verifyCategoryRemoval(0)

            } catch (e: Exception) {
                _deleteCategoryState.value = _deleteCategoryState.value.copy(errorMsg = e.message)
            } finally {
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }
        }

    }


    private fun getCategories() {
        viewModelScope.launch {
            try {
                //Asetetaan sovelluksen sivu lataustilaan
                _categoriesState.value = _categoriesState.value.copy(loading = true)
                //Tallennetaan muuttujaan Api-pyynnöstä saatava vastaus
                val response = categoriesService.getCategories()
                //Kopioidaan vastauksesta kategoriat
                _categoriesState.value = _categoriesState.value.copy(list = response.categories)
            //Virheiden varalta tallennetaan error message
            } catch(e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(errorMsg = e.message)
            } finally {
                //Asetetaan sivu pois lataustilasta
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }
        }
    }

    /* Esimerkki viiveellä
    private fun getCategories() {
        viewModelScope.launch {
            _categoriesState.value = _categoriesState.value.copy(loading = true)
            waitForCategories()
            _categoriesState.value = categoriesState.value.copy(
                loading = false,
                list = categoriesService.getCategories().categories
            )
        }
    }
    */

    fun addToSelectedCategories(name: String, id: Int) {
        val currentList = _categoriesState.value.selectedList
        val newItem = CategoryItem(name = name, id = id)
        if (!currentList.contains(newItem))
        {
            _categoriesState.value = _categoriesState.value.copy(selectedList = currentList + newItem)
        }
    }
    fun removeFromSelectedCategories(id: Int) {
        val newSelectedList = _categoriesState.value.selectedList.filter {
            id != it.id
        }
        _categoriesState.value = _categoriesState.value.copy(selectedList = newSelectedList)
    }
    fun checkSelection(name: String, id: Int) : Boolean {
        return _categoriesState.value.selectedList.contains(CategoryItem(name = name, id = id))
    }
    fun unselectAll() {
        _categoriesState.value = _categoriesState.value.copy(selectedList = emptyList())
    }
    fun refreshCategories() {
        getCategories()
        toggleAddStatus(false)
        toggleDeleteStatus(false)
        toggleEditStatus(false)
        unselectAll()
    }

}