package com.example.edistynytmobiili.model

import com.google.gson.annotations.SerializedName

data class RentalItemsState(
    val list: List<RentalItem> = emptyList(),
    val loading: Boolean = false,
    val selectedItem: RentalItem = RentalItem(),
    val categoryName: String = "<Category name>",
    val errorMsg: String? = null
)
data class RentalItemState(
    val item: RentalItem = RentalItem(),
    val loading: Boolean = false,
    val errorMsg: String? = null
)

data class AddRentalItemState(
    val name: String = "",
    val loading: Boolean = false,
    val done: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String? = null
)

data class EditRentalItemState(
    val item: RentalItem = RentalItem(),
    val categoryId: Int = 0,
    val loading: Boolean = false,
    val done: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String? = null
)
data class DeleteRentalItemState(
    val id: Int = 0,
    val errorMsg: String? = null
)

data class RentalItem(
    @SerializedName("rental_item_id")
    val id: Int = 0,
    @SerializedName("rental_item_name")
    val name: String = "")

data class RentalItemsResponse(val items: List<RentalItem> = emptyList())
data class RentalItemResponse(
    val item: RentalItem = RentalItem(),
    @SerializedName("category_category")
    val category: CategoryItem = CategoryItem()
)

data class RentalItemStateRes(
    @SerializedName("rental_item_name")
    val name: String = "",
    @SerializedName("rental_item_state")
    val availability: String = ""
)
data class EditRentalItemReq(
    @SerializedName("rental_item_name")
    val name: String = ""
)
data class AddRentalItemReq(
    @SerializedName("rental_item_name")
    val name: String = "",
    @SerializedName("created_by_user_id")
    val userId: Int = 0
)
