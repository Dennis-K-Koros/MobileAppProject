package com.example.mobileappproject.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    isLoggedIn: Boolean,
    userName: String? = null,
    userEmail: String? = null,
    onMenuItemClick: (String) -> Unit,
    content: @Composable () -> Unit // Content of the screen
) {
    val drawerState = rememberDrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                if (isLoggedIn) {
                    // Show user details
                    Text(userName ?: "Unknown User")
                    Text(userEmail ?: "No Email")
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    // Show Login/Sign Up buttons
                    Button(onClick = { onMenuItemClick("Login") }) {
                        Text("Login")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onMenuItemClick("SignUp") }) {
                        Text("Sign Up")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Navigation options
                NavigationDrawerItem(label = { Text("Home") }, selected = false, onClick = { onMenuItemClick("Home") })
                NavigationDrawerItem(label = { Text("Categories") }, selected = false, onClick = { onMenuItemClick("Categories") })
                NavigationDrawerItem(label = { Text("Services") }, selected = false, onClick = { onMenuItemClick("Services") })
                NavigationDrawerItem(label = { Text("Orders") }, selected = false, onClick = { onMenuItemClick("Orders") })
                NavigationDrawerItem(label = { Text("Favourites") }, selected = false, onClick = { onMenuItemClick("Favourites") })
                NavigationDrawerItem(label = { Text("Profile") }, selected = false, onClick = { onMenuItemClick("Profile") })
                Spacer(modifier = Modifier.height(16.dp))

                if (isLoggedIn) {
                    NavigationDrawerItem(label = { Text("Log Out") }, selected = false, onClick = { onMenuItemClick("Logout") })
                }
            }
        },
        content = {
            content() // The screen content
        }
    )
}
