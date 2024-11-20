package com.example.mobileappproject.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileappproject.R
import com.example.mobileappproject.ui.components.AppDrawer
import kotlinx.coroutines.launch

enum class HomeScreen(@StringRes val title: Int){
    Home(title = R.string.app_name),
    Categories(title = R.string.categories),
    Services(title = R.string.services),
    Orders(title = R.string.orders),
    Favourites(title = R.string.favourites),
    Profile(title = R.string.profile),
    SignUp(title = R.string.sign_up),
    LogIn(title = R.string.log_in),
    ResetPassword(title = R.string.reset_password)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    currentScreen: HomeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onShoppingCartClick: () -> Unit, // Add this parameter
    onMenuClick: () -> Unit, // Add this parameter
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(currentScreen.title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            // Shopping Cart Icon
            IconButton(onClick = onShoppingCartClick) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                    contentDescription = "Shopping Cart"
                )
            }
            // Menu Icon
            IconButton(onClick = onMenuClick) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_more),
                    contentDescription = "Menu"
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
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
fun HomeScreenApp(){
    //Create Nav Controller
    val navController = rememberNavController()
    //Create current back stack Entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    //Get the name of the current screen
    val currentScreen = HomeScreen.valueOf(
        backStackEntry?.destination?.route ?: HomeScreen.Home.name
    )

    Scaffold(
        topBar = {
            HomeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                onShoppingCartClick = { /* Handle shopping cart action */ },
                onMenuClick = { /* Handle menu click action */ }
            )
        }
    ) { innerPadding ->
        // TODO: Navigation host
        NavHost(
            navController = navController,
            startDestination = HomeScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ){}

    }
}

@Composable
fun HomeScreen(
    isLoggedIn: Boolean,
    userEmail: String?,
    userName: String?,
    onMenuItemClick: (String) -> Unit,
    onServiceClick: (String) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // Modal Navigation Drawer
    AppDrawer(
        isLoggedIn = isLoggedIn,
        userName = userName,
        userEmail = userEmail,
        onMenuItemClick = onMenuItemClick
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_mylocation),
                        contentDescription = "Location",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select Location")
                    TextButton(onClick = { /* Change location functionality */ }) {
                        Text("Change")
                    }
                }
                val coroutineScope = rememberCoroutineScope() // Remember a coroutine scope
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_sort_by_size),
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            coroutineScope.launch {
                                if (!drawerState.isOpen) drawerState.open() else drawerState.close()
                            }
                        }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            var searchQuery by remember { mutableStateOf("") }
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                    .padding(8.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                items(listOf("Carpet Cleaning", "Sofa Set Cleaning")) { service ->
                    ServiceCard(service) { onServiceClick(service) }
                }
            }
        }
    }

}

@Composable
fun ServiceCard(serviceName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(serviceName, fontSize = 14.sp)
        }
    }
}
