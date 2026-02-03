package com.example.myfashion.presentasion.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myfashion.presentasion.navigation.NavRoutes
import com.example.myfashion.R
import kotlinx.coroutines.delay


@Composable
 fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit){
        delay(300)
        navController.navigate(NavRoutes.HomeScreen) {
            popUpTo(NavRoutes.SplashScreen) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = "App Logo",
            Modifier.size(180.dp).background(color = Color(0xFF161414),
                shape = CircleShape)
        )
    }
}