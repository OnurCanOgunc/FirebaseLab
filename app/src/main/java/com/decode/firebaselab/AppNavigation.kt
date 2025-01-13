package com.decode.firebaselab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.decode.firebaselab.data.auth.AuthenticationManager
import com.decode.firebaselab.data.db.DataManager
import com.decode.firebaselab.ui.auth.AuthScreen
import com.decode.firebaselab.ui.home.HomeScreen
import com.decode.firebaselab.ui.home.HomeViewModel
import com.decode.firebaselab.ui.login.LoginScreen
import com.decode.firebaselab.ui.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authenticationManager: AuthenticationManager,
    dataManager: DataManager
) {
    NavHost(navController = navController, startDestination = Screens.Auth, modifier = modifier) {
        composable<Screens.Auth> {
            AuthScreen(
                authManager = authenticationManager,
                navigateToLogin = { navController.navigate(Screens.Login) },
                navigateToSignUp = { navController.navigate(Screens.SignUp) },
                navigateToHome = { navController.navigate(Screens.Home("Home")) }
            )
        }
        composable<Screens.Login> {
            LoginScreen(
                authManager = authenticationManager,
                navigateToAuth = { navController.navigate(Screens.Auth) },
                navigateToHome = { navController.navigate(Screens.Home("Home")) })
        }
        composable<Screens.SignUp> {
            SignUpScreen(
                authManager = authenticationManager,
                navigateToAuth = { navController.navigate(Screens.Auth) },
                navigateToHome = { navController.navigate(Screens.Home(it)) })
        }
        composable<Screens.Home> { backStackEntry ->
            val argument = backStackEntry.toRoute<Screens.Home>()
            val viewModel = HomeViewModel(dataManager = dataManager)
            HomeScreen(name = argument.name, homeViewModel = viewModel)
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