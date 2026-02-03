package com.example.myfashion.data.local.room.preset


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "presets")
data class Preset(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    @TypeConverters(StringListConverter::class)
    val items: List<String>, // List of clothing item IDs
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val description: String? = null,
    val tags: List<String> = emptyList()
)

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
    }
}