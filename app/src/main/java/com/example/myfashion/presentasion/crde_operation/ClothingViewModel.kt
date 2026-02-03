package com.example.myfashion.presentasion.crde_operation

import androidx.lifecycle.*
import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.data.repositoryImp.ClothingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class ClothingViewModel @Inject constructor(
    private val repository: ClothingRepository
): ViewModel() {

    // LiveData for all items
   private val _allItems: LiveData<List<ClothingItem>> = repository.getAllItems().asLiveData()
 val allItems =_allItems.asFlow()

    // LiveData for favorite items
    val favoriteItems: LiveData<List<ClothingItem>> = repository.getFavoriteItems().asLiveData()

    // Loading state
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // Error handling
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Search functionality
    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

   /* val searchResults: LiveData<List<ClothingItem>> = Transformations.switchMap(_searchQuery) { query ->
        if (query.isBlank()) {
            allItems
        } else {
            repository.searchItems(query).asLiveData()
        }
    }*/



    // Load all items
    fun loadData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                // Data is automatically loaded through Flow
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load items: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    // Add new clothing item
    fun addItem(item: ClothingItem) {
        viewModelScope.launch {
            try {
                repository.addItem(item)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add item: ${e.message}"
            }
        }
    }

    // Edit existing clothing item
    fun editItem(item: ClothingItem) {
        viewModelScope.launch {
            try {
                repository.editItem(item)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to edit item: ${e.message}"
            }
        }
    }

    // Delete clothing item
    fun deleteItem(item: ClothingItem) {
        viewModelScope.launch {
            try {
                repository.deleteItem(item)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete item: ${e.message}"
            }
        }
    }

    fun deleteItemById(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteItemById(id)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete item: ${e.message}"
            }
        }
    }

    // Toggle favorite status
    fun toggleFavorite(item: ClothingItem) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(item.id, item.isFavorite)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update favorite status: ${e.message}"
            }
        }
    }

    // Increment wear count
    fun incrementWearCount(item: ClothingItem) {
        viewModelScope.launch {
            try {
                repository.incrementWearCount(item.id)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update wear count: ${e.message}"
            }
        }
    }

    // Update wear count with specific value
    fun updateWearCount(itemId: String, newCount: Int) {
        viewModelScope.launch {
            try {
                repository.updateWearCount(itemId, newCount)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update wear count: ${e.message}"
            }
        }
    }

    // Get item by ID
    fun getItemById(id: String): LiveData<ClothingItem?> = liveData {
        try {
            val item = repository.getItemById(id)
            emit(item)
        } catch (e: Exception) {
            _errorMessage.postValue("Failed to get item: ${e.message}")
            emit(null)
        }
    }

    // Search items
    fun search(query: String) {
        _searchQuery.value = query
    }

    // Clear error message
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    // Get all categories
    suspend fun getAllCategories(): List<String> {
        return try {
            repository.getAllCategories()
        } catch (e: Exception) {
            _errorMessage.postValue("Failed to get categories: ${e.message}")
            emptyList()
        }
    }
}

/*
// ViewModel Factory
class ClothingViewModelFactory(private val repository: ClothingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClothingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClothingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
