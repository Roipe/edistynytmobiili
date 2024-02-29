package com.example.edistynytmobiili.model

import com.google.gson.annotations.SerializedName

data class CategoriesState (
    val list: List<CategoryItem> = emptyList(),
    val loading: Boolean = false,
    val errorMsg: String? = null
)
data class CategoryState(
    //itemin default arvo on tyhjä CategoryItem (ei annettuja arvoja, joten id = 0, name = "")
    val item: CategoryItem = CategoryItem(),
    val loading: Boolean = false,
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