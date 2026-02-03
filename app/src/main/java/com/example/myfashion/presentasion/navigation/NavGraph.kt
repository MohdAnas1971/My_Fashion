package com.example.myfashion.presentasion.navigation
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myfashion.presentasion.FashionViewModel
import com.example.myfashion.presentasion.auth.LoginScreen
import com.example.myfashion.presentasion.auth.LoginSignUpOptionScreen
import com.example.myfashion.presentasion.category.CategoryScreen
import com.example.myfashion.presentasion.collection.AddEditPresetScreen
import com.example.myfashion.presentasion.collection.AddEditPresetViewModel
import com.example.myfashion.presentasion.collection.CollectionScreen
import com.example.myfashion.presentasion.crde_operation.AddItemScreen
import com.example.myfashion.presentasion.crde_operation.ClothingViewModel
import com.example.myfashion.presentasion.home.HomeScreen
import com.example.myfashion.presentasion.preview.PreviewScreen
import com.example.myfashion.presentasion.profile.ProfileScreen
import com.example.myfashion.presentasion.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavGraph() {

    val navController= rememberNavController()

    val fashionViewModel: FashionViewModel= hiltViewModel()

    val clothingViewModel: ClothingViewModel =hiltViewModel()

    val addEditPresetViewModel: AddEditPresetViewModel =hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SplashScreen
    ) {
        composable<NavRoutes.SplashScreen> {
            SplashScreen(navController)
        }

        composable<NavRoutes.LoginSignUpOptionScreen> {
            LoginSignUpOptionScreen(onLoginClick = {navController.navigate(NavRoutes.LoginScreen)}, onSignUpClick = {})
        }

        composable<NavRoutes.LoginScreen> {
            LoginScreen()
        }

        composable<NavRoutes.HomeScreen> {
            HomeScreen(viewModel = fashionViewModel,navController=navController)
        }

        composable<NavRoutes.CategoryScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<NavRoutes.CategoryScreen>()
            val categoryName = args.categoryName
            CategoryScreen(fashionViewModel,addEditPresetViewModel=addEditPresetViewModel, navController=navController, isSelectionOn = categoryName=="selection" )
        }

        composable<NavRoutes.CollectionScreen> {
            CollectionScreen( navController)
        }


        composable<NavRoutes.AddEditPresetScreen> {
            AddEditPresetScreen( navController=navController, addEditPresetViewModel = addEditPresetViewModel)
        }


        composable<NavRoutes.ProfileScreen> {
            ProfileScreen(navController=navController)
        }


        composable<NavRoutes.PreviewScreen> { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            if (itemId != null) {
                PreviewScreen(itemId, fashionViewModel, navController)
            }
        }

        composable < NavRoutes.AddItemScreen> {
            AddItemScreen(clothingViewModel, onBack = {navController.navigateUp()})
        }
    }
}
