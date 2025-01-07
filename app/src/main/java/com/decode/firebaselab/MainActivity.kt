package com.decode.firebaselab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.decode.firebaselab.ui.theme.FirebaseLabTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            FirebaseLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        auth = auth,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
            val currentUser = auth.currentUser
            currentUser?.let {
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(Screens.Home(currentUser.displayName ?: "")) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                    //auth.signOut()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.clickable {
            throw RuntimeException("Test Crash")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirebaseLabTheme {

    }
}