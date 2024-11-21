package com.example.mobileappproject.ui.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileappproject.R
import com.example.mobileappproject.ui.navigation.NavigationDestination


object SignUpDestination : NavigationDestination {
    override val route = "signUp"
    override val titleRes = R.string.sign_up
}


@Composable
fun SignUpScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Arrow
        IconButton(onClick = { /* Handle back action */ }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back_button),
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Title
        Text(
            text = stringResource(R.string.sign_up_message),
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input Fields
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Username Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle username input */ },
                label = { Text(stringResource(R.string.user_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Email Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle email input */ },
                label = { Text(stringResource(R.string.user_email)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Phone Number Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle phone input */ },
                label = { Text(stringResource(R.string.phone)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            // Password Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle password input */ },
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Confirm Password Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle confirm password input */ },
                label = { Text(stringResource(R.string.conf)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Register Button
        Button(
            onClick = { /* Handle register action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.register))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}