package com.example.edistynytmobiili

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.edistynytmobiili.ui.theme.EdistynytMobiiliTheme
import com.example.edistynytmobiili.view.AddCategoryScreen
import com.example.edistynytmobiili.view.CategoriesScreen
import com.example.edistynytmobiili.view.EditCategoryScreen
import com.example.edistynytmobiili.view.LoginScreen
import com.example.edistynytmobiili.view.LogoutScreen
import com.example.edistynytmobiili.view.RegistrationScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdistynytMobiiliTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val navController = rememberNavController()
                    //Modaali-ikkuna tulee päänäkymän päälle. Puhelimissa järkevä käyttää tätä.
                    ModalNavigationDrawer(
                        //Asetetaan navigation drawerille tila (auki/kiinni)
                        drawerState = drawerState,
                        /*Käytetään saatavilla olevaa default paddingiä, jotta itemit asettuvat sopusuhtaisesti
                        laitteesta riippumatta.
                         */
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        //Drawerin sisältö
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = "Categories")},
                                    //Oletusikkunassa selected = true
                                    selected = true,
                                    onClick = { scope.launch { drawerState.close()}},
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Home,
                                            contentDescription = "Home"
                                        )
                                    })
                                /*
                                NavigationDrawerItem(
                                    label = { Text(text = "Login")},
                                    selected = false,
                                    onClick = { scope.launch {
                                        navController.navigate("loginScreen")
                                        drawerState.close()} },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Login,
                                            contentDescription = "Login"
                                        )
                                    })

                                 */
                                NavigationDrawerItem(
                                    label = { Text(text = "Logout")},
                                    selected = false,
                                    onClick = { scope.launch {
                                        navController.navigate("logoutScreen")
                                        drawerState.close()} },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Logout,
                                            contentDescription = "Logout"
                                        )
                                    })

                            }
                        },
                        ) {
                            NavHost(navController = navController, startDestination = Screen.Login.route) {
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
                                        goToAddCategory = {navController.navigate(Screen.AddCategory.route)}
                                    )

                                }
                                composable(route = Screen.Login.route) {
                                    LoginScreen(
                                        goToCategories = { navController.navigate(Screen.Categories.route)},
                                        goToRegistration = { navController.navigate(Screen.Registration.route)}
                                    )
                                }
                                composable(route = Screen.EditCategory.route) {
                                EditCategoryScreen(
                                    onCancel = { navController.navigateUp() },
                                    goToCategories = { navController.navigate(Screen.Categories.route) }
                                )
                                }
                                composable(route = Screen.Logout.route) {
                                    LogoutScreen(
                                        goToLogin = {
                                            navController.navigate(Screen.Login.route) {
                                                popUpTo(Screen.Login.route) { inclusive = true }
                                            }
                                        },
                                        cancelLogout = {navController.navigateUp()},
                                        exitApp = {finish()}
                                    )
                                }

                                composable(route = Screen.Registration.route) {
                                    RegistrationScreen(goToLogin = {navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    } })
                                }

                                composable(route = Screen.AddCategory.route) {
                                    AddCategoryScreen(
                                        onDone = { navController.navigate(Screen.Categories.route) },
                                        onCancel = { navController.navigateUp() }
                                    )
                                }



                            }

                        }
                }
            }
        }
    }
}

