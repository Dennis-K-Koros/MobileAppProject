package com.example.mobileappproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileappproject.ui.components.AppDrawer


@Composable
fun CategoriesScreen(
    isLoggedIn: Boolean,
    userName: String?,
    userEmail: String?,
    onMenuItemClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onViewMoreClick: (String) -> Unit
) {
    AppDrawer(
        isLoggedIn = isLoggedIn,
        userName = userName,
        userEmail = userEmail,
        onMenuItemClick = onMenuItemClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            var searchQuery by remember { mutableStateOf("") }

            // Search bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_search),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Categories list
            LazyColumn {
                items(listOf("Painting", "Construction")) { category ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onCategoryClick(category) },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { onViewMoreClick(category) }) {
                            Text("View More")
                        }
                    }
                }
            }
        }
    }
}
