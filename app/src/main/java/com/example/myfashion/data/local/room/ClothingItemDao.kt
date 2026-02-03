package com.example.myfashion.data.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingItemDao {
    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ClothingItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ClothingItem>)

    // Read
    @Query("SELECT * FROM clothing_items ORDER BY addedAt DESC")
    fun getAllItems(): Flow<List<ClothingItem>>

    @Query("SELECT * FROM clothing_items WHERE id = :id")
    suspend fun getItemById(id: String): ClothingItem?

    @Query("SELECT * FROM clothing_items WHERE isFavorite = 1 ORDER BY addedAt DESC")
    fun getFavoriteItems(): Flow<List<ClothingItem>>

    @Query("SELECT * FROM clothing_items WHERE category = :category ORDER BY addedAt DESC")
    fun getItemsByCategory(category: String): Flow<List<ClothingItem>>

    @Query("SELECT DISTINCT category FROM clothing_items")
    suspend fun getAllCategories(): List<String>

    @Query("SELECT * FROM clothing_items WHERE name LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%' OR color LIKE '%' || :query || '%'")
    fun searchItems(query: String): Flow<List<ClothingItem>>

    // Update
    @Update
    suspend fun update(item: ClothingItem)

    @Query("UPDATE clothing_items SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)

    @Query("UPDATE clothing_items SET wearCount = wearCount + 1 WHERE id = :id")
    suspend fun incrementWearCount(id: String)

    @Query("UPDATE clothing_items SET wearCount = :newCount WHERE id = :id")
    suspend fun updateWearCount(id: String, newCount: Int)

    // Delete
    @Delete
    suspend fun delete(item: ClothingItem)

    @Query("DELETE FROM clothing_items WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM clothing_items")
    suspend fun deleteAll()
}