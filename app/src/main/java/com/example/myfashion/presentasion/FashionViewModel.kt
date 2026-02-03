package com.example.myfashion.presentasion

// FashionViewModel.kt
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfashion.data.local.MockData
import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.data.repositoryImp.ClothingRepository
import com.example.myfashion.domain.model.ClothItem
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class FashionViewModel @Inject constructor(
    private val repository: ClothingRepository
) : ViewModel() {

    // UI State sealed class for different states
    sealed class FashionUiState {
        object Loading : FashionUiState()
        data class Success(
            val categories: List<Category>,
            val clothItems: List<ClothItem>,
            val collections: List<Collection>,
            val selectedCategory: Category? = null,
            val selectedItem: ClothingItem? = null
        ) : FashionUiState()
        data class Error(val message: String) : FashionUiState()
    }

    // Convert ClothingItem to ClothItem if needed
    private fun ClothingItem.toClothItem(): ClothItem {
        return ClothItem(
            id = this.id,
            name = this.name,
            category = this.category,
            imageUrl = this.imageUrl,
            isFavorite = this.isFavorite,
            color = this.color,
            brand = this.brand?:"Brand Null",
            price = this.price?:0.0,
            subCategory = this.subCategory?:"empty",
            purchaseDate = this.purchaseDate.toString(),
            model3dUrl = null,
            tags = this.tags,
            size = this.size ?: "Not Specify",
            material = this.material?:"Not Specify",
            wearCount = this.wearCount,
            lastWorn = null,
            // Add other mappings as needed
        )
    }

    private fun createCategoriesFromItems(items: List<ClothingItem>): List<Category> {
        // Extract unique categories from items
      /*  val uniqueCategories = items.map { it.category }.distinct()

        return listOf(Category( name = "All", iconRes = R.drawable.ic_all)) + uniqueCategories.map { categoryName ->
            Category(
                name = categoryName,
                iconRes = getCategoryIcon(categoryName) // You'll need to implement this
            )
        }*/

        return MockData.categories
    }

    private fun createCollections(items: List<ClothingItem>): List<Collection> {
        val favorites = items.filter { it.isFavorite }
        val recent = items.sortedByDescending { it.addedAt }.take(5)

        return listOf(
            Collection("Favorites", favorites.map { it.toClothItem() }),
            Collection("Recently Added", recent.map { it.toClothItem() })
        )
    }

    var uiState by mutableStateOf<FashionUiState>(FashionUiState.Loading)
        private set

    var selectedCategory by mutableStateOf<Category?>(null)

    var selectedClothItem by mutableStateOf<ClothingItem?>(null)
        private set

    init {
        loadInitialData()
    }


    private fun loadInitialData() {
        viewModelScope.launch {
            uiState = FashionUiState.Loading
            try {
                // Get all items from repository
                val allItems = repository.getAllItems()

                allItems.collect { clothingItems ->
                    // Convert ClothingItems to ClothItems for UI
                    val clothItems = clothingItems.map { it.toClothItem() }

                    // Create categories from actual data
                    val categories = createCategoriesFromItems(clothingItems)

                    // Create collections from actual data
                    val collections = createCollections(clothingItems)

                    uiState = FashionUiState.Success(
                        categories = categories,
                        clothItems = clothItems,
                        collections = collections,
                    )
                }
            } catch (e: Exception) {
                uiState = FashionUiState.Error("Failed to load data: ${e.message}")
            }
        }
    }

    /*private fun observeFavoriteItems() {
        viewModelScope.launch {
            repository.getFavoriteItems().collect { favorites ->
                // Update UI state with new favorites
                val currentState = uiState
                if (currentState is FashionUiState.Success) {
                    // Update cloth items with new favorite status
                    val updatedClothItems = currentState.clothItems.map { clothItem ->
                        val isFavorite = favorites.any { it.id == clothItem.id }
                        clothItem.copy(isFavorite = isFavorite)
                    }

                    // Update collections with new favorites
                    val updatedCollections = currentState.collections.map { collection ->
                        if (collection.name == "Favorites") {
                            collection.copy(items = favorites.map { it.toClothItem() })
                        } else {
                            collection
                        }
                    }

                    uiState = currentState.copy(
                        clothItems = updatedClothItems,
                        collections = updatedCollections
                    )
                }
            }
        }
    }
*/
    fun selectCategory(categoryName: String) {
        val currentState = uiState
        if (currentState is FashionUiState.Success) {
            selectedCategory = currentState.categories.find { it.name == categoryName }
            uiState = currentState.copy(selectedCategory = selectedCategory)
        }
    }

    fun selectClothingItem(item: ClothingItem) {
        selectedClothItem = item

        val currentState = uiState
        if (currentState is FashionUiState.Success) {
            uiState = currentState.copy(selectedItem = item)
        }
    }

    fun getClothingItemById(itemId: String): ClothItem? {
        val currentState = uiState
        return if (currentState is FashionUiState.Success) {
            currentState.clothItems.find { it.id == itemId }
        } else {
            null
        }
    }

    fun getItemCount(categoryName: String): Int {
        val currentState = uiState

        return if (currentState is FashionUiState.Success) {
            val allItems = currentState.clothItems
            if (categoryName == "All") {
                allItems.size
            } else {
                allItems.count { item -> item.category == categoryName }
            }
        } else {
            0
        }
    }

    fun toggleFavorite(itemId: String) {
        viewModelScope.launch {
            try {
                // Get current item to check if it's favorite
                val item = getClothingItemById(itemId)
                item?.let {
                    // Toggle favorite status in repository
                    repository.toggleFavorite(itemId, it.isFavorite)
                }
            } catch (e: Exception) {
                // Handle error
                // You might want to show a snackbar or update error state
            }
        }
    }

    fun addNewItem(item: ClothingItem) {
        viewModelScope.launch {
            try {
                repository.addItem(item)
                // The UI will automatically update through Flow observation
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun editItem(item: ClothingItem) {
        viewModelScope.launch {
            try {
                repository.editItem(item)
                // The UI will automatically update through Flow observation
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            try {
                repository.deleteItemById(itemId)
                // The UI will automatically update through Flow observation
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}





data class Category(
    val name: String,
    @DrawableRes val iconRes: Int
)

data class Collection(
    val name: String,
    val items: List<ClothItem>
)
