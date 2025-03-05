package com.example.remoconmouse

import android.os.Bundle
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.remoconmouse.ui.HomeScreen
import com.example.remoconmouse.ui.MouseScreen
import com.example.remoconmouse.data.NetworkRepository

object ServerData {
    val serverManager = NetworkRepository()
}

class MainActivity : ComponentActivity() {
    private val serverManager = ServerData.serverManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "homeScreen") {
                composable(route = "homeScreen") {
                    HomeScreen(onNavigateMouse = { navController.navigate("mouseScreen") } )
                }
                composable(route = "mouseScreen") {
                    MouseScreen(onNavigateHome = { navController.navigate("homeScreen") } )
                }
            }
        }
    }
}
