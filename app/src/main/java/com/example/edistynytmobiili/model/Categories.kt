package com.example.edistynytmobiili.model

data class CategoriesState (
    val list: List<CategoryItem> = emptyList(),
    val loading: Boolean = false
)

data class CategoryItem(val id: Int = 0, val name: String = "")