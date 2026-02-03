package com.example.myfashion.data.repositoryImp

import com.example.myfashion.data.local.data_preference.ThemePreference
import com.example.myfashion.domain.repository.ThemeRepository


class ThemeRepositoryImpl(
    private val themePreference: ThemePreference
) : ThemeRepository {

    override val isDarkTheme = themePreference.isDarkTheme

    override suspend fun setDarkTheme(isDark: Boolean) {
        themePreference.setDarkTheme(isDark)
    }
}
