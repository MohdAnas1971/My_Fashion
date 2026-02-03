package com.example.myfashion.data.local.room.preset


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {

    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preset: Preset): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(presets: List<Preset>)

    // Read
    @Query("SELECT * FROM presets ORDER BY createdAt DESC")
    fun getAllPresets(): Flow<List<Preset>>

    @Query("SELECT * FROM presets WHERE id = :id")
    suspend fun getPresetById(id: Int): Preset?

    @Query("SELECT * FROM presets WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoritePresets(): Flow<List<Preset>>

    @Query("SELECT * FROM presets WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchPresets(query: String): Flow<List<Preset>>

    @Query("SELECT * FROM presets WHERE :tag IN (tags)")
    fun getPresetsByTag(tag: String): Flow<List<Preset>>

    // Get all presets (non-Flow version for tag extraction)
    @Query("SELECT * FROM presets")
    suspend fun getAllPresetsForTags(): List<Preset>

    // Update
    @Update
    suspend fun update(preset: Preset)

    @Query("UPDATE presets SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("UPDATE presets SET name = :name, description = :description WHERE id = :id")
    suspend fun updatePresetInfo(id: Int, name: String, description: String? = null)

    @Query("UPDATE presets SET items = :items WHERE id = :id")
    suspend fun updatePresetItems(id: Int, items: List<String>)

    @Query("UPDATE presets SET tags = :tags WHERE id = :id")
    suspend fun updatePresetTags(id: Int, tags: List<String>)

    // Delete
    @Delete
    suspend fun delete(preset: Preset)

    @Query("DELETE FROM presets WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM presets")
    suspend fun deleteAll()
}