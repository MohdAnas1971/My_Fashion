package com.example.myfashion.domain.model

data class ClothItem(
    val id: String,
    val name: String,
    val category: String,
    val subCategory: String,
    val color: String,
    val brand: String,
    val price: Double,
    val purchaseDate: String,
    val imageUrl: String,
    val model3dUrl: String?, // For 3D preview
    val tags: List<String>,
    val size: String,
    val material: String,
    val isFavorite: Boolean,
    val wearCount: Int,
    val lastWorn: String?
)