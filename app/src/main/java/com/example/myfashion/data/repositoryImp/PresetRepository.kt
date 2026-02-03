package com.example.myfashion.data.repositoryImp


import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.data.local.room.preset.Preset
import com.example.myfashion.data.local.room.preset.PresetDao
import com.example.myfashion.domain.model.PresetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PresetRepository @Inject constructor(
    private val presetDao: PresetDao,
    private val clothingRepository: ClothingRepository
) {

    // Get all presets
    fun getAllPresets(): Flow<List<PresetModel>> {
        return presetDao.getAllPresets()
            .map { presetEntities ->
                presetEntities.map { entity ->
                    toPresetModel(entity)
                }
            }
    }


    // Get preset by ID
    suspend fun getPresetById(id: Int): Preset? = presetDao.getPresetById(id)

    // Get favorite presets
    fun getFavoritePresets(): Flow<List<Preset>> = presetDao.getFavoritePresets()

    // Search presets
    fun searchPresets(query: String): Flow<List<Preset>> = presetDao.searchPresets(query)

    // Get presets by tag
    fun getPresetsByTag(tag: String): Flow<List<Preset>> = presetDao.getPresetsByTag(tag)

    // Get all tags from all presets
    suspend fun getAllTags(): List<String> {
        val allPresets = presetDao.getAllPresetsForTags()
        val allTags = mutableSetOf<String>()

        allPresets.forEach { preset ->
            allTags.addAll(preset.tags)
        }

        return allTags.toList().sorted()
    }
    // Create preset
    suspend fun createPreset(preset: Preset): Long = presetDao.insert(preset)

    suspend fun createPreset(
        name: String,
        items: List<String>,
        description: String? = null,
        tags: List<String> = emptyList()
    ): Long {
        val preset = Preset(
            name = name,
            items = items,
            description = description,
            tags = tags
        )
        return presetDao.insert(preset)
    }

    // Update preset
    suspend fun updatePreset(preset: Preset) = presetDao.update(preset)

    suspend fun updatePresetInfo(
        id: Int,
        name: String,
        description: String? = null
    ) = presetDao.updatePresetInfo(id, name, description)

    suspend fun updatePresetItems(id: Int, items: List<String>) =
        presetDao.updatePresetItems(id, items)

    suspend fun updatePresetTags(id: Int, tags: List<String>) =
        presetDao.updatePresetTags(id, tags)

    // Toggle favorite
    suspend fun toggleFavorite(id: Int, isCurrentlyFavorite: Boolean) =
        presetDao.updateFavoriteStatus(id, !isCurrentlyFavorite)

    // Delete preset
    suspend fun deletePreset(preset: Preset) = presetDao.delete(preset)

    suspend fun deletePresetById(id: Int) = presetDao.deleteById(id)

    // Add item to preset
    suspend fun addItemToPreset(presetId: Int, itemId: String) {
        val preset = getPresetById(presetId) ?: return
        val updatedItems = preset.items + itemId
        updatePresetItems(presetId, updatedItems)
    }

    // Remove item from preset
    suspend fun removeItemFromPreset(presetId: Int, itemId: String) {
        val preset = getPresetById(presetId) ?: return
        val updatedItems = preset.items.filter { it != itemId }
        updatePresetItems(presetId, updatedItems)
    }

    // Add tag to preset
    suspend fun addTagToPreset(presetId: Int, tag: String) {
        val preset = getPresetById(presetId) ?: return
        val updatedTags = preset.tags + tag
        updatePresetTags(presetId, updatedTags)
    }

    // Remove tag from preset
    suspend fun removeTagFromPreset(presetId: Int, tag: String) {
        val preset = getPresetById(presetId) ?: return
        val updatedTags = preset.tags.filter { it != tag }
        updatePresetTags(presetId, updatedTags)
    }

    // Check if preset contains item
    suspend fun presetContainsItem(presetId: Int, itemId: String): Boolean {
        val preset = getPresetById(presetId)
        return preset?.items?.contains(itemId) ?: false
    }

    // Get presets containing specific item
    fun getPresetsContainingItem(itemId: String): Flow<List<Preset>> {
        // Note: This requires a custom query. For simplicity, we'll filter in memory
        // In production, you might want to create a proper query
        throw NotImplementedError("Use ViewModel's filteredFlow instead")
    }


    // Get Item By itemId
   private suspend fun getItemById(itemId: String): ClothingItem? {
        val clothingItem = clothingRepository.getItemById(itemId)
        return clothingItem
    }

    suspend fun getItemsByIds(itemIds: List<String>): List<ClothingItem> {
        return itemIds.mapNotNull { itemId ->
            getItemById(itemId)
        }
    }

    private suspend fun toPresetModel(preset: Preset): PresetModel {

        val items=getItemsByIds(preset.items)

        return PresetModel(
            id = preset.id,
            name =preset.name ,
            items = items,
            createdAt =preset.createdAt,
            isFavorite =preset.isFavorite ,
            description =preset.description,
            tags = preset.tags
        )

    }

}