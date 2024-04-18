package com.example.edistynytmobiili.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.api.authService
import com.example.edistynytmobiili.model.RegReq
import com.example.edistynytmobiili.model.RegistrationState
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    private val _registrationState = mutableStateOf(RegistrationState())
    val registrationState: State<RegistrationState> = _registrationState

    fun setUsername(newUsername: String) {
        //.copylla kopioidaan kaikki tiedot statesta, mutta usernamea muutetaan
        _registrationState.value = _registrationState.value.copy(username =  newUsername)
    }
    fun setPassword(newPassword : String) {
        _registrationState.value = _registrationState.value.copy(password = newPassword)
    }
    fun setPasswordConfirm(newConfirmPassword : String) {
        _registrationState.value = _registrationState.value.copy(passwordConfirm = newConfirmPassword)
    }
    fun setRegistrationStatus(newStatus: Boolean) {
        _registrationState.value = _registrationState.value.copy(status = newStatus)
    }

    fun register() {
        clearErrors()
        viewModelScope.launch {
            try {
                if (_registrationState.value.password != _registrationState.value.passwordConfirm) {
                    _registrationState.value = _registrationState.value.copy(isPasswordError = true)
                    throw IllegalArgumentException("Passwords must match")
                }
                _registrationState.value = _registrationState.value.copy(loading = true)
                val res = authService.register(
                    RegReq(
                        username = _registrationState.value.username,
                        password = _registrationState.value.password
                    )
                )
                Log.d("register", "registration successful for username: ${res.username}")
                setRegistrationStatus(true)
            } catch (e: Exception) {
                var message = e.message
                e.message?.let {
                    //Mikäli saadaan HTTP response 409, todetaan käyttäjän olevan jo olemassa
                    //Tätä varten tein pienen lisäyksen backendin auth.py-scriptin 25. riville:
                    /*
                     check = self.db.query(models.AuthUser).filter(models.AuthUser.username == user.username).first()
                     if check is not None:
                        raise HTTPException(status_code=409, detail='user already exists')

                    */
                    //Tarkistetaan siis onko käyttäjänimi jo olemassa:
                    if (it.contains("409", ignoreCase = true)) {
                        _registrationState.value = _registrationState.value.copy(isUsernameError = true)
                        message = "Username already in use"
                    }
                }
                _registrationState.value = _registrationState.value.copy(errorMsg = message)
                //toggleError(true)
            } finally {
                _registrationState.value = _registrationState.value.copy(loading = false)

            }
        }
    }
    private fun clearErrors() {
        _registrationState.value = _registrationState.value.copy(errorMsg = null, isPasswordError = false, isUsernameError = false)
    }

    /*
    fun toggleError(status: Boolean) {
        _registrationState.value = _registrationState.value.copy(showError = status)
    }
       fun clearError() {
        _registrationState.value = _registrationState.value.copy(errorMsg = null)
    }

     */

}