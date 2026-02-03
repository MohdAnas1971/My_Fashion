package com.example.myfashion.presentasion.profile


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfashion.presentasion.FashionUiState
import com.example.myfashion.presentasion.FashionViewModel
import com.example.myfashion.presentasion.ThemeViewModel
import com.example.myfashion.presentasion.profile.ui_utils.ProfileActionsRow
import com.example.myfashion.presentasion.profile.ui_utils.ProfileHeaderSection
import com.example.myfashion.presentasion.profile.ui_utils.ProfileTopBar
import com.example.myfashion.presentasion.profile.ui_utils.RecentItemsSection
import com.example.myfashion.presentasion.profile.ui_utils.UserStatsSection
import com.example.myfashion.presentasion.ui_utils.AppBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(

    navController: NavController
) {
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val profileViewModel: ProfileViewModel=hiltViewModel()

    val uiState by profileViewModel.uiState.collectAsState()

    val allItems by profileViewModel.allItems.collectAsState(emptyList())

    val userStats by remember { derivedStateOf {
        mapOf(
            "Total Items" to allItems.size,
            "Outfits Created" to uiState.createdOutfitsCount,
            "Favorites" to uiState.favoriteItems.size,
            "Style Points" to 1250
        )
    } }

    Log.d("ProfileViewModel","allItems:${allItems.size}")

    val recentItems = allItems.sortedByDescending { it.addedAt }.take(6)

    LaunchedEffect(allItems) {

        profileViewModel.updateRecentItems()
    }

    Scaffold(
        topBar = {
            ProfileTopBar(navController)
        },
        bottomBar = {
            AppBottomBar(navController,"Profile")
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // Profile Header with Photo
            ProfileHeaderSection()

            Spacer(modifier = Modifier.height(32.dp))

            // User Statistics
            UserStatsSection(userStats)

            Spacer(modifier = Modifier.height(32.dp))

            // Quick Actions
            ProfileActionsRow(navController)

            Spacer(modifier = Modifier.height(32.dp))
            // Recent Items Section
            if (uiState.isLoading){
                CircularProgressIndicator()
            }else{
                RecentItemsSection(uiState.recentItems, navController)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


