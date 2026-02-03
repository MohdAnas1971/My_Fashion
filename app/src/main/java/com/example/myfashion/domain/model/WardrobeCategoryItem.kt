package com.example.myfashion.domain.model

import androidx.compose.ui.graphics.Color




data class WardrobeCategoryItem(
    val id: String,
    val name: String,
    val itemCount: Int,
    val iconRes: Int,
    val backgroundColor: Color,
    val innerColor: Color
)





