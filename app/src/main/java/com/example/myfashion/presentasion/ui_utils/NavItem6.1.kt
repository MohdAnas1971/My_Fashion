package com.example.myfashion.presentasion.ui_utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myfashion.presentasion.navigation.NavRoutes

// ✅ Data class for Nav Items
data class NavItem(
    val title: String,
    val icon: ImageVector,
    val route: NavRoutes
)
