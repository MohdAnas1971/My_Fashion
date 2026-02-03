package com.example.myfashion.presentasion.collection

import com.example.myfashion.data.local.room.preset.Preset
import com.example.myfashion.domain.model.PresetModel

// You can create more detailed UI state models if needed
data class PresetUiState(
    val presets: List<PresetModel> = emptyList(),
    val favoritePresets: List<Preset> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedPreset: Preset? = null,
    val selectedTag: String? = null,
    val availableTags: List<String> = emptyList(),
    val isCreatingPreset: Boolean = false,
    val isEditingPreset: Boolean = false
) {
    val filteredPresets: List<PresetModel>
        get() {
            var filtered = presets

            // Apply search filter
            if (searchQuery.isNotBlank()) {
                filtered = filtered.filter { preset ->
                    preset.name.contains(searchQuery, ignoreCase = true) ||
                            preset.description?.contains(searchQuery, ignoreCase = true) == true ||
                            preset.tags.any { it.contains(searchQuery, ignoreCase = true) }
                }
            }

            // Apply tag filter
            selectedTag?.let { tag ->
                filtered = filtered.filter { preset ->
                    preset.tags.contains(tag)
                }
            }

            return filtered
        }

    val hasError: Boolean get() = error != null
    val isEmpty: Boolean get() = filteredPresets.isEmpty() && !isLoading && error == null
}

// Actions that can be performed on the preset screen
sealed class PresetAction {
    data class CreatePreset(
        val name: String,
        val items: List<String>,
        val description: String? = null,
        val tags: List<String> = emptyList()
    ) : PresetAction()
    data class UpdatePreset(val preset: Preset) : PresetAction()
    data class DeletePreset(val presetId: Int) : PresetAction()
    data class ToggleFavorite(val presetId: Int, val isCurrentlyFavorite: Boolean) : PresetAction()
    data class Search(val query: String) : PresetAction()
    data class SelectTag(val tag: String?) : PresetAction()
    object ClearSearch : PresetAction()
    object ClearTagFilter : PresetAction()
    object Refresh : PresetAction()
    data class AddItemToPreset(val presetId: Int, val itemId: String) : PresetAction()
    data class RemoveItemFromPreset(val presetId: Int, val itemId: String) : PresetAction()
}




