package com.example.myfashion.di

import android.content.Context
import androidx.room.Room
import com.example.myfashion.data.local.data_preference.ThemePreference
import com.example.myfashion.data.local.room.ClothingDatabase
import com.example.myfashion.data.local.room.ClothingItemDao
import com.example.myfashion.data.local.room.preset.PresetDao
import com.example.myfashion.data.remot.WeatherApi
import com.example.myfashion.data.repositoryImp.ClothingRepository
import com.example.myfashion.data.repositoryImp.PresetRepository
import com.example.myfashion.data.repositoryImp.ThemeRepositoryImpl
import com.example.myfashion.data.repositoryImp.WeatherRepositoryImpl
import com.example.myfashion.domain.repository.ThemeRepository
import com.example.myfashion.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideClothingDatabase(@ApplicationContext context: Context): ClothingDatabase {
        return Room.databaseBuilder(
            context, ClothingDatabase::class.java, "clothing_database"
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun provideClothingItemDao(database: ClothingDatabase) = database.clothingItemDao()

    @Provides
    @Singleton
    fun providePresetDao(database: ClothingDatabase) = database.presetDao()

    @Provides
    @Singleton
    fun provideClothingRepository(dao: ClothingItemDao): ClothingRepository {
        return ClothingRepository(dao)
    }

    @Provides
    @Singleton
    fun providePresetRepository(
        dao: PresetDao,
        clothingRepository: ClothingRepository
    ): PresetRepository {
        return PresetRepository(
            dao, clothingRepository
        )
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    @Provides
    @Singleton
    fun provideWeatherApi(
        client: HttpClient
    ): WeatherApi {
        return WeatherApi(client)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }

    //Data Store theme
    @Provides
    @Singleton
    fun provideThemePreference(
        @ApplicationContext context: Context
    ): ThemePreference = ThemePreference(context)

    @Provides
    @Singleton
    fun provideThemeRepository(
        themePreference: ThemePreference
    ): ThemeRepository = ThemeRepositoryImpl(themePreference)

}