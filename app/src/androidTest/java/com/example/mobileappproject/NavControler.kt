package com.example.mobileappproject

import androidx.navigation.NavController
import org.junit.Assert.assertEquals

fun NavController.assertCurrentRouteName(expectedRoute: String) {
    val currentRoute = currentBackStackEntry?.destination?.route
    assertEquals("Expected route: $expectedRoute, but was $currentRoute", expectedRoute, currentRoute)
}
