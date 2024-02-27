package com.example.edistynytmobiili.model


//data class kirjautumispyyntöä varten
data class LoginReqModel(
    val username: String = "",
    val password: String  = "",
    val loading: Boolean = false
)
// data class kirjautumisvastausta varten
data class LoginResModel(
    val id: Int = 0,
    val accessToken: String = "",
    val username: String = "",
)
// data class todennuspyyntöä varten
data class AuthReqModel(
    val username: String = "",
    val password: String  = ""
)

