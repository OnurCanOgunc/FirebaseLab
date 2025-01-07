package com.decode.firebaselab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.decode.firebaselab.ui.auth.AuthScreen
import com.decode.firebaselab.ui.home.HomeScreen
import com.decode.firebaselab.ui.login.LoginScreen
import com.decode.firebaselab.ui.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    auth: FirebaseAuth
) {
    NavHost(navController = navController, startDestination = Screens.Auth, modifier = modifier) {
        composable<Screens.Auth> {
            AuthScreen(
                navigateToLogin = { navController.navigate(Screens.Login) },
                navigateToSignUp = { navController.navigate(Screens.SignUp) }
            )
        }
        composable<Screens.Login> {
            LoginScreen(auth = auth, navigateToAuth = { navController.navigate(Screens.Auth)})
        }
        composable<Screens.SignUp> {
            SignUpScreen(auth = auth, navigateToAuth = { navController.navigate(Screens.Auth)})
        }
        composable<Screens.Home> { backStackEntry ->
            val argument = backStackEntry.toRoute<Screens.Home>()
            HomeScreen(name = argument.name)
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

    @Serializable
    data class Home(val name: String) : Screens
}