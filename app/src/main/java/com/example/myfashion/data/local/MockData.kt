package com.example.myfashion.data.local

// MockData.kt
import androidx.compose.ui.graphics.Color
import com.example.myfashion.domain.model.ClothItem
import com.example.myfashion.domain.model.WearOption
import com.example.myfashion.R
import com.example.myfashion.domain.model.Collection
import com.example.myfashion.domain.model.WardrobeCategoryItem
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

    // Wardrobe items with vibrant colors
    val wardrobeCategoryItems = listOf(
        WardrobeCategoryItem(
            id = "1",
            name = "Casual Wear",
            itemCount = 15,
            iconRes = R.drawable.ic_casual,
            backgroundColor = Color(0xFF1A1A2E),
            innerColor = Color(0xFF16213E)
        ),
        WardrobeCategoryItem(
            id = "2",
            name = "Formal",
            itemCount = 8,
            iconRes = R.drawable.ic_formal,
            backgroundColor = Color(0xFF2D3047),
            innerColor = Color(0xFF424874)
        ),
        WardrobeCategoryItem(
            id = "3",
            name = "Sports",
            itemCount = 6,
            iconRes = R.drawable.ic_sports,
            backgroundColor = Color(0xFF1C3F60),
            innerColor = Color(0xFF2C5364)
        ),
        WardrobeCategoryItem(
            id = "4",
            name = "Party",
            itemCount = 5,
            iconRes = R.drawable.ic_traditional,
            backgroundColor = Color(0xFF553C8B),
            innerColor = Color(0xFF9D65C9)
        ),
        WardrobeCategoryItem(
            id = "5",
            name = "Seasonal",
            itemCount = 7,
            iconRes = R.drawable.ic_sleepwear,
            backgroundColor = Color(0xFF2C3E50),
            innerColor = Color(0xFF34495E)
        ),
        WardrobeCategoryItem(
            id = "6",
            name = "Workout",
            itemCount = 4,
            iconRes = R.drawable.ic_sports,
            backgroundColor = Color(0xFF0F3460),
            innerColor = Color(0xFF1A5F7A)
        )
    )

    // Clothing items sample data
    val clothItems = listOf(
        // ---------------- TOPS ----------------
        ClothItem(
            id = "1",
            name = "Nebula Hoodie",
            category = "Tops",
            subCategory = "Hoodies",
            color = "Black",
            brand = "Cyberwear",
            price = 89.99,
            purchaseDate = "2024-01-15",
            imageUrl = "https://images.unsplash.com/photo-1543076447-215ad9ba6923?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/hoodie.glb",
            tags = listOf("Streetwear", "Comfort", "Winter"),
            size = "M",
            material = "Cotton Blend",
            isFavorite = true,
            wearCount = 12,
            lastWorn = "2024-03-20"
        ),

        ClothItem(
            id = "2",
            name = "Classic White T-Shirt",
            category = "Tops",
            subCategory = "T-Shirts",
            color = "White",
            brand = "Uniqlo",
            price = 19.99,
            purchaseDate = "2023-11-02",
            imageUrl = "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/tshirt_white.glb",
            tags = listOf("Casual", "Minimal", "Daily"),
            size = "L",
            material = "Cotton",
            isFavorite = false,
            wearCount = 30,
            lastWorn = "2024-03-18"
        ),

        ClothItem(
            id = "3",
            name = "Skyline Oversize Shirt",
            category = "Tops",
            subCategory = "Shirts",
            color = "Blue",
            brand = "H&M",
            price = 39.99,
            purchaseDate = "2024-02-01",
            imageUrl = "https://images.unsplash.com/photo-1558171813-4c088753af8f?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTV8fFNreWxpbmUlMjBPdmVyc2l6ZSUyMFNoaXJ0fGVufDB8fDB8fHww?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/oversize_shirt.glb",
            tags = listOf("Summer", "Casual"),
            size = "XL",
            material = "Linen",
            isFavorite = true,
            wearCount = 8,
            lastWorn = "2024-03-12"
        ),

        // ---------------- BOTTOMS ----------------
        ClothItem(
            id = "4",
            name = "Slim Fit Jeans",
            category = "Bottoms",
            subCategory = "Jeans",
            color = "Denim Blue",
            brand = "Levis",
            price = 59.99,
            purchaseDate = "2024-01-20",
            imageUrl = "https://images.unsplash.com/photo-1583002123412-3fdc36f0f3c2?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/jeans.glb",
            tags = listOf("Casual", "Daily"),
            size = "32",
            material = "Denim",
            isFavorite = false,
            wearCount = 20,
            lastWorn = "2024-03-13"
        ),

        ClothItem(
            id = "5",
            name = "Khaki Chino Pants",
            category = "Bottoms",
            subCategory = "Chinos",
            color = "Beige",
            brand = "Zara",
            price = 49.99,
            purchaseDate = "2023-12-12",
            imageUrl = "https://images.unsplash.com/photo-1696889450800-e94ec7a32206?q=80&w=724&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/chinos.glb",
            tags = listOf("Smart Casual", "Office"),
            size = "30",
            material = "Cotton",
            isFavorite = true,
            wearCount = 14,
            lastWorn = "2024-03-19"
        ),

        // ---------------- DRESSES ----------------
        ClothItem(
            id = "6",
            name = "Evening Elegant Dress",
            category = "Dresses",
            subCategory = "Party",
            color = "Red",
            brand = "H&M",
            price = 129.99,
            purchaseDate = "2024-02-10",
            imageUrl = "https://images.unsplash.com/photo-1585487000160-6971e11053b3?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/dress_red.glb",
            tags = listOf("Evening", "Formal"),
            size = "S",
            material = "Silk",
            isFavorite = true,
            wearCount = 2,
            lastWorn = "2024-03-01"
        ),

        ClothItem(
            id = "7",
            name = "Summer Floral Dress",
            category = "Dresses",
            subCategory = "Casual",
            color = "Yellow",
            brand = "Forever21",
            price = 45.99,
            purchaseDate = "2023-05-12",
            imageUrl = "https://images.unsplash.com/photo-1586363104862-3e22c2bc8c2f?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/dress_flower.glb",
            tags = listOf("Summer", "Casual"),
            size = "M",
            material = "Cotton",
            isFavorite = false,
            wearCount = 11,
            lastWorn = "2024-03-17"
        ),

        // ---------------- OUTERWEAR ----------------
        ClothItem(
            id = "8",
            name = "Urban Winter Jacket",
            category = "Outerwear",
            subCategory = "Jackets",
            color = "Navy",
            brand = "NorthFace",
            price = 149.99,
            purchaseDate = "2023-12-01",
            imageUrl = "https://images.unsplash.com/photo-1544441893-675973e31985?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/jacket_navy.glb",
            tags = listOf("Winter", "Outdoor"),
            size = "L",
            material = "Polyester",
            isFavorite = true,
            wearCount = 6,
            lastWorn = "2024-02-28"
        ),

        ClothItem(
            id = "9",
            name = "Lightweight Windbreaker",
            category = "Outerwear",
            subCategory = "Windbreaker",
            color = "Gray",
            brand = "Adidas",
            price = 79.99,
            purchaseDate = "2024-01-05",
            imageUrl = "https://images.unsplash.com/photo-1584999734485-0f84e99bb9e3?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/windbreaker.glb",
            tags = listOf("Sport", "Travel"),
            size = "M",
            material = "Nylon",
            isFavorite = false,
            wearCount = 9,
            lastWorn = "2024-03-10"
        ),

        // ---------------- SHOES ----------------
        ClothItem(
            id = "10",
            name = "Air Runner Sneakers",
            category = "Shoes",
            subCategory = "Sneakers",
            color = "White",
            brand = "Nike",
            price = 119.99,
            purchaseDate = "2024-01-18",
            imageUrl = "https://images.unsplash.com/photo-1584735174914-6b1acb260f8b?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/sneaker_white.glb",
            tags = listOf("Sport", "Streetwear"),
            size = "9",
            material = "Mesh",
            isFavorite = true,
            wearCount = 16,
            lastWorn = "2024-03-21"
        ),

        ClothItem(
            id = "11",
            name = "Brown Leather Boots",
            category = "Shoes",
            subCategory = "Boots",
            color = "Brown",
            brand = "Timberland",
            price = 149.99,
            purchaseDate = "2023-10-09",
            imageUrl = "https://images.unsplash.com/photo-1505685296765-3a2736de412f?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/boots_brown.glb",
            tags = listOf("Winter", "Outdoor"),
            size = "10",
            material = "Leather",
            isFavorite = false,
            wearCount = 4,
            lastWorn = "2024-03-02"
        ),

        // ---------------- ACCESSORIES ----------------
        ClothItem(
            id = "12",
            name = "Minimalist Wrist Watch",
            category = "Accessories",
            subCategory = "Watches",
            color = "Black",
            brand = "Daniel Wellington",
            price = 99.99,
            purchaseDate = "2023-08-14",
            imageUrl = "https://images.unsplash.com/photo-1524592094714-0f0654e20314?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/watch_black.glb",
            tags = listOf("Formal", "Luxury"),
            size = "One Size",
            material = "Leather + Steel",
            isFavorite = true,
            wearCount = 22,
            lastWorn = "2024-03-15"
        ),

        ClothItem(
            id = "13",
            name = "Daily Carry Backpack",
            category = "Accessories",
            subCategory = "Bags",
            color = "Gray",
            brand = "Herschel",
            price = 69.99,
            purchaseDate = "2024-01-11",
            imageUrl = "https://images.unsplash.com/photo-1580910051074-d1445d6b5c49?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/backpack.glb",
            tags = listOf("Travel", "School"),
            size = "Large",
            material = "Canvas",
            isFavorite = false,
            wearCount = 10,
            lastWorn = "2024-03-08"
        ),

        // ---------- 14 ----------
        ClothItem(
            id = "14",
            name = "Textured Knit Sweater",
            category = "Tops",
            subCategory = "Sweater",
            color = "Beige",
            brand = "CozyWear",
            price = 65.00,
            purchaseDate = "2024-02-12",
            imageUrl = "https://images.unsplash.com/photo-1617127365659-c47fa864d8bc?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/knit_sweater.glb",
            tags = listOf("Warm", "Casual"),
            size = "M",
            material = "Wool Blend",
            isFavorite = false,
            wearCount = 5,
            lastWorn = "2024-04-03"
        ),

        // ---------- 15 ----------
        ClothItem(
            id = "15",
            name = "Cotton Henley Shirt",
            category = "Tops",
            subCategory = "Shirt",
            color = "White",
            brand = "UrbanBasics",
            price = 29.99,
            purchaseDate = "2024-01-15",
            imageUrl = "https://images.unsplash.com/photo-1598033129183-c4f50c736f10?auto=format&fit=crop&w=800&q=80", // Corrected to shirt image
            model3dUrl = "models/henley.glb",
            tags = listOf("Summer", "Casual"),
            size = "L",
            material = "100% Cotton",
            isFavorite = false,
            wearCount = 7,
            lastWorn = "2024-03-22"
        ),

        // ---------- 16 ----------
        ClothItem(
            id = "16",
            name = "Oversized Hoodie",
            category = "Tops",
            subCategory = "Hoodie",
            color = "Black",
            brand = "StreetCore",
            price = 56.00,
            purchaseDate = "2024-02-20",
            imageUrl = "https://images.unsplash.com/photo-1602810317721-210c50f0ae64?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/hoodie_black.glb",
            tags = listOf("Streetwear", "Comfort"),
            size = "XL",
            material = "Fleece",
            isFavorite = true,
            wearCount = 12,
            lastWorn = "2024-04-01"
        ),

        // ---------- 17 ----------
        ClothItem(
            id = "17",
            name = "Slim Fit Jeans",
            category = "Bottoms",
            subCategory = "Jeans",
            color = "Blue",
            brand = "DenimPro",
            price = 49.99,
            purchaseDate = "2023-12-28",
            imageUrl = "https://images.unsplash.com/photo-1542272604-787c3835535d?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/slim_jeans.glb",
            tags = listOf("Casual", "Daily"),
            size = "32",
            material = "Denim",
            isFavorite = true,
            wearCount = 15,
            lastWorn = "2024-04-03"
        ),

        // ---------- 18 ----------
        ClothItem(
            id = "18",
            name = "Relaxed Fit Joggers",
            category = "Bottoms",
            subCategory = "Joggers",
            color = "Grey",
            brand = "AthleisureCo",
            price = 39.50,
            purchaseDate = "2024-01-10",
            imageUrl = "https://images.unsplash.com/photo-1583743782676-c65b876035f7?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/joggers.glb",
            tags = listOf("Gym", "Comfort"),
            size = "L",
            material = "Poly-Cotton",
            isFavorite = false,
            wearCount = 10,
            lastWorn = "2024-03-30"
        ),

        // ---------- 19 ----------
        ClothItem(
            id = "19",
            name = "Classic Chino Pants",
            category = "Bottoms",
            subCategory = "Chinos",
            color = "Khaki",
            brand = "FormLine",
            price = 52.00,
            purchaseDate = "2024-02-02",
            imageUrl = "https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/chinos.glb",
            tags = listOf("Office", "Casual"),
            size = "33",
            material = "Twill",
            isFavorite = false,
            wearCount = 6,
            lastWorn = "2024-03-21"
        ),

        // ---------- 20 ----------
        ClothItem(
            id = "20",
            name = "Denim Overshirt Jacket",
            category = "Outerwear",
            subCategory = "Overshirt",
            color = "Blue",
            brand = "UrbanEdge",
            price = 72.50,
            purchaseDate = "2024-02-12",
            imageUrl = "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/denim_shirt.glb",
            tags = listOf("Casual", "Layering"),
            size = "M",
            material = "Denim",
            isFavorite = true,
            wearCount = 3,
            lastWorn = "2024-03-29"
        ),

        // ---------- 21 ----------
        ClothItem(
            id = "21",
            name = "Puffer Vest",
            category = "Outerwear",
            subCategory = "Vest",
            color = "Black",
            brand = "NorthLine",
            price = 88.99,
            purchaseDate = "2024-01-25",
            imageUrl = "https://images.unsplash.com/photo-1618354691373-d851c5c7d813?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/puffer_vest.glb",
            tags = listOf("Winter", "Lightweight"),
            size = "L",
            material = "Polyester",
            isFavorite = false,
            wearCount = 5,
            lastWorn = "2024-02-15"
        ),

        // ---------- 22 ----------
        ClothItem(
            id = "22",
            name = "Rainproof Windbreaker",
            category = "Outerwear",
            subCategory = "Windbreaker",
            color = "Yellow",
            brand = "AeroShield",
            price = 84.00,
            purchaseDate = "2024-03-01",
            imageUrl = "https://images.unsplash.com/photo-1503342217505-b0a15ec3261c?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/windbreaker.glb",
            tags = listOf("Travel", "Rain"),
            size = "M",
            material = "Polyester",
            isFavorite = true,
            wearCount = 2,
            lastWorn = "2024-03-27"
        ),

        // ---------- 23 ----------
        ClothItem(
            id = "23",
            name = "Retro White Sneakers",
            category = "Shoes",
            subCategory = "Sneakers",
            color = "White",
            brand = "StepOne",
            price = 69.99,
            purchaseDate = "2024-03-11",
            imageUrl = "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/sneakers_white.glb",
            tags = listOf("Daily", "Casual"),
            size = "9",
            material = "Mesh",
            isFavorite = false,
            wearCount = 3,
            lastWorn = "2024-04-03"
        ),

        // ---------- 24 ----------
        ClothItem(
            id = "24",
            name = "Black Leather Boots",
            category = "Shoes",
            subCategory = "Boots",
            color = "Black",
            brand = "WildCraft",
            price = 110.00,
            purchaseDate = "2024-02-07",
            imageUrl = "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/leather_boots.glb",
            tags = listOf("Outdoor", "Rugged"),
            size = "10",
            material = "Leather",
            isFavorite = true,
            wearCount = 4,
            lastWorn = "2024-03-22"
        ),

        // ---------- 25 ----------
        ClothItem(
            id = "25",
            name = "High-Top Canvas Sneakers",
            category = "Shoes",
            subCategory = "High Top",
            color = "Black",
            brand = "UrbanSteps",
            price = 57.50,
            purchaseDate = "2024-02-15",
            imageUrl = "https://images.unsplash.com/photo-1506277886164-e25aa3f4ef7a?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/high_top.glb",
            tags = listOf("Streetwear", "Retro"),
            size = "9",
            material = "Canvas",
            isFavorite = false,
            wearCount = 8,
            lastWorn = "2024-03-30"
        ),

        // ---------- 26 ----------
        ClothItem(
            id = "26",
            name = "Round Metal Sunglasses",
            category = "Accessories",
            subCategory = "Sunglasses",
            color = "Black",
            brand = "ShadeCraft",
            price = 34.00,
            purchaseDate = "2024-01-29",
            imageUrl = "https://images.unsplash.com/photo-1608572489815-21b0f44a3694?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/sunglasses_round.glb",
            tags = listOf("Summer", "Travel"),
            size = "One Size",
            material = "Metal",
            isFavorite = false,
            wearCount = 12,
            lastWorn = "2024-04-01"
        ),

        // ---------- 27 ----------
        ClothItem(
            id = "27",
            name = "Leather Billfold Wallet",
            category = "Accessories",
            subCategory = "Wallet",
            color = "Brown",
            brand = "PrimeLeather",
            price = 49.99,
            purchaseDate = "2024-01-12",
            imageUrl = "https://images.unsplash.com/photo-1591375275888-44d61bba0f88?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/wallet_brown.glb",
            tags = listOf("Daily", "Premium"),
            size = "Standard",
            material = "Leather",
            isFavorite = true,
            wearCount = 20,
            lastWorn = "2024-04-03"
        ),

        // ---------- 28 ----------
        ClothItem(
            id = "28",
            name = "Sports Training Tee",
            category = "Tops",
            subCategory = "T-Shirt",
            color = "Red",
            brand = "FitFlex",
            price = 22.50,
            purchaseDate = "2024-03-05",
            imageUrl = "https://images.unsplash.com/photo-1578587018452-892bacefd3f2?auto=format&fit=crop&w=800&q=80", // Corrected to t-shirt image
            model3dUrl = "models/sport_tee.glb",
            tags = listOf("Gym", "Breathable"),
            size = "L",
            material = "Polyester",
            isFavorite = false,
            wearCount = 4,
            lastWorn = "2024-04-02"
        ),

        // ---------- 29 ----------
        ClothItem(
            id = "29",
            name = "Athletic Shorts",
            category = "Bottoms",
            subCategory = "Shorts",
            color = "Black",
            brand = "AthleisureCo",
            price = 25.00,
            purchaseDate = "2024-03-03",
            imageUrl = "https://images.unsplash.com/photo-1599058917212-d750089bc07a?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/sport_shorts.glb",
            tags = listOf("Gym", "Running"),
            size = "L",
            material = "Polyester",
            isFavorite = false,
            wearCount = 6,
            lastWorn = "2024-04-03"
        ),

        // ---------- 30 ----------
        ClothItem(
            id = "30",
            name = "Padded Sports Jacket",
            category = "Outerwear",
            subCategory = "Sports Jacket",
            color = "Blue",
            brand = "FlexGear",
            price = 89.99,
            purchaseDate = "2024-03-01",
            imageUrl = "https://images.unsplash.com/photo-1580226394764-451b8708d1b6?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/sports_jacket.glb",
            tags = listOf("Training", "Outdoor"),
            size = "M",
            material = "Polyester",
            isFavorite = true,
            wearCount = 3,
            lastWorn = "2024-04-01"
        ),

        // ---------- 31 ----------
        ClothItem(
            id = "31",
            name = "Casual Linen Shirt",
            category = "Tops",
            subCategory = "Shirt",
            color = "Sky Blue",
            brand = "BreezeWear",
            price = 44.00,
            purchaseDate = "2024-03-09",
            imageUrl = "https://images.unsplash.com/photo-1584813802625-cdaeb0bb392e?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/linen_shirt.glb",
            tags = listOf("Summer", "Light"),
            size = "L",
            material = "Linen",
            isFavorite = false,
            wearCount = 2,
            lastWorn = "2024-04-03"
        ),

        // ---------- 32 ----------
        ClothItem(
            id = "32",
            name = "Corduroy Overshirt",
            category = "Outerwear",
            subCategory = "Overshirt",
            color = "Brown",
            brand = "WarmLine",
            price = 68.99,
            purchaseDate = "2024-02-25",
            imageUrl = "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/corduroy_overshirt.glb",
            tags = listOf("Fall", "Layering"),
            size = "M",
            material = "Corduroy",
            isFavorite = false,
            wearCount = 4,
            lastWorn = "2024-03-29"
        ),

        // ---------- 33 ----------
        ClothItem(
            id = "33",
            name = "Thermal Base Layer",
            category = "Tops",
            subCategory = "Thermal",
            color = "Black",
            brand = "HeatMax",
            price = 32.99,
            purchaseDate = "2023-12-20",
            imageUrl = "https://images.unsplash.com/photo-1571115764595-2a99a92ee0d9?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/thermal.glb",
            tags = listOf("Winter", "Warm"),
            size = "L",
            material = "Poly-Wool",
            isFavorite = false,
            wearCount = 9,
            lastWorn = "2024-02-18"
        ),

        // ---------- 34 ----------
        ClothItem(
            id = "34",
            name = "Faux Leather Jacket",
            category = "Outerwear",
            subCategory = "Jacket",
            color = "Brown",
            brand = "MotoFlex",
            price = 120.99,
            purchaseDate = "2024-02-10",
            imageUrl = "https://images.unsplash.com/photo-1503341455253-b2e723bb3dbb?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/leather_jacket.glb",
            tags = listOf("Street", "Bold"),
            size = "L",
            material = "Faux Leather",
            isFavorite = true,
            wearCount = 3,
            lastWorn = "2024-03-15"
        ),

        // ---------- 35 ----------
        ClothItem(
            id = "35",
            name = "Canvas Backpack",
            category = "Accessories",
            subCategory = "Bag",
            color = "Olive",
            brand = "PackCo",
            price = 59.99,
            purchaseDate = "2024-03-05",
            imageUrl = "https://images.unsplash.com/photo-1529338296731-c4280a44fc10?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/backpack.glb",
            tags = listOf("Travel", "Daily"),
            size = "Large",
            material = "Canvas",
            isFavorite = false,
            wearCount = 10,
            lastWorn = "2024-04-03"
        ),

        // ---------- 36 ----------
        ClothItem(
            id = "36",
            name = "Knitted Winter Cap",
            category = "Accessories",
            subCategory = "Cap",
            color = "Grey",
            brand = "WarmKnit",
            price = 19.00,
            purchaseDate = "2023-12-18",
            imageUrl = "https://images.unsplash.com/photo-1611080626919-7cf5a8897c70?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/winter_cap.glb",
            tags = listOf("Winter", "Warm"),
            size = "Free Size",
            material = "Wool",
            isFavorite = false,
            wearCount = 8,
            lastWorn = "2024-02-20"
        ),

        // ---------- 37 ----------
        ClothItem(
            id = "37",
            name = "Ripped Denim Jeans",
            category = "Bottoms",
            subCategory = "Jeans",
            color = "Light Blue",
            brand = "StreetDenim",
            price = 59.00,
            purchaseDate = "2024-03-08",
            imageUrl = "https://images.unsplash.com/photo-1473966968600-fa801b869a1a?auto=format&fit=crop&w=800&q=80", // Corrected to jeans image
            model3dUrl = "models/ripped_jeans.glb",
            tags = listOf("Streetwear", "Casual"),
            size = "32",
            material = "Denim",
            isFavorite = true,
            wearCount = 1,
            lastWorn = "2024-04-02"
        ),

        // ---------- 38 ----------
        ClothItem(
            id = "38",
            name = "Fleece Sweatshirt",
            category = "Tops",
            subCategory = "Sweatshirt",
            color = "Navy",
            brand = "CozyAthletic",
            price = 42.50,
            purchaseDate = "2024-02-22",
            imageUrl = "https://images.unsplash.com/photo-1543087903-1ac2ec7aa8c5?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/sweatshirt.glb",
            tags = listOf("Winter", "Cozy"),
            size = "M",
            material = "Fleece",
            isFavorite = false,
            wearCount = 4,
            lastWorn = "2024-03-29"
        ),

        // ---------- 39 ----------
        ClothItem(
            id = "39",
            name = "Training Running Shoes",
            category = "Shoes",
            subCategory = "Running",
            color = "Blue",
            brand = "RunPro",
            price = 85.00,
            purchaseDate = "2024-02-18",
            imageUrl = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/running_shoes.glb",
            tags = listOf("Running", "Gym"),
            size = "9",
            material = "Mesh",
            isFavorite = false,
            wearCount = 3,
            lastWorn = "2024-03-28"
        ),

        // ---------- 40 ----------
        ClothItem(
            id = "40",
            name = "Soft Cotton Pajama Pants",
            category = "Bottoms",
            subCategory = "Pajamas",
            color = "Grey",
            brand = "SleepWearCo",
            price = 28.99,
            purchaseDate = "2023-12-25",
            imageUrl = "https://images.unsplash.com/photo-1604079628040-94301bb21b6b?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/pajama.glb",
            tags = listOf("Home", "Comfort"),
            size = "L",
            material = "Cotton",
            isFavorite = true,
            wearCount = 12,
            lastWorn = "2024-03-27"
        ),

        // ---------- 41 ----------
        ClothItem(
            id = "41",
            name = "Casual Polo Shirt",
            category = "Tops",
            subCategory = "Polo",
            color = "Green",
            brand = "Classique",
            price = 37.50,
            purchaseDate = "2024-03-02",
            imageUrl = "https://images.unsplash.com/photo-1581655353564-df123a1eb820?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/polo_shirt.glb",
            tags = listOf("Smart Casual"),
            size = "L",
            material = "Cotton",
            isFavorite = false,
            wearCount = 1,
            lastWorn = "2024-04-03"
        ),

        // ---------- 42 ----------
        ClothItem(
            id = "42",
            name = "Classic Beanie",
            category = "Accessories",
            subCategory = "Beanie",
            color = "Black",
            brand = "UrbanCold",
            price = 18.00,
            purchaseDate = "2024-01-10",
            imageUrl = "https://images.unsplash.com/photo-1517677208171-0bc6725a3e60?auto=format&fit=crop&w=800&q=80",
            model3dUrl = "models/beanie.glb",
            tags = listOf("Winter", "Warm"),
            size = "Free Size",
            material = "Wool",
            isFavorite = false,
            wearCount = 7,
            lastWorn = "2024-02-12"
        ),

        // ---------- 43 ----------
        ClothItem(
            id = "43",
            name = "Cargo Utility Shorts",
            category = "Bottoms",
            subCategory = "Cargo Shorts",
            color = "Green",
            brand = "FieldGear",
            price = 36.00,
            purchaseDate = "2024-03-08",
            imageUrl = "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?auto=format&fit=crop&w=800&q=80", // Corrected to shorts image
            model3dUrl = "models/cargo_shorts.glb",
            tags = listOf("Outdoor", "Summer"),
            size = "L",
            material = "Cotton",
            isFavorite = false,
            wearCount = 1,
            lastWorn = "2024-04-01"
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