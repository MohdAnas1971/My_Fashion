package com.example.myfashion.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.UUID

@Entity(tableName = "clothing_items")
data class ClothingItem(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    // Required
    val name: String,
    val category: String,
    val subCategory: String? = null,
    val color: String,
    val imageUrl: String,

    // Optional
    val brand: String? = null,
    val size: String? = null,
    val season: String? = null,
    val price: Double? = null,
    val purchaseDate: Long? = null,
    val material: String? = null,
    val careInstructions: String? = null,

    // Auto-Tracked/Tags
    val wearCount: Int = 0,
    val isFavorite: Boolean = false,
    val tags: List<String> = emptyList(),

    // Metadata
    val addedAt: Long = System.currentTimeMillis()
)



