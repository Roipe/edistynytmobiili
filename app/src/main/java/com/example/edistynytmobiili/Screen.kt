package com.example.edistynytmobiili

sealed class Screen (val route: String) {
    data object Login: Screen("loginScreen")
    data object Logout: Screen("logoutScreen")
    data object Registration: Screen("registrationScreen")
    data object Categories: Screen("categoriesScreen")
    data object EditCategory: Screen("editCategoryScreen/{categoryId}") {
        fun routeWithId(id: Int) : String {
            return "editCategoryScreen/$id"
        }
    }
    data object CategoryList: Screen("categoryListScreen")
}