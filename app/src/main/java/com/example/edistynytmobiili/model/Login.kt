package com.example.edistynytmobiili.model

import com.google.gson.annotations.SerializedName



data class LoginState(
    val username: String = "",
    val password: String  = "",
    val loading: Boolean = false,
    val errorMsg: String? = null,
    val loginStatus: Boolean = false
)

/*
data class LoginRes(
    val id: Int = 0,
    val accessToken: String = "",
    val username: String = "",
)

 */

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
    val logoutStatus: Boolean = false
)



