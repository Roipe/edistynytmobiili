package com.example.edistynytmobiili.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynytmobiili.AccountDatabase
import com.example.edistynytmobiili.DbProvider
import com.example.edistynytmobiili.api.authService
import com.example.edistynytmobiili.model.LogoutState
import kotlinx.coroutines.launch

class LogoutViewModel(private val db: AccountDatabase = DbProvider.db) : ViewModel() {

    private val _logoutState = mutableStateOf(LogoutState())
    val logoutState: State<LogoutState> = _logoutState

    fun logout() {
        viewModelScope.launch {
            try {
                _logoutState.value = _logoutState.value.copy(loading = true)
                val accessToken = db.accountDao().getToken()
                accessToken?.let {
                    authService.logout("Bearer $it")
                    db.accountDao().removeTokens()
                    setLogoutStatus(true)
                }

            } catch(e: Exception) {
                _logoutState.value = _logoutState.value.copy(errorMsg = e.message)
            } finally {
                _logoutState.value = _logoutState.value.copy(loading = false)
            }
        }
    }
    private fun setLogoutStatus(newStatus: Boolean) {
        _logoutState.value = _logoutState.value.copy(status = newStatus)
    }

}