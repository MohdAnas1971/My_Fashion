package com.example.myfashion.presentasion.ui_utils

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SettingsOverscan
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfashion.presentasion.navigation.NavRoutes


@Composable
fun AppBottomBar(
    navController: NavController,
    route: String
) {

    val navItems = listOf(
        NavItem("Home", Icons.Outlined.Home, NavRoutes.HomeScreen),
        NavItem("Collection", Icons.Outlined.Collections, NavRoutes.CollectionScreen),
        NavItem("Try AR", Icons.Outlined.SettingsOverscan, NavRoutes.PreviewScreen("")),
        NavItem("Profile", Icons.Outlined.AccountCircle, NavRoutes.ProfileScreen),
    )

    val cs = MaterialTheme.colorScheme

    // Theme-based gradients (replaces hard-coded gradients)
    val gradientColors = listOf(
        listOf(cs.primary, cs.primaryContainer),
        listOf(cs.primary, cs.surface),
        listOf(cs.primary, cs.onBackground),
        listOf(cs.primary, cs.background)
    )

    NavigationBar(
        containerColor = cs.surface,
        tonalElevation = 0.dp,
        modifier = Modifier
            .shadow(
                elevation = 25.dp,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                clip = true,
                spotColor = cs.primary.copy(alpha = 0.3f)
            )
    ) {
        navItems.forEachIndexed { index, item ->

            val isSelected = item.title == route

            val iconBgColor =
                if (isSelected)
                    Brush.linearGradient(gradientColors[index % gradientColors.size])
                else
                    Brush.linearGradient(
                        listOf(
                            cs.surfaceVariant.copy(alpha = 0.8f),
                            cs.surfaceVariant.copy(alpha = 0.8f)
                        )
                    )

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                   /* Box(
                        modifier = Modifier
                            .size(if (isSelected) 50.dp else 40.dp)
                            .clip(CircleShape)
                            .background(iconBgColor)
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected)
                                    cs.onPrimary.copy(alpha = 0.3f)
                                else
                                    cs.outline.copy(alpha = 0.2f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {*/
                        Icon(
                            item.icon,
                            contentDescription = item.title,
                            tint = if (isSelected)
                                Color.Black
                            else
                                Color.Gray,
                            modifier = Modifier.size(if (isSelected) 28.dp else 22.dp)
                        )
                   // }
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected)
                            cs.onSurface
                        else
                            cs.onSurfaceVariant
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Transparent,
                    selectedTextColor = cs.onSurface,
                    unselectedIconColor = Color.Transparent,
                    unselectedTextColor = cs.onSurfaceVariant,
                    indicatorColor = Color.Transparent
                ),
            )
        }
    }
}
