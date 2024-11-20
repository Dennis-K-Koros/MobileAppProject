package com.example.mobileappproject.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileappproject.R

enum class LandingScreen(@StringRes val title: Int){
    LandingPage(title = R.string.app_name),
    SignUp(title = R.string.sign_up),
    LogIn(title = R.string.log_in),
    ResetPassword(title = R.string.reset_password)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreenAppBar(
    currentScreen: LandingScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun LandingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = painterResource(id = R.drawable.landingscreen)
        // Image
        Image(
            painter = image,
            contentDescription = stringResource(R.string.landing_screen_description),
            modifier = Modifier
                .width(250.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        // App Name
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Brief Description
        Text(
            text = stringResource(R.string.Brief_description),
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Buttons Row
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // Log In Button
            Button(onClick = {
                // Action for Log In
            }) {
                Text(text = stringResource(R.string.log_in))
            }

            // Sign Up Button
            Button(onClick = {
                // Action for Sign Up
            }) {
                Text(text = stringResource(R.string.sign_up))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    LandingScreen()
}
