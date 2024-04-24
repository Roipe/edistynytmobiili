package com.example.edistynytmobiili

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.edistynytmobiili.components.NavigationComponents
import com.example.edistynytmobiili.ui.theme.EdistynytMobiiliTheme

class MainActivity : ComponentActivity() {
    val windowSizeInfo = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EdistynytMobiiliTheme {
                val windowSizeInfo = rememberWindowSize()
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
                        windowSizeInfo = windowSizeInfo

                        )

                }
            }
        }
    }
}


