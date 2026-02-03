package com.example.myfashion.presentasion.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.data.repositoryImp.ClothingRepository
import com.example.myfashion.data.repositoryImp.PresetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class ProfileViewModel @Inject constructor(
    repository: ClothingRepository,
    presetRepository: PresetRepository
) : ViewModel() {

    // LiveData for all items
    private val _allItems: LiveData<List<ClothingItem>> = repository.getAllItems().asLiveData()
    val allItems =_allItems.asFlow()

    // LiveData for favorite items
    private  val _favoriteItems: LiveData<List<ClothingItem>> = repository.getFavoriteItems().asLiveData()

     val allPresets = presetRepository.getAllPresets()

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState



    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        _uiState.value = _uiState.value.copy(
            isLoading=true,
        )

        viewModelScope.launch {
            allPresets.collect {presets->

                _uiState.value =_uiState.value.copy(
                    createdOutfitsCount = presets.size
                )
            }
            _uiState.value = _uiState.value.copy(
                isLoading=false,
                allItems = _allItems.value?:emptyList(),
                favoriteItems=_favoriteItems.value?:emptyList(),
            )
            updateRecentItems()
        }


    }


    fun updateRecentItems(){

        // 🔹 Mock / Local data for now
        val recentItems = _allItems.value?.sortedByDescending { it.addedAt }?.take(6)?:emptyList()

        Log.d("ProfileViewModel","_allItems:${_allItems.value?.size}")

        Log.d("ProfileViewModel","recentItem:${recentItems.size}")

        _uiState.value = _uiState.value.copy(
            recentItems = recentItems,
        )

    }
}
