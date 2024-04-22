package com.example.edistynytmobiili.api


import com.example.edistynytmobiili.model.AddCategoryReq
import com.example.edistynytmobiili.model.CategoriesResponse
import com.example.edistynytmobiili.model.CategoryItem
import com.example.edistynytmobiili.model.CategoryResponse
import com.example.edistynytmobiili.model.EditCategoryReq
import com.example.edistynytmobiili.model.RentalItemsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


//Kutsu Client.kt-tiedoston funktiolle
private val retrofit = createClient()

//getCategories-funktion kutsua varten tehdään CategoriesApi-interfacesta muuttuja, tätä kutsutaan ViewModelissa.
val categoriesService = retrofit.create(CategoriesApi::class.java)

//Jokaista rajapintakutsua kohden tehdään interface
interface CategoriesApi {

    //Tämä osoite käytännössä muotoa:
    // baseUrl + category
    // eli -> "http://10.0.2.2:8000/api/v1/category"
    //GET:llä haetaan backendistä tämän osoitteen sisältö
    @GET("category/")
    //Mikäli hausta ei kuulu tuloksia riittävän ajan kuluttua, supsend funktiolla se voidaan keskeyttää
    //Tätä funktiota kutsutaan myöhemmin CategoriesViewModelista nimellä "getCategories".
    //Vastauksena saadaan Categories model-tiedostossa määritellyn data classin "CategoriesResponse"-tietotyypin listaus kategorioista
    suspend fun getCategories(): CategoriesResponse

    @GET("category/{id}")
    suspend fun getCategory(@Path("id") id: Int): CategoryResponse
    @POST("category/")
    suspend fun createCategory(@Body req: AddCategoryReq) : CategoryResponse
    @PUT("category/{id}")
    suspend fun editCategory(@Path("id") id: Int, @Body saveCategoryReq: EditCategoryReq) : CategoryResponse
    @DELETE("category/{id}")
    suspend fun removeCategory(@Path("id") id: Int)

    @GET("category/{id}/items")
    suspend fun getItems(@Path("id") id: Int): RentalItemsResponse


}
