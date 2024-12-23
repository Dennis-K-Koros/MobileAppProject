package com.example.mobileappproject.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobileappproject.R
import com.example.mobileappproject.ui.screens.CategoriesDestination
import com.example.mobileappproject.ui.screens.FavouritesDestination
import com.example.mobileappproject.ui.screens.HomeDestination
import com.example.mobileappproject.ui.registration.LogInDestination
import com.example.mobileappproject.ui.screens.OrdersDestination
import com.example.mobileappproject.ui.screens.ServicesDestination
import com.example.mobileappproject.ui.registration.SignUpDestination
import com.example.mobileappproject.ui.screens.ProfileDestination
import com.example.mobileappproject.viewmodels.UserViewModel


@Composable
fun AppDrawer(
    userViewModel: UserViewModel,
    navController: NavHostController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val isLoggedIn by userViewModel.isLoggedIn.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()

    Log.d("AppDrawer", "Recomposed: isLoggedIn: $isLoggedIn, currentUser: $currentUser")

    Box {
        // Drawer content
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.75f) // Adjust drawer width (75% of the screen width)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    // User Info
                    if (isLoggedIn) {
                        // If logged in, display user details
                        val userName = currentUser?.username
                        val userEmail = currentUser?.email
                        Log.d("AppDrawer", "Logged-in user - Username: $userName, Email: $userEmail")
                        UserHeader(userName = userName, userEmail = userEmail)
                    } else {
                        // If not logged in, show login and signup buttons
                        UserHeader(userName = null, userEmail = null) // Show guest info
                        Log.d("AppDrawer", "No user logged in")
                        Spacer(modifier = Modifier.height(16.dp))
                        LoginSignupButtons(navController = navController)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Navigation Items
                    DrawerItems(navController = navController, isLoggedIn = isLoggedIn)
                }
            },
            content = { content() } // Main screen content
        )
    }
}

@Composable
fun LoginSignupButtons(navController: NavHostController) {
    // Login Button
    Button(
        onClick = {
            navController.navigate(LogInDestination.route)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = "Login", color = MaterialTheme.colorScheme.onPrimary)
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Signup Button
    Button(
        onClick = {
            navController.navigate(SignUpDestination.route)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = "Sign Up", color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun DrawerItems(navController: NavHostController, isLoggedIn: Boolean) {
    val drawerItems = listOf(
        Pair(Icons.Default.Home, "Home"),
        Pair(Icons.Default.List, "Categories"),
        Pair(Icons.Default.Build, "Services"),
        Pair(Icons.Default.DateRange, "Orders"),
        Pair(Icons.Default.Favorite, "Favourites"),
        Pair(Icons.Default.Person, "Profile")
    )

    drawerItems.forEachIndexed { index, item ->
        NavigationDrawerItem(
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = item.first,
                        contentDescription = item.second,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(item.second)
                }
            },
            selected = false,
            onClick = {
                val route = when (item.second) {
                    "Home" -> HomeDestination.route
                    "Categories" -> CategoriesDestination.route
                    "Services" -> ServicesDestination.route
                    "Orders" -> OrdersDestination.route
                    "Favourites" -> FavouritesDestination.route
                    "Profile" -> {
                        if (isLoggedIn) ProfileDestination.route else LogInDestination.route
                    }
                    else -> HomeDestination.route
                }
                navController.navigate(route)
            }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Log out option
    if (isLoggedIn) {
        NavigationDrawerItem(
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Log Out",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text("Log Out")
                }
            },
            selected = false,
            onClick = {
                // Handle logout logic, navigate to Login screen if necessary
                navController.navigate(LogInDestination.route)
            }
        )
    }
}




@Composable
fun UserHeader(userName: String?, userEmail: String?) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = userName ?: stringResource(R.string.guest),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = userEmail ?: stringResource(R.string.no_email),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun DrawerItem(label: String, onClick: () -> Unit) {
    NavigationDrawerItem(
        label = { Text(label, style = MaterialTheme.typography.bodyLarge) },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
