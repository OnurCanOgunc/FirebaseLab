package com.decode.firebaselab.ui.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.decode.firebaselab.R
import com.decode.firebaselab.ui.auth.component.CustomButton
import com.decode.firebaselab.ui.theme.black
import com.decode.firebaselab.ui.theme.black2
import com.decode.firebaselab.ui.theme.green

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit = {},
    navigateToSignUp: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.portraits),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, black),
                    )
                )
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Millions of songs. \nFree on Spotify.",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = { navigateToSignUp() },
                colors = ButtonDefaults.buttonColors(containerColor = green),
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp)
            ) {
                Text(
                    text = "Sign up free",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = black2
                )
            }
            CustomButton(
                modifier = Modifier.clickable {  },
                title = "Continue with Google",
                painter = painterResource(R.drawable.google)
            )
            CustomButton(
                modifier = Modifier.clickable {  },
                title = "Continue with Facebook",
                painter = painterResource(R.drawable.facebook)
            )
            Text(
                text = "Log In",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(24.dp)
                    .clickable { navigateToLogin()  },
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun AuthScreenPreview(modifier: Modifier = Modifier) {
    AuthScreen(modifier)
}