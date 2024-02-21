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
import androidx.compose.material.icons.filled.Home
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
import com.example.edistynytmobiili.view.LoginScreen
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
                                    onClick = {},
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Home,
                                            contentDescription = "Home"
                                        )
                                    })
                            }
                        },
                        ) {

                        NavHost(navController = navController, startDestination = "categoriesScreen") {
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
                                })
                                //Näin CategoriesScreenillä ei tarvitse olla erikseen tietoa drawerin tilasta, jota MainActivity hallinnoi.
                            }
                        }

                    }
                }
            }
        }
    }
}

