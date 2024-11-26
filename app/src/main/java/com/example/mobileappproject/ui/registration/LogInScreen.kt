package com.example.mobileappproject.ui.registration

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobileappproject.R
import com.example.mobileappproject.RegisTopAppBar
import com.example.mobileappproject.ui.navigation.NavigationDestination
import com.example.mobileappproject.ui.screens.HomeDestination
import com.example.mobileappproject.viewmodels.UserViewModel
import com.example.mobileappproject.viewmodels.Result



object LogInDestination : NavigationDestination {
    override val route = "login"
    override val titleRes = R.string.log_in
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit,
    navController: NavHostController,
    userViewModel: UserViewModel // Inject the ViewModel
) {
    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val loginState by userViewModel.loginResult.collectAsState() // Observe login result state
    val isLoading = loginState is Result.Loading
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RegisTopAppBar(
                title = stringResource(R.string.log_in),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hey,\nWelcome Back",
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = usernameOrEmail,
                onValueChange = { usernameOrEmail = it },
                label = { Text(stringResource(R.string.username_or_email)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(if (passwordVisible) "Hide" else "Show")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!isLoading) { // Ensure the button only responds if not already loading
                        Log.d("LoginScreen", "Attempting login for: $usernameOrEmail")
                        errorMessage = null
                        userViewModel.login(usernameOrEmail, password) { success ->
                            if (success) {
                                Log.d("LoginScreen", "Login successful, navigating to Home")
                                navController.navigate(HomeDestination.route) {
                                    popUpTo(LogInDestination.route) { inclusive = true }
                                }
                            }else
                                errorMessage = "Login failed. Incorrect password or email."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = usernameOrEmail.isNotBlank() && password.isNotBlank() && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp // Adjust stroke width for a more subtle indicator
                    )
                } else {
                    Text("Sign in")
                }
            }


            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onForgotPasswordClick) {
                Text("Forgot password?")
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun LogInScreenPreview() {
    //LoginScreen()
}