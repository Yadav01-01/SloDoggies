package com.bussiness.slodoggiesapp.model

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val route: String,
    val selectedIcon: Int? = null
)


