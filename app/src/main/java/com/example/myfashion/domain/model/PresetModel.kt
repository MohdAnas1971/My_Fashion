package com.example.myfashion.domain.model

import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.data.local.room.preset.Preset

data class PresetModel(
    val id: Int,
    val name: String,
    val items: List<ClothingItem>, // List of clothing items
    val createdAt: Long,
    val isFavorite: Boolean = false,
    val description: String? = null,
    val tags: List<String> = emptyList()
)