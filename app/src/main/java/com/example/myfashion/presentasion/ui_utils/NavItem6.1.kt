package com.example.infittron.presentation.ui_utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.infittron.presentation.navigation.NavRoutes

// ✅ Data class for Nav Items
data class NavItem(
    val title: String,
    val icon: ImageVector,
    val route: NavRoutes
)
