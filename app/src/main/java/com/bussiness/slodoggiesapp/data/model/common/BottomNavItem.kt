package com.bussiness.slodoggiesapp.data.model.common

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val route: String,
    val selectedIcon: Int? = null
)


