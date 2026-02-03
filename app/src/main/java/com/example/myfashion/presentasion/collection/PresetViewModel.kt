package com.example.myfashion.presentasion.collection


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfashion.data.local.room.preset.Preset
import com.example.myfashion.data.repositoryImp.PresetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PresetViewModel @Inject constructor(
    private val presetRepository: PresetRepository
) : ViewModel() {

    // Private mutable state
    private val _uiState = MutableStateFlow(
        PresetUiState(isLoading = true)
    )
    // Public state
    val uiState: StateFlow<PresetUiState> = _uiState.asStateFlow()

    // Private flows for data
    private val allPresetsFlow = presetRepository.getAllPresets()
    private val favoritePresetsFlow = presetRepository.getFavoritePresets()

    init {
        loadInitialData()
        observeData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                val tags = presetRepository.getAllTags()
                _uiState.update { it.copy(availableTags = tags) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to load tags: ${e.message}") }
            }
        }
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(
                allPresetsFlow,
                favoritePresetsFlow
            ) { allPresets, favorites ->
                _uiState.update { currentState ->
                    currentState.copy(
                        presets = allPresets,
                        favoritePresets = favorites,
                        isLoading = false,
                        error = null
                    )
                }
            }.catch { throwable ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = "Failed to load presets: ${throwable.message}"
                    )
                }
            }.collect()
        }
    }

    // Actions
    fun searchPresets(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectTag(tag: String?) {
        _uiState.update { it.copy(selectedTag = tag) }
    }

    fun clearSearch() {
        _uiState.update { it.copy(searchQuery = "") }
    }

    fun clearTagFilter() {
        _uiState.update { it.copy(selectedTag = null) }
    }

    fun createPreset(
        name: String,
        items: List<String>,
        description: String? = null,
        tags: List<String> = emptyList()
    ) {
        viewModelScope.launch {
            try {
                presetRepository.createPreset(name, items, description, tags)
                // Reload tags
                val updatedTags = presetRepository.getAllTags()
                _uiState.update { it.copy(availableTags = updatedTags) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to create preset: ${e.message}") }
            }
        }
    }

    fun updatePreset(preset: Preset) {
        viewModelScope.launch {
            try {
                presetRepository.updatePreset(preset)
                // Reload tags
                val updatedTags = presetRepository.getAllTags()
                _uiState.update { it.copy(availableTags = updatedTags) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update preset: ${e.message}") }
            }
        }
    }

    fun deletePreset(presetId: Int) {
        viewModelScope.launch {
            try {
                presetRepository.deletePresetById(presetId)
                // Reload tags
                val updatedTags = presetRepository.getAllTags()
                _uiState.update { it.copy(availableTags = updatedTags) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to delete preset: ${e.message}") }
            }
        }
    }

    fun toggleFavorite(presetId: Int, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch {
            try {
                presetRepository.toggleFavorite(presetId, isCurrentlyFavorite)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update favorite: ${e.message}") }
            }
        }
    }



    fun addItemToPreset(presetId: Int, itemId: String) {
        viewModelScope.launch {
            try {
                presetRepository.addItemToPreset(presetId, itemId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to add item: ${e.message}") }
            }
        }
    }

    fun removeItemFromPreset(presetId: Int, itemId: String) {
        viewModelScope.launch {
            try {
                presetRepository.removeItemFromPreset(presetId, itemId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to remove item: ${e.message}") }
            }
        }
    }

    fun addTagToPreset(presetId: Int, tag: String) {
        viewModelScope.launch {
            try {
                presetRepository.addTagToPreset(presetId, tag)
                // Reload tags
                val updatedTags = presetRepository.getAllTags()
                _uiState.update { it.copy(availableTags = updatedTags) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to add tag: ${e.message}") }
            }
        }
    }

    fun removeTagFromPreset(presetId: Int, tag: String) {
        viewModelScope.launch {
            try {
                presetRepository.removeTagFromPreset(presetId, tag)
                // Reload tags
                val updatedTags = presetRepository.getAllTags()
                _uiState.update { it.copy(availableTags = updatedTags) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to remove tag: ${e.message}") }
            }
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        loadInitialData()
    }

    fun setCreatingPreset(isCreating: Boolean) {
        _uiState.update { it.copy(isCreatingPreset = isCreating) }
    }

    fun setEditingPreset(isEditing: Boolean) {
        _uiState.update { it.copy(isEditingPreset = isEditing) }
    }

    suspend fun getPresetById(presetId: Int): Preset? {
        return try {
            presetRepository.getPresetById(presetId)
        } catch (e: Exception) {
            _uiState.update { it.copy(error = "Failed to get preset: ${e.message}") }
            null
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
