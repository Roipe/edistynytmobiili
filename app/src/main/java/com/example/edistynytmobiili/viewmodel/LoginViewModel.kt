package com.example.edistynytmobiili.viewmodel

import android.accounts.Account
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.AccountDatabase
import com.example.edistynytmobiili.AccountEntity
import com.example.edistynytmobiili.DbProvider
import com.example.edistynytmobiili.api.authService
import com.example.edistynytmobiili.model.AuthReq
import com.example.edistynytmobiili.model.LoginState


import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val db: AccountDatabase = DbProvider.db) : ViewModel() {


    //Login.kt -model tiedoston LoginReqModel -data classista tehdään muutettava tila privaattina
    //Jäsenmuuttujat tehdään privaatiksi, jotta niitä pystytään manipuloimaan ja muuttamaan vain tässä luokassa tehdyillä funktioilla
    private val _loginState = mutableStateOf(LoginState())
    //Tehdään myös julkinen LoginReqModel-tyypin tilamuuttuja, jota hallinnoidaan privaatista muuttujasta
    val loginState: State<LoginState> = _loginState



    init {
        checkAccess()
    }

    fun setUsername(newUsername: String) {
        //.copylla kopioidaan kaikki tiedot statesta, mutta usernamea muutetaan
        _loginState.value = _loginState.value.copy(username =  newUsername)
    }

    fun setPassword(newPassword : String) {
        _loginState.value = _loginState.value.copy(password = newPassword)
    }

    fun setLoginStatus(newStatus: Boolean) {
        _loginState.value = _loginState.value.copy(status = newStatus)

    }
    fun login() {
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)
                val res = authService.login(
                    AuthReq(
                        username = _loginState.value.username,
                        password = _loginState.value.password
                    )
                )
                //Tallennetaan tietokantaan access token
                db.accountDao().addToken(
                    AccountEntity(accessToken = res.accessToken)
                )
                setLoginStatus(true)

            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(errorMsg = e.message)
            } finally {
                _loginState.value = _loginState.value.copy(loading = false)
            }
        }

    }

    private fun checkAccess() {
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)
                val accessToken = db.accountDao().getToken()
                accessToken?.let {
                authService.getAccount("Bearer $it")

                    setLoginStatus(true)
                }
            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(errorMsg = e.message)
            } finally {
                _loginState.value = _loginState.value.copy(loading = false)
            }
        }

    }




    /*
    fun clearError() {
        _loginState.value = _loginState.value.copy(errorMsg = null)
    }
     */

}