package com.example.edistynytmobiili.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class DrawerEnabled(val status: Boolean = false)
class NavigationViewModel : ViewModel() {
    private val _drawerEnabledState = mutableStateOf(DrawerEnabled())
    val drawerEnabledState: State<DrawerEnabled> = _drawerEnabledState

    fun toggleDrawerEnable() {
        _drawerEnabledState.value = _drawerEnabledState.value.copy(status = !_drawerEnabledState.value.status )
    }
}