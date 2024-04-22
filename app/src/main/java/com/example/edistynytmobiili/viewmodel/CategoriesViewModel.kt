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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState

    private val _deleteCategoryState = mutableStateOf(DeleteCategoryState())
    val deleteCategoryState: State<DeleteCategoryState> = _deleteCategoryState


    //Esimerkiksi rajapintakutsut tehdään init:n sisällä. Tämän sisältämä koodi suoritetaan näytön renderauksen yhteydessä.
    init {
        getCategories()
    }

    fun setSelectedItem (name: String = "", id: Int = 0) {
        var newItem = CategoryItem(name = name, id = id)
        //tarkistetaan onko item jo valittuna, tyhjennetään tarvittaessa
        if (newItem == _categoriesState.value.selectedItem) newItem = CategoryItem()
        _categoriesState.value = _categoriesState.value.copy(selectedItem = newItem)
    }
    fun isSelectedItem (id: Int) : Boolean {
        return _categoriesState.value.selectedItem.id == id
    }

    fun setCategoryForRemoval(categoryId: Int = 0) {
        _deleteCategoryState.value = _deleteCategoryState.value.copy(id = categoryId)
    }


    fun clearDeletionError() {
        _deleteCategoryState.value = _deleteCategoryState.value.copy(errorMsg = null)
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
                //verifyCategoryRemoval(0)
                refresh()

            } catch (e: Exception) {
                _deleteCategoryState.value = _deleteCategoryState.value.copy(errorMsg = e.message)
            } finally {
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }
        }

    }
    private fun refresh() {
        getCategories()
        setCategoryForRemoval()
        setSelectedItem()


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
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(errorMsg = e.message)
            } finally {
                //Asetetaan sivu pois lataustilasta
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }
        }
    }
}
