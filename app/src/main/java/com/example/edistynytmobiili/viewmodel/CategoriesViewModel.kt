package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.categoriesService
import com.example.edistynytmobiili.model.CategoriesResponse
import com.example.edistynytmobiili.model.CategoriesState
import com.example.edistynytmobiili.model.CategoryItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState

    //Esimerkiksi rajapintakutsut tehdään init:n sisällä. Tämän sisältämä koodi suoritetaan näytön renderauksen yhteydessä.
    init {
        getCategories()
    }

    private suspend fun waitForCategories() {
        delay(2000)
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                //Asetetaan sovelluksen sivu lataustilaan
                _categoriesState.value = _categoriesState.value.copy(loading = true)
                //Tallennetaan muuttujaan Api-pyynnöstä saatava vastaus
                val response = categoriesService.getCategories()
                //Kopioidaan vastauksesta kategoriat
                _categoriesState.value = categoriesState.value.copy(list = response.categories)
            } catch(e: Exception) {
            } finally {
                //Asetetaan sivu pois lataustilasta
                _categoriesState.value = categoriesState.value.copy(loading = false)
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

}