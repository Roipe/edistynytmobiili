package com.example.edistynytmobiili.components


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState

import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.edistynytmobiili.Screen

import com.example.edistynytmobiili.menuScreens
import com.example.edistynytmobiili.view.AddCategoryScreen
import com.example.edistynytmobiili.view.AddRentalItemScreen
import com.example.edistynytmobiili.view.CategoriesScreen
import com.example.edistynytmobiili.view.EditCategoryScreen
import com.example.edistynytmobiili.view.EditRentalItemScreen
import com.example.edistynytmobiili.view.LoginScreen
import com.example.edistynytmobiili.view.LogoutConfirmationDialog
import com.example.edistynytmobiili.view.LogoutScreen
import com.example.edistynytmobiili.view.RegistrationScreen
import com.example.edistynytmobiili.view.RentalItemsScreen
import com.example.edistynytmobiili.view.RentalSummaryScreen
import com.example.edistynytmobiili.viewmodel.NavigationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationComponents(
    drawerState : DrawerState,
    scope : CoroutineScope,
    navController: NavHostController,
    currentBackStackEntry: NavBackStackEntry?
) {

    val vm: NavigationViewModel = viewModel()
    //toggle, jotta navigation drawer ei ole käytössä login-screenissä.
    if (vm.drawerEnabledState.value.status) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding),
            drawerContent = {
                DrawerSheet(onItemClick = { route ->
                    navController.navigate(route)
                    scope.launch { drawerState.close() } },
                    destinationRoute = currentBackStackEntry?.destination?.route?: ""
                )
            },
        ) {
            NavigationHost(
                drawerState = drawerState,
                scope = scope,
                navController = navController,
                toggleDrawerEnable = { vm.toggleDrawerEnable() }
            )
        }
    }
    else
        NavigationHost(
            drawerState = drawerState,
            scope = scope,
            navController = navController,
            toggleDrawerEnable = { vm.toggleDrawerEnable() }
        )

}
@Composable
fun NavigationHost(
    drawerState : DrawerState,
    scope : CoroutineScope,
    navController: NavHostController,
    toggleDrawerEnable: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route) {

        composable(route = Screen.Login.route) {
            LoginScreen(
                goToCategories = {
                    toggleDrawerEnable()
                    navController.navigate(Screen.Categories.route)
                 },
                goToRegistration = { navController.navigate(Screen.Registration.route)}
            )
        }

        composable(route = Screen.LogoutDialog.route) {
            LogoutConfirmationDialog(
                onConfirm = { toggleDrawerEnable()
                    navController.navigate(Screen.Logout.route) {
                    popUpTo(Screen.Logout.route) { inclusive = true }
                    }
                },
                onCancel = { navController.navigateUp() }
            )
        }

        composable(route = Screen.Logout.route) {
            BackHandler(onBack = { })
            LogoutScreen(
                goToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Registration.route) {
            RegistrationScreen(goToLogin = {navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }})
        }

        composable(route = Screen.Categories.route) {
            CategoriesScreen(onMenuClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            },
                //Screen-luokan EditCategory-objektilla on "routeWithId"-funktio, jolla toteutetaan parametrin välitys
                goToEditCategory = { navController.navigate(Screen.EditCategory.routeWithId(it)) },
                goToAddCategory = { navController.navigate(Screen.AddCategory.route) },
                goToRentalItems = { navController.navigate(Screen.RentalItems.routeWithId(it)) }
            )
        }

        composable(route = Screen.EditCategory.route) {
            EditCategoryScreen(
                onCancel = { navController.navigateUp() },
                goToCategories = { navController.navigate(Screen.Categories.route) }
            )
        }

        composable(route = Screen.AddCategory.route) {
            AddCategoryScreen(
                onDone = { navController.navigate(Screen.Categories.route) },
                onCancel = { navController.navigateUp() }
            )
        }

        composable(route = Screen.RentalItems.route) {
            RentalItemsScreen(
                onRentComplete = {navController.navigate(Screen.RentalSummary.routeWithId(it)) },
                goToAddItem = { navController.navigate(Screen.AddRentalItem.routeWithId(it)) },
                goToEditItem = { navController.navigate(Screen.EditRentalItem.routeWithId(it)) },
                onBack = { navController.navigate(Screen.Categories.route) },
            )
        }
        composable(route = Screen.EditRentalItem.route) {
            EditRentalItemScreen(
                onDone = { navController.navigate(Screen.RentalItems.routeWithId(it)) },
                onCancel = {navController.navigateUp()}
            )
        }

        composable(route = Screen.AddRentalItem.route) {
            AddRentalItemScreen(
                onDone = { navController.navigate(Screen.RentalItems.routeWithId(it)) },
                onCancel = {navController.navigateUp()}
            )
        }

        composable(route = Screen.RentalSummary.route) {
            RentalSummaryScreen(
                goToCategories = { navController.navigate(Screen.Categories.route) },
                onMenuClick = { scope.launch {
                    drawerState.apply { if (isClosed) open() else close() }
                }}
            )
        }
    }
}


@Composable
fun DrawerSheet(
    onItemClick: (String) -> Unit = {},
    destinationRoute: String
) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(16.dp))
        menuScreens.forEach { item ->
            NavigationDrawerItem(
                label = { Text(text = item.title) },
                selected = item.route == destinationRoute,
                onClick = { onItemClick(item.route) },
                icon = { item.icon?.let {
                    Icon(imageVector = it, contentDescription = item.title)
                }}
            )
        }
    }
}

@Composable
fun DirectingText(
    directingText: String, textButtonText: String,
    onClick : () -> Unit, fontSize: TextUnit = 14.sp
) {
    Row (verticalAlignment = Alignment.CenterVertically){
        Text(text = directingText, fontSize = fontSize)
        TextButton(onClick = { onClick() }) {
            Text(text = textButtonText, fontSize = fontSize)
        }
    }
}



