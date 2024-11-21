package com.example.mobileappproject.ui.home

import androidx.lifecycle.ViewModel
import com.example.mobileappproject.R
import com.example.mobileappproject.data.ServiceItem



class HomeViewModel : ViewModel() {
    val services = listOf(
        ServiceItem(1, "Office Painting", "Painting Services", R.drawable.landingscreen),
        ServiceItem(2, "Home Painting", "Painting Services", R.drawable.landingscreen),
        ServiceItem(3, "Car Painting", "Painting Services", R.drawable.landingscreen),
        ServiceItem(4, "House Painting", "Painting Services", R.drawable.landingscreen),
        ServiceItem(5, "Pipe Repair", "Plumbing Services", R.drawable.landingscreen),
        ServiceItem(6, "Leak Fixing", "Plumbing Services", R.drawable.landingscreen),
        ServiceItem(7, "Leak Fixing", "Construction Services", R.drawable.landingscreen),
        ServiceItem(8, "Reattachment", "Hair Salon Services", R.drawable.landingscreen),
        ServiceItem(9, "BodyGuard", "Security Services", R.drawable.landingscreen)
    )
}

