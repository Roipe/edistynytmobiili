package com.example.edistynytmobiili

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.edistynytmobiili.components.NavigationComponents
import com.example.edistynytmobiili.ui.theme.EdistynytMobiiliTheme
import com.example.edistynytmobiili.view.AddCategoryScreen
import com.example.edistynytmobiili.view.CategoriesScreen
import com.example.edistynytmobiili.view.EditCategoryScreen
import com.example.edistynytmobiili.view.LoginScreen
import com.example.edistynytmobiili.view.LogoutConfirmationDialog
import com.example.edistynytmobiili.view.LogoutScreen
import com.example.edistynytmobiili.view.RegistrationScreen
import com.example.edistynytmobiili.view.RentalItemsScreen
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
                    val currentBackStackEntry by navController.currentBackStackEntryAsState()


                    NavigationComponents(
                        drawerState = drawerState,
                        scope = scope,
                        navController = navController,
                        currentBackStackEntry = currentBackStackEntry,

                        )

                }
            }
        }
    }
}


