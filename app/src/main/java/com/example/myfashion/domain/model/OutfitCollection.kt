package com.example.myfashion.domain.model

import com.example.myfashion.data.local.room.ClothingItem

data class OutfitCollection(
    val id: Int,
    val name: String,
    val items: List<ClothingItem>,
)






