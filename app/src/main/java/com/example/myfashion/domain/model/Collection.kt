package com.example.myfashion.domain.model

import androidx.compose.ui.graphics.Color

data class Collection(
    val id: String,
    val name: String,
    val description: String?=null,
    val itemCount: Int,
    val coverImage: String?=null,
    val gradientColors: List<Color> ?= null,
    val createdDate: String?=null
)