package com.example.myfashion.presentasion

import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.domain.model.ClothItem
import com.example.myfashion.domain.model.Collection

sealed class FashionUiState {
    object Loading : FashionUiState()
    data class Success(
        val categories: List<Category>,
        val clothItems: List<ClothingItem>,
        val collections: List<Collection>,
        val selectedCategory: Category? = null,
        val selectedItem: ClothingItem? = null
    ) : FashionUiState()
    data class Error(val message: String) : FashionUiState()
}
