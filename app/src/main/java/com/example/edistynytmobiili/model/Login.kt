package com.example.edistynytmobiili.model

import com.google.gson.annotations.SerializedName



data class LoginState(
    val username: String = "",
    val password: String  = "",
    val loading: Boolean = false,
    val errorMsg: String? = null,
    val status: Boolean = false
)
data class AuthReq(
    val username: String = "",
    val password: String  = ""
)

data class AuthRes(
    @SerializedName("access_token")
    val accessToken: String = ""
)

data class LogoutState(
    val loading: Boolean = false,
    val errorMsg: String? = null,
    val status: Boolean = false
)

data class RegistrationState (
    val username: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val loading: Boolean = false,
    val errorMsg: String? = null,
    val status: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    //val showError: Boolean = false
)

data class RegReq(
    val username: String = "",
    val password: String  = ""
)

data class RegRes(
    @SerializedName("username")
    val username: String = ""
)



