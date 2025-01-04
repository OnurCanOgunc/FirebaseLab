package com.decode.firebaselab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.decode.firebaselab.ui.auth.AuthScreen
import com.decode.firebaselab.ui.login.LoginScreen
import com.decode.firebaselab.ui.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.Auth, modifier = modifier) {
        composable<Screens.Auth> {
            AuthScreen(
                navigateToLogin = { navController.navigate(Screens.Login) },
                navigateToSignUp = { navController.navigate(Screens.SignUp) }
            )
        }
        composable<Screens.Login> {
            LoginScreen()
        }
        composable<Screens.SignUp> {
            SignUpScreen()
        }
    }
}

sealed interface Screens {
    @Serializable
    object Login : Screens

    @Serializable
    object SignUp : Screens

    @Serializable
    object Auth : Screens
}