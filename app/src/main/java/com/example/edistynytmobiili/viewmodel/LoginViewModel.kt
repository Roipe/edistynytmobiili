package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.model.LoginReqModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    //Login.kt -model tiedoston LoginReqModel -data classista tehdään muutettava tila privaattina
    //Jäsenmuuttujat tehdään privaatiksi, jotta niitä pystytään manipuloimaan ja muuttamaan vain tässä luokassa tehdyillä funktioilla
    private val _loginState = mutableStateOf(LoginReqModel())
    //Tehdään myös julkinen LoginReqModel-tyypin tilamuuttuja, jota hallinnoidaan privaatista muuttujasta
    val loginState: State<LoginReqModel> = _loginState

    fun setUsername(newUsername: String) {
        //.copylla kopioidaan kaikki tiedot statesta, mutta usernamea muutetaan
        _loginState.value = _loginState.value.copy(username =  newUsername)
    }

    fun setPassword(newPassword : String) {
        _loginState.value = _loginState.value.copy(password = newPassword)
    }

    //suspend funktio, jolla saadaan aikaan keinotekoinen viive nappia painaessa
    private suspend fun _waitForLogin() {
        delay(2000)
    }

    fun login() {

        /*Tässä halutaan kutsua ylläolevaa suspend-funktiota.
        Suspend funktiota ei voi kuitenkaan kutsua muuten, kuin toisen suspend-funktion tai coroutinen sisältä
        suspend-funktiota kutsutaan siis viewModelScope-coroutinella, joka periytyy ViewModel()-luokasta
        Tämän scopen avulla kerrotaan ohjelmalle, mihin data tulisi palauttaa.
        ViewModelin "tappamisen" yhteydessä myös scope kuolee, eli requesti canceloituu
         */
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(loading = true)
            _waitForLogin()
            val user = LoginReqModel()
            _loginState.value = _loginState.value.copy(loading = false)
        }



    }
}