package com.example.myfashion.domain.model

import androidx.compose.ui.graphics.Color

// For 3D preview
data class WearOption(
    val id: String,
    val name: String,
    val iconRes: Int,
    val color: Color,
    val description: String
)