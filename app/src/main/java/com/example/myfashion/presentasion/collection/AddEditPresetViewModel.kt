package com.example.myfashion.presentasion.collection

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.data.local.room.preset.Preset
import com.example.myfashion.data.repositoryImp.PresetRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditPresetViewModel @Inject constructor(
    private val repository: PresetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val presetId: Int? = savedStateHandle["presetId"]

    private val _uiState = MutableStateFlow(AddEditPresetUiState())
    val uiState = _uiState.asStateFlow()

    private val _selectedItems = MutableStateFlow<List<ClothingItem>>(emptyList())
    val selectedItems = _selectedItems.asStateFlow()


    private val _selectedItemsIds = mutableStateListOf<String>()
    val selectedItemsIds: List<String> = _selectedItemsIds


    fun toggleSelection(item: ClothingItem) {
        Log.d("AddEditPresetViewModel","seItem: ${_selectedItems.value.size}")

        val current = _selectedItems.value.toMutableList()

        if (current.any { it.id == item.id }) {
            current.removeAll { it.id == item.id }
        } else {
            current.add(item)
        }
        _selectedItems.value = current
        Log.d("AddEditPresetViewModel"," after seItem: ${_selectedItems.value.size}")

    }

    fun isItemSelected(itemId: String): Boolean {
        return _selectedItems.value.any { it.id == itemId }
    }

    fun clearSelection() {
        _selectedItems.value = emptyList()
    }


    init {
        if (presetId != null) {
            loadPreset(presetId)
        }
    }

    private fun loadPreset(presetId: Int) {
        viewModelScope.launch {
            repository.getPresetById(presetId)?.let { preset ->
                _uiState.update { it.copy(
                    name = preset.name,
                    description = preset.description,
                    tags = preset.tags,
                    isFavorite = preset.isFavorite
                ) }
                // Load items from IDs
                val items = repository.getItemsByIds(preset.items)
                _selectedItems.value = items
            }
        }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description.ifBlank { null }) }
    }

    fun addTag(tag: String) {
        if (tag.isNotBlank() && !_uiState.value.tags.contains(tag)) {
            _uiState.update { it.copy(tags = it.tags + tag) }
        }
    }

    fun removeTag(tag: String) {
        _uiState.update { it.copy(tags = it.tags - tag) }
    }

    fun toggleFavorite() {
        _uiState.update { it.copy(isFavorite = !it.isFavorite) }
    }

    fun updateSelectedItems(items: List<ClothingItem>) {
        _selectedItems.value = items
    }
    fun isFormValid(): Boolean {
        return _uiState.value.name.isNotBlank()
    }

    fun savePreset() {
        if (!isFormValid()) return

        val preset = Preset(
            id = presetId ?: 0,
            name = _uiState.value.name,
            description = _uiState.value.description,
            items = _selectedItems.value.map { it.id },
            tags = _uiState.value.tags,
            isFavorite = _uiState.value.isFavorite,
            createdAt = if (presetId == null) System.currentTimeMillis() else _uiState.value.createdAt
        )

        viewModelScope.launch {
            if (presetId == null) {
                repository.createPreset(preset)
            } else {
                repository.updatePreset(preset)
            }
            // Navigate back
        }
    }
}

data class AddEditPresetUiState(
    val name: String = "",
    val description: String? = null,
    val tags: List<String> = emptyList(),
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)