package com.example.edistynytmobiili.api

import com.example.edistynytmobiili.model.AddRentalItemReq
import com.example.edistynytmobiili.model.EditRentalItemReq
import com.example.edistynytmobiili.model.RentalItemResponse
import com.example.edistynytmobiili.model.RentalItemStateRes
import com.example.edistynytmobiili.model.RentalItemsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path



private val retrofit = createClient()


val rentalServices = retrofit.create(RentalApi::class.java)


interface RentalApi {

    @GET("category/{id}/items")
    suspend fun getItems(@Path("id") id: Int): RentalItemsResponse

    @POST("category/{id}/items")
    suspend fun createItem(@Path("id") id: Int, @Body req: AddRentalItemReq) : RentalItemResponse

    @GET("rentalitem/{id}")
    suspend fun getItem(@Path("id")id: Int): RentalItemStateRes
    @PUT("rentalitem/{id}")
    suspend fun editItem(@Path("id") id: Int, @Body editRentalItemReq: EditRentalItemReq) : RentalItemResponse
    @DELETE("rentalitem/{id}")
    suspend fun removeItem(@Path("id") id: Int)


}
