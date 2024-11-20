package com.example.mobileappproject.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mobileappproject.ui.components.AppDrawer

@Composable
fun FavoritesScreen(
    isLoggedIn: Boolean,
    userEmail: String?,
    userName: String?,
    onMenuItemClick: (String) -> Unit,
) {
    val tabs = listOf("Services", "Products")
    val selectedIndex = remember { androidx.compose.runtime.mutableStateOf(0) }

    AppDrawer(
        isLoggedIn = isLoggedIn,
        userName = userName,
        userEmail = userEmail,
        onMenuItemClick = onMenuItemClick
    ){
        androidx.compose.material3.Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                // Tabs
                TabRow(selectedTabIndex = selectedIndex.value) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedIndex.value == index,
                            onClick = { selectedIndex.value = index },
                            text = { androidx.compose.material3.Text(text = title) }
                        )
                    }
                }

                // Content
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when (selectedIndex.value) {
                        0 -> Text(
                            text = "No Favorite Services",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        1 -> Text(
                            text = "No Favorite Products",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


