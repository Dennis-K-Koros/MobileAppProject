package com.example.mobileappproject.ui.categories

import androidx.lifecycle.ViewModel
import com.example.mobileappproject.R
import com.example.mobileappproject.data.CategoryItem

class CategoriesViewModel : ViewModel() {
    val categories = listOf(
        CategoryItem(1, "Motor Vehicle Mechanic", "Automotive", R.drawable.landingscreen),
        CategoryItem(2, "Body works", "Automotive", R.drawable.landingscreen),
        CategoryItem(3, "Home Cleaning", "Cleaning", R.drawable.landingscreen),
        CategoryItem(4, "Plumbing", "Construction", R.drawable.landingscreen),
        CategoryItem(5, "Painting", "Construction", R.drawable.landingscreen),
        CategoryItem(6, "Word working", "Construction", R.drawable.landingscreen),
        CategoryItem(7, "Flooring", "Construction", R.drawable.landingscreen),
        CategoryItem(8, "Skin Aesthetics", "Beauty", R.drawable.landingscreen),
        CategoryItem(9, "Makeup", "Beauty", R.drawable.landingscreen)
    )
}