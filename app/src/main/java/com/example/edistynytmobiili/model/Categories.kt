package com.example.edistynytmobiili.model

import com.google.gson.annotations.SerializedName

data class CategoriesState(
    val list: List<CategoryItem> = emptyList(),
    val loading: Boolean = false,
    val selectedItem: CategoryItem = CategoryItem(),
    val errorMsg: String? = null

)
data class CategoryState(
    val item: CategoryItem = CategoryItem(),
    val loading: Boolean = false,
    val errorMsg: String? = null
)
data class AddCategoryState(
    val name: String = "",
    val loading: Boolean = false,
    val done: Boolean = false,
    val errorMsg: String? = null
)
data class EditCategoryState(
    val item: CategoryItem = CategoryItem(),
    val loading: Boolean = false,
    val done: Boolean = false,
    val errorMsg: String? = null
)
data class DeleteCategoryState(
    val id: Int = 0,
    val errorMsg: String? = null
)

data class CategoryItem(
    @SerializedName("category_id")
    val id: Int = 0,
    @SerializedName("category_name")
    val name: String = "")

//Backendin vastausta varten tehdään data class, jossa muuttuja, johon listataan yksittäisiä kategorioita
//Tämän parametri nimetään backendin JSON-tiedostossa vastaavan listauksen nimen mukaiseksi, jotta Gson osaa kääntää tämän.
data class CategoriesResponse(val categories: List<CategoryItem> = emptyList())
data class CategoryResponse(val category: CategoryItem = CategoryItem())

data class EditCategoryReq(
    @SerializedName("category_name")
    val name: String = ""
)
data class AddCategoryReq(
    @SerializedName("category_name")
    val name: String = ""
)