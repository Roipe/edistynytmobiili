package com.example.edistynytmobiili.api

import com.example.edistynytmobiili.model.AddRentalItemReq
import com.example.edistynytmobiili.model.EditRentalItemReq
import com.example.edistynytmobiili.model.EditRentalItemRes
import com.example.edistynytmobiili.model.RentRentalItemReq
import com.example.edistynytmobiili.model.RentalItemStatusRes
import com.example.edistynytmobiili.model.RentalItemsResponse

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path



private val retrofit = createClient()

val rentalServices = retrofit.create(RentalApi::class.java)


interface RentalApi {

    @GET("category/{id}/items")
    suspend fun getItems(@Path("id") id: Int): RentalItemsResponse

    @POST("category/{id}/items")
    suspend fun createItem(
        @Path("id") id: Int,
        @Header("Authorization") bearerToken : String,
        @Body addRentalItemReq: AddRentalItemReq)

    @GET("rentalitem/{id}")
    suspend fun getItem(@Path("id") id: Int): RentalItemStatusRes
    @PUT("rentalitem/{id}")
    suspend fun editItem(@Path("id") id: Int, @Body editRentalItemReq: EditRentalItemReq) : EditRentalItemRes
    @DELETE("rentalitem/{id}")
    suspend fun removeItem(@Path("id") id: Int)

    @POST("rentalitem/{id}/rent")
    suspend fun rentItem(@Path("id") id: Int, @Header("Authorization") bearerToken: String, @Body rentRentalItemReq: RentRentalItemReq)


}
