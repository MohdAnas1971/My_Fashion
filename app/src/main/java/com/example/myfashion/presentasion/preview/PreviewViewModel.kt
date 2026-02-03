package com.example.myfashion.presentasion.preview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.myfashion.data.local.MockData
import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.data.repositoryImp.ClothingRepository
import com.example.myfashion.domain.model.WearOption
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val repository: ClothingRepository
): ViewModel() {

    private val _allItems: LiveData<List<ClothingItem>> = repository.getAllItems().asLiveData()
    val allItems =_allItems.asFlow()

    val wearOption = MockData.wearOptions
    var currentWearOption by mutableStateOf<WearOption?>(null)
        private set

    fun selectWearOption(option: WearOption) {
        currentWearOption = option
        // Here you would update the 3D model based on selection
    }

}