package com.example.edistynytmobiili.model

data class CategoriesState (
    val list: List<CategoryItem> = emptyList(),
    val loading: Boolean = false
)

data class CategoryItem(val category_id: Int = 0, val category_name: String = "")

//Backendin vastausta varten tehdään data class, jossa muuttuja, johon listataan yksittäisiä kategorioita
//Tämän parametri nimetään backendin JSON-tiedostossa vastaavan listauksen nimen mukaiseksi, jotta Gson osaa kääntää tämän.
data class CategoriesResponse(val categories: List<CategoryItem> = emptyList())