package com.example.myfashion.data.local

// MockData.kt
import androidx.compose.ui.graphics.Color
import com.example.myfashion.domain.model.WearOption
import com.example.myfashion.R
import com.example.myfashion.domain.model.Collection
import com.example.myfashion.presentasion.Category

object MockData {
    // Categories with beautiful gradients
    val categories = listOf(
        Category(
            name = "All",
            iconRes = R.drawable.ic_all,
        ),
        Category(
            name = "Tops",
            iconRes = R.drawable.ic_shirt,
        ),
        Category(
            name = "Bottoms",
            iconRes = R.drawable.ic_pants,
        ),
        Category(
            name = "Dresses",
            iconRes = R.drawable.suit,
        ),
        Category(
            name = "Outerwear",
            iconRes = R.drawable.ic_jacket,
        ),
        Category(
            name = "Shoes",
            iconRes = R.drawable.ic_shoes,
        ),
        Category(
            name = "Accessories",
            iconRes = R.drawable.ic_accessories,
        )
    )

    // Collections data
    val collections = listOf(
        Collection(
            id = "1",
            name = "Cyberpunk Collection",
            description = "Futuristic streetwear",
            itemCount = 8,
            coverImage = "https://example.com/cyberpunk.jpg",
            gradientColors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC)),
            createdDate = "2024-02-01"
        ),
        Collection(
            id = "1",
            name = "Cyberpunk Collection",
            description = "Futuristic streetwear",
            itemCount = 8,
            coverImage = "https://example.com/cyberpunk.jpg",
            gradientColors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC)),
            createdDate = "2024-02-01"
        ),
        // Add more collections...
    )

    // Wear options for 3D preview
    val wearOptions = listOf(
        WearOption(
            id = "1",
            name = "T-Shirt",
            iconRes = R.drawable.ic_t_shirt,
            color = Color(0xFFFF6B6B),
            description = "Casual look"
        ),
        WearOption(
            id = "2",
            name = "Jacket",
            iconRes = R.drawable.ic_jacket,
            color = Color(0xFF4ECDC4),
            description = "Outer layer"
        ),
        WearOption(
            id = "3",
            name = "Accessories",
            iconRes = R.drawable.ic_accessories,
            color = Color(0xFFFFD166),
            description = "Add-ons"
        ),
        WearOption(
            id = "4",
            name = "Pants",
            iconRes = R.drawable.ic_pants,
            color = Color(0xFF06D6A0),
            description = "Bottom wear"
        ),
        WearOption(
            id = "5",
            name = "Shoes",
            iconRes = R.drawable.ic_shoes,
            color = Color(0xFF118AB2),
            description = "Footwear"
        ),
        WearOption(
            id = "6",
            name = "Hat",
            iconRes = R.drawable.ic_hat,
            color = Color(0xFFEF476F),
            description = "Headwear"
        )
    )
}