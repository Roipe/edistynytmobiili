package com.example.edistynytmobiili.api


import com.example.edistynytmobiili.model.AuthReq
import com.example.edistynytmobiili.model.AuthRes
import com.example.edistynytmobiili.model.RegReq
import com.example.edistynytmobiili.model.RegRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

//Kutsu Client.kt-tiedoston
private val retrofit = createClient()

val authService = retrofit.create(AuthApi::class.java)

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body req: AuthReq): AuthRes

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") bearerToken: String)

    @POST("auth/register")
    suspend fun register(@Body req: RegReq): RegRes

    @GET("auth/account")
    suspend fun getAccount(@Header("Authorization") bearerToken: String)



}
