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
import com.example.edistynytmobiili.view.CategoriesScreen
import com.example.edistynytmobiili.view.EditCategoryScreen
import com.example.edistynytmobiili.view.LoginScreen
import com.example.edistynytmobiili.view.LogoutScreen
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
                                NavigationDrawerItem(
                                    label = { Text(text = "Login")},
                                    selected = false,
                                    onClick = { scope.launch {
                                        navController.navigate("loginScreen")
                                        drawerState.close()} },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Lock,
                                            contentDescription = "Login"
                                        )
                                    })
                                NavigationDrawerItem(
                                    label = { Text(text = "Logout")},
                                    selected = false,
                                    onClick = { scope.launch {
                                        navController.navigate("logoutScreen")
                                        drawerState.close()} },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.ExitToApp,
                                            contentDescription = "Logout"
                                        )
                                    })

                            }
                        },
                        ) {

                            NavHost(navController = navController, startDestination = "loginScreen") {
                                //Navigoinnin mahdollistamiseksi NavHostiin tulee lisätä kohteille composablet
                                composable(route = "categoriesScreen") {
                                    /*onMenuClick-callbackillä menu iconia painamalla avataan drawer.
                                    Käytännössä CategoriesScreenin koodissa tehty "Click-listener" kutsuu seuraavaa toimenpidettä
                                    ikonia painaessa:
                                     */
                                    CategoriesScreen(onMenuClick = {
                                        scope.launch {
                                            /*
                                            if (drawerState.isClosed) {
                                                drawerState.open()
                                            }
                                            else {
                                                drawerState.close()
                                            }
                                             */
                                            //Voidaan lyhentää .apply:llä
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    },  //Näin CategoriesScreenillä ei tarvitse olla erikseen tietoa drawerin tilasta, jota MainActivity hallinnoi.

                                        //Navigoinnissa voidaan välittää parametri lisäämällä routen perään kenoviiva ja itse parametri.
                                        goToEditCategory = { navController.navigate("editCategoryScreen/${it}") }
                                    )

                                }

                                //Composable loginScreenin navigointireittiä varten
                                composable(route = "loginScreen") {
                                    /*
                                    LoginScreenille annetaan lambda-argumentti, jolla käytännössä välitetään navigointiohjeet sille.
                                    Tällä callback-funktiolla Nav Controlleria ei tarvitse erikseen määritellä tai kutsua LoginScreenin koodissa,
                                    sillä ohjeet välitetään tämän kautta.
                                    */
                                    LoginScreen(goToCategories = { navController.navigate("categoriesScreen") })
                                }
                                //Parametriä vastaanottavalle routelle lisätään "wild card" merkintä routen perään
                                //Tällä tunnisteella vastaanottavassa ViewModelissa haetaan parametri savedStateHandlesta.
                                composable(route = "editCategoryScreen/{categoryId}") {
                                EditCategoryScreen(
                                    backToCategories = { navController.navigateUp() },
                                    goToCategories = { navController.navigate("categoriesScreen") }
                                )

                                }

                                composable(route = "logoutScreen") {
                                    LogoutScreen(goToLogin = {navController.navigate("loginScreen") {
                                        popUpTo("loginScreen") {
                                            inclusive = true
                                        }
                                    } },
                                        cancelLogout = {navController.navigateUp()})
                                }



                            }

                        }
                }
            }
        }
    }
}

