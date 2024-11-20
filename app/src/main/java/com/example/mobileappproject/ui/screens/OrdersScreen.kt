package com.example.mobileappproject.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun OrdersScreen(
    isLoggedIn: Boolean,
    userEmail: String?,
    userName: String?,
    onMenuItemClick: (String) -> Unit,
    onServiceClick: (String) -> Unit
) {
    val tabs = listOf("Current", "Closed")
    val coroutineScope = rememberCoroutineScope()
    val selectedIndex = remember { androidx.compose.runtime.mutableStateOf(0) }

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
                        text = "No Current Orders",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    1 -> Text(
                        text = "No Closed Orders",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
