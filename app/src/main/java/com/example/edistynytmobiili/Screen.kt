package com.example.edistynytmobiili

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val route: String, val title: String = "", val icon: ImageVector? = null) {
    data object Login: Screen(route = "loginScreen")
    data object Logout: Screen(route = "logoutScreen")
    data object LogoutDialog: Screen(route = "logoutDialog", title = "Logout", icon = Icons.Filled.Logout)
    data object Registration: Screen(route = "registrationScreen")
    data object Categories: Screen(route = "categoriesScreen", title = "Categories", icon = Icons.Filled.Home)
    data object EditCategory: Screen(route = "editCategoryScreen/{categoryId}") {
        fun routeWithId(id: Int) : String {
            return "editCategoryScreen/$id"
        }
    }
    data object AddCategory: Screen(route = "addCategoryScreen")
    data object RentalItems: Screen(route = "rentalItemsScreen/{categoryId}") {
        fun routeWithId(id: Int) : String {
            return "rentalItemsScreen/$id"
        }
    }

}
internal val menuScreens = listOf(
    Screen.Categories,
    Screen.LogoutDialog
)
