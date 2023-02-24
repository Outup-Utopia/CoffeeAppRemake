package dev.outup.coffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.api.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.outup.coffeeapp.screen.AddContentScreen
import dev.outup.coffeeapp.screen.ContentScreen
import dev.outup.coffeeapp.ui.theme.CoffeeAppTheme
import dev.outup.coffeeapp.screen.LoginScreen
import dev.outup.coffeeapp.screen.SignUpScreen

class MainActivity : ComponentActivity() {
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            CoffeeAppTheme {
                App()
            }
        }
    }

    @Composable
    fun App() {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable(route = "login") {
                    LoginScreen({
                        navController.navigate("signUp")
                    }, {
                        navController.navigate("content")
                    })

                }
                composable(route = "signUp") {
                    SignUpScreen({
                        navController.navigate("login")
                    }, {
                        navController.navigate("content")
                    })
                }
                composable(route = "content") {
                    ContentScreen({
                        navController.navigate("login")
                    }, {
                        navController.navigate("addContent")
                    })
                }
                composable(route = "addContent") {
                    AddContentScreen()
                }
            }
        }
    }
}