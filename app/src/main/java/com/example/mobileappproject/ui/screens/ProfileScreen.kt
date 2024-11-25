package com.example.mobileappproject.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import com.example.mobileappproject.R
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.mobileappproject.KaziHubTopAppBar
import com.example.mobileappproject.ui.components.AppDrawer
import com.example.mobileappproject.ui.navigation.NavigationDestination
import com.example.mobileappproject.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object ProfileDestination : NavigationDestination {
    override val route = "profile"
    override val titleRes = R.string.profile
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    AppDrawer(
        navController = navController,
        userViewModel = userViewModel,
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                KaziHubTopAppBar(
                    title = stringResource(R.string.orders),
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    navigateUp = { navController.popBackStack() },
                    onCartClick = {
                        navController.navigate("shoppingCart")
                    },
                    onMenuClick = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Image
                Image(
                    painter = painterResource(id = R.drawable.service_placeholder),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                        .clip(CircleShape)
                )

                // Name and Email
                Text(text = "JohnDoe", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Johndoe@gmail.com", fontSize = 14.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(24.dp))

                // Menu Items
                ProfileMenuItem(icon = Icons.Default.Person, text = "Update Profile")
                ProfileMenuItem(icon = Icons.Default.Lock, text = "Security")
                ProfileMenuItem(icon = Icons.Default.Favorite, text = "Favorites")
                ProfileMenuItem(icon = Icons.Default.Star, text = "Reviews")
                ProfileMenuItem(icon = Icons.Default.Settings, text = "App Settings")

                Spacer(modifier = Modifier.height(24.dp))

                // Delete Account
                Text(
                    text = "Delete Account",
                    style = TextStyle(color = Color.Red, fontSize = 16.sp),
                    modifier = Modifier.clickable { /* Handle delete action */ }
                )
            }
        }

    }
}

@Composable
fun ProfileMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle menu click */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp, color = Color.Black)
    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    //ProfileScreen()
}

