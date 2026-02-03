package com.example.myfashion.presentasion.profile.ui_utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfashion.presentasion.navigation.NavRoutes

@Composable
fun ProfileActionsRow(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    // State for dialogs
    var showEditDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Edit Profile Card
            ActionCard(
                title = "Check Collection",
                icon = Icons.Default.Collections,
                iconColor = colorScheme.primary,
                backgroundColor = colorScheme.primary.copy(alpha = 0.1f),
                onClick = { navController.navigate(NavRoutes.CollectionScreen) },
                modifier = Modifier.weight(1f)
            )

            // Privacy Policy Card
            ActionCard(
                title = "Privacy Policy",
                icon = Icons.Default.Policy,
                iconColor = colorScheme.secondary,
                backgroundColor = colorScheme.secondary.copy(alpha = 0.1f),
                onClick = { showPrivacyDialog = true },
                modifier = Modifier.weight(1f)
            )

            // Settings Card
            ActionCard(
                title = "Settings",
                icon = Icons.Default.Settings,
                iconColor = colorScheme.onSurface,
                backgroundColor = colorScheme.tertiary.copy(alpha = 0.1f),
                onClick = { showSettingsDialog = true },
                modifier = Modifier.weight(1f)
            )

            // Change Theme Card
            ActionCard(
                title = "Change Theme",
                icon = Icons.Default.Brightness4,
                iconColor = colorScheme.error,
                backgroundColor = colorScheme.error.copy(alpha = 0.1f),
                onClick = { showThemeDialog = true },
                modifier = Modifier.weight(1f)
            )
        }}

    if (showPrivacyDialog) {
        PrivacyPolicyDialog(
            onDismiss = { showPrivacyDialog = false }
        )
    }

    if (showSettingsDialog) {
        SettingsDialog(
            onDismiss = { showSettingsDialog = false }
        )
    }

    if (showThemeDialog) {
        ThemeDialog(
            onDismiss = { showThemeDialog = false }
        )
    }
}

@Composable
fun ActionCard(
    title: String,
    icon: ImageVector,
    iconColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier= Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(
                onClick = onClick,
                // indication = rememberRipple(bounded = true)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                /*   .background(
                       color = iconColor.copy(alpha = 0.2f),
                       shape = CircleShape
                   )*/,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}
