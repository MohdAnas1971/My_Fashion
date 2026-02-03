package com.example.myfashion.data.repositoryImp

import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.data.local.room.ClothingItemDao
import kotlinx.coroutines.flow.Flow

class ClothingRepository(private val clothingItemDao: ClothingItemDao) {

    // Get all items
    fun getAllItems(): Flow<List<ClothingItem>> = clothingItemDao.getAllItems()

    // Get item by ID
    suspend fun getItemById(id: String): ClothingItem? = clothingItemDao.getItemById(id)

    // Get favorite items
    fun getFavoriteItems(): Flow<List<ClothingItem>> = clothingItemDao.getFavoriteItems()

    // Get items by category
    fun getItemsByCategory(category: String): Flow<List<ClothingItem>> =
        clothingItemDao.getItemsByCategory(category)

    // Get all categories
    suspend fun getAllCategories(): List<String> = clothingItemDao.getAllCategories()

    // Search items
    fun searchItems(query: String): Flow<List<ClothingItem>> = clothingItemDao.searchItems(query)

    // Add new item
    suspend fun addItem(item: ClothingItem) {
        clothingItemDao.insert(item)
    }

    // Edit existing item
    suspend fun editItem(item: ClothingItem) {
        clothingItemDao.update(item)
    }

    // Delete item
    suspend fun deleteItem(item: ClothingItem) {
        clothingItemDao.delete(item)
    }

    suspend fun deleteItemById(id: String) {
        clothingItemDao.deleteById(id)
    }

    // Toggle favorite status
    suspend fun toggleFavorite(id: String, isCurrentlyFavorite: Boolean) {
        clothingItemDao.updateFavoriteStatus(id, !isCurrentlyFavorite)
    }

    // Increment wear count
    suspend fun incrementWearCount(id: String) {
        clothingItemDao.incrementWearCount(id)
    }

    // Update wear count
    suspend fun updateWearCount(id: String, newCount: Int) {
        clothingItemDao.updateWearCount(id, newCount)
    }

    // Add multiple items
    suspend fun addItems(items: List<ClothingItem>) {
        clothingItemDao.insertAll(items)
    }

    // Clear all items
    suspend fun clearAll() {
        clothingItemDao.deleteAll()
    }
}
