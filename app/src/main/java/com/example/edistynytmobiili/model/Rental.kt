package com.example.edistynytmobiili.model

import com.google.gson.annotations.SerializedName

data class RentalListState(
    val list: List<RentalItem> = emptyList(),
    val loading: Boolean = false,
    val showAddDialog: Boolean = false,
    val errorMsg: String? = null
)
data class RentalItemState(
    val item: RentalItem = RentalItem(),
    val loading: Boolean = false,
    val ok: Boolean = false,
    val errorMsg: String? = null
)

data class DeleteRentalItemState(
    val id: Int = 0,
    val errorMsg: String? = null
)

data class AddRentalItemState(
    val name: String = "",
    val errorMsg: String? = null
)

data class RentalItem(
    @SerializedName("rental_item_id")
    val id: Int = 0,
    @SerializedName("rental_item_name")
    val name: String = "")