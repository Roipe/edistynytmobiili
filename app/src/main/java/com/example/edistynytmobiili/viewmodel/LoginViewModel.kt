package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.edistynytmobiili.model.LoginReqModel

class LoginViewModel : ViewModel() {

    //Login.kt -model tiedoston LoginReqModel -data classista tehdään muutettava tila privaattina
    //Jäsenmuuttujat tehdään privaatiksi, jotta niitä pystytään manipuloimaan ja muuttumaan vain tässä luokassa tehdyillä funktioilla
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
}