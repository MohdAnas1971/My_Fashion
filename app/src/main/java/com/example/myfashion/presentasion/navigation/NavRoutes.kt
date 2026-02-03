package com.example.infittron.presentation.navigation
import kotlinx.serialization.Serializable

sealed class NavRoutes {

    @Serializable
    object SplashScreen : NavRoutes()

    @Serializable
    object LoginSignUpOptionScreen: NavRoutes()

    @Serializable
    object LoginScreen : NavRoutes()

    @Serializable
    object SignUpScreen : NavRoutes()

    @Serializable
    object HomeScreen : NavRoutes()

    @Serializable
    data class CategoryScreen(val categoryName: String): NavRoutes()

    @Serializable
    object CollectionScreen : NavRoutes()

    @Serializable
    object AddEditPresetScreen : NavRoutes()


    @Serializable
    object WardrobeScreen : NavRoutes()


    @Serializable
    object ProfileScreen : NavRoutes()

    @Serializable
    object ArScreen : NavRoutes()

    @Serializable
    data class PreviewScreen(val itemId: String) : NavRoutes()

    //CRED Screens

    @Serializable
    object  AddItemScreen : NavRoutes()

}
