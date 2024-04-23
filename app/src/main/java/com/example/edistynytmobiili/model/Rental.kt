package com.example.edistynytmobiili.model


import com.google.gson.annotations.SerializedName

data class RentalItemsState(
    val list: List<RentalItem> = emptyList(),
    val loading: Boolean = false,
    val categoryName: String = "",
    val errorMsg: String? = null
)
data class RentalState(
    val rentItemId: Int = 0,
    val openItem: RentalItem = RentalItem(),
    val selectedItem: RentalItem = RentalItem(),
)

data class AddRentalItemState(
    val name: String = "",
    val loading: Boolean = false,
    val done: Boolean = false,
    val errorMsg: String? = null
)

data class EditRentalItemState(
    val item: RentalItem = RentalItem(),
    val categoryId: Int = 0,
    val loading: Boolean = false,
    val done: Boolean = false,
    val errorMsg: String? = null
)
data class DeleteRentalItemState(
    val id: Int = 0,
    val errorMsg: String? = null
)
data class RentalSummaryState(
    val itemName: String = "",
    val categoryName: String = "",
    val loading: Boolean = false,
    val errorMsg: String? = null
)
data class RentalItem(
    @SerializedName("rental_item_id")
    val id: Int = 0,
    @SerializedName("rental_item_name")
    val name: String = "",
    val isFree: Boolean = true
)

data class RentalItemsResponse(val items: List<RentalItem> = emptyList())

data class RentalItemStatusRes(
    @SerializedName("rental_item_name")
    val name: String = "",
    @SerializedName("rental_item_state_rental_item_state")
    val rentalStatus: RentalItemStatus = RentalItemStatus(),
    @SerializedName("category_category")
    val category: CategoryItem = CategoryItem()
)
data class RentalItemStatus(
    @SerializedName("rental_item_state_id")
    val statusId: Int = 0,
    @SerializedName("rental_item_state")
    val statusName: String = ""
)
data class EditRentalItemReq(
    @SerializedName("rental_item_name")
    val name: String = ""
)
data class EditRentalItemRes(
    val item: RentalItem = RentalItem(),
    @SerializedName("category_category")
    val category: CategoryItem = CategoryItem(),
    @SerializedName("created_by_user")
    val user: AuthUser = AuthUser(),

)
data class AddRentalItemReq(
    @SerializedName("rental_item_name")
    val name: String = "",
    @SerializedName("created_by_user_id")
    val userId: Int? = null
)

data class RentRentalItemReq(
    @SerializedName("auth_user_auth_user_id")
    val userId: Int? = null
)
