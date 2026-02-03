package com.example.myfashion.presentasion

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.example.myfashion.domain.repository.ThemeRepository
import kotlinx.coroutines.launch

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: ThemeRepository
) : ViewModel() {

    val isDarkTheme = repository.isDarkTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    fun toggleTheme(isDark: Boolean) {
        Log.d("ThemeViewModel","DarkB: ${isDarkTheme.value},,,,$isDark")
        viewModelScope.launch {
            repository.setDarkTheme(isDark)

            Log.d("ThemeViewModel","Dark: ${isDarkTheme.value}")
        }
    }
}
