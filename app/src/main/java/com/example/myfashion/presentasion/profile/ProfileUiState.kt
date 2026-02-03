package com.example.myfashion.presentasion.profile

import com.example.myfashion.data.local.room.ClothingItem

data class ProfileUiState (
    val isLoading: Boolean =false,
    val recentItems: List<ClothingItem> =emptyList(),
    val allItems: List<ClothingItem> =emptyList(),
    val favoriteItems: List<ClothingItem> =emptyList(),
    val createdOutfitsCount:Int =0,
    val error: String=""
)
