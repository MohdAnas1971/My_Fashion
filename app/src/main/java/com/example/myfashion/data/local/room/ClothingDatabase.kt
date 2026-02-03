package com.example.myfashion.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.myfashion.data.local.room.preset.Preset
import com.example.myfashion.data.local.room.preset.PresetDao
import com.example.myfashion.data.local.room.preset.StringListConverter

@Database(
    entities = [ClothingItem::class, Preset::class],
    version = 2, // Increment version
    exportSchema = false
)
@TypeConverters(
   StringListConverter::class
)
abstract class ClothingDatabase : RoomDatabase() {
    abstract fun clothingItemDao(): ClothingItemDao
    abstract fun presetDao(): PresetDao

    companion object {
        @Volatile
        private var INSTANCE: ClothingDatabase? = null

        fun getDatabase(context: Context): ClothingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClothingDatabase::class.java,
                    "clothing_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
