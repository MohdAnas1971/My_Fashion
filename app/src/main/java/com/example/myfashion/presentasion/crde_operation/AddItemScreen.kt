package com.example.myfashion.presentasion.crde_operation

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Texture
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.presentasion.camera.ImagePickerDialog
import com.example.myfashion.presentasion.camera.ImagePreview
import com.example.myfashion.presentasion.camera.TakeImage
import com.example.myfashion.presentasion.camera.copyUriToInternalStorage
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    viewModel: ClothingViewModel,
    onBack: () -> Unit,
    initialItem: ClothingItem? = null
) {
    val uiState: AddItemScreenState = rememberAddItemScreenState(initialItem)
    val context = LocalContext.current
    Scaffold(
        topBar = {
            AddItemTopBar(
                isEditMode = uiState.isEditMode,
                onBack = onBack,
                onSave = {
                    // Create the ClothingItem from CURRENT state values
                    val item = ClothingItem(
                        name = uiState.name,
                        category = uiState.selectedCategory,
                        subCategory = uiState.selectedSubCategory.ifEmpty { null },
                        color = uiState.selectedColor,
                        imageUrl = uiState.imageUrl,
                        brand = uiState.brand.ifEmpty { null },
                        size = uiState.selectedSize.ifEmpty { null },
                        season = uiState.selectedSeason.ifEmpty { null },
                        price = uiState.price.toDoubleOrNull(),
                        purchaseDate = uiState.purchaseDate?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond()?.times(1000),
                        material = uiState.selectedMaterial.ifEmpty { null },
                        careInstructions = uiState.careInstructions.ifEmpty { null },
                        tags = uiState.selectedTags,
                        wearCount = initialItem?.wearCount ?: 0,
                        isFavorite = initialItem?.isFavorite ?: false
                    )
                    Log.d("AddItemScreen","item: ${item.imageUrl}")
                    // Save the CURRENT item
                    viewModel.saveItem(uiState,item)
                    onBack()
                },
                isSaveEnabled = uiState.isValid
            )
        }
    ) { paddingValues ->
        AddItemContent(
            uiState = uiState,
            context,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }

    // Dialogs
    if (uiState.showImagePicker) {
        ImagePickerDialog(
            onDismiss = { uiState.showImagePicker = false },
            onImageSelected = { url -> uiState.imageUrl = url }
        )
    }

    if (uiState.showDatePicker) {
        DatePickerDialog(
            onDismiss = { uiState.showDatePicker = false },
            onDateSelected = { date -> uiState.purchaseDate = date }
        )
    }
}

@Stable
class AddItemScreenState(
    initialItem: ClothingItem? = null
) {
    val isEditMode = initialItem != null
    var name by mutableStateOf(initialItem?.name ?: "")
    var selectedCategory by mutableStateOf(initialItem?.category ?: "")
    var selectedSubCategory by mutableStateOf(initialItem?.subCategory ?: "")
    var selectedColor by mutableStateOf(initialItem?.color ?: "")
    var imageUrl by mutableStateOf(initialItem?.imageUrl ?: "")
    var brand by mutableStateOf(initialItem?.brand ?: "")
    var selectedSize by mutableStateOf(initialItem?.size ?: "")
    var selectedSeason by mutableStateOf(initialItem?.season ?: "")
    var price by mutableStateOf(initialItem?.price?.toString() ?: "")
    var purchaseDate by mutableStateOf<LocalDate?>(null)
    var selectedMaterial by mutableStateOf(initialItem?.material ?: "")
    var careInstructions by mutableStateOf(initialItem?.careInstructions ?: "")
    var selectedTags by mutableStateOf(initialItem?.tags ?: emptyList())
    var showImagePicker by mutableStateOf(false)
    var showDatePicker by mutableStateOf(false)

    val isValid: Boolean
        get() = name.isNotBlank() &&
                selectedCategory.isNotBlank() &&
                selectedColor.isNotBlank() &&
                imageUrl.isNotBlank()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun rememberAddItemScreenState(
    initialItem: ClothingItem? = null
): AddItemScreenState {
    val state = rememberSaveable(saver = AddItemScreenStateSaver) {
        AddItemScreenState(initialItem)
    }

    // Initialize purchase date if editing
    LaunchedEffect(initialItem) {
        initialItem?.purchaseDate?.let {
            state.purchaseDate = LocalDate.ofEpochDay(it / (1000 * 60 * 60 * 24))
        }
    }

    return state
}

private val AddItemScreenStateSaver = run {
    // Simplified saver - in production, you'd save all necessary fields
    androidx.compose.runtime.saveable.Saver<AddItemScreenState, Any>(
        save = { state ->
            // Save minimal state if needed
            listOf(
                state.name,
                state.selectedCategory,
                state.selectedColor,
                state.imageUrl
            )
        },
        restore = { _ -> AddItemScreenState() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddItemTopBar(
    isEditMode: Boolean,
    onBack: () -> Unit,
    onSave: () -> Unit,
    isSaveEnabled: Boolean
) {
    TopAppBar(
        title = {
            Text(
                text = if (isEditMode) "Edit Item" else "Add New Clothing",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSave,
                enabled = isSaveEnabled
            ) {
                Icon(
                    imageVector = if (isEditMode) Icons.Default.Save else Icons.Default.Add,
                    contentDescription = if (isEditMode) "Save" else "Add"
                )
            }
        }
    )
}

@Composable
private fun AddItemContent(
    uiState: AddItemScreenState,
    context: Context,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Image Section
        ImageSection(
            imageUrl = uiState.imageUrl,
            context=context,
            onAddImageClick = { imageUrl-> uiState.imageUrl = imageUrl },
        )

        // Required Information
        RequiredInformationSection(
            name = uiState.name,
            onNameChange = { uiState.name = it },
            selectedCategory = uiState.selectedCategory,
            onCategoryChange = {
                uiState.selectedCategory = it
                uiState.selectedSubCategory = ""
            },
            selectedSubCategory = uiState.selectedSubCategory,
            onSubCategoryChange = { uiState.selectedSubCategory = it },
            selectedColor = uiState.selectedColor,
            onColorChange = { uiState.selectedColor = it }
        )

        // Optional Details
        OptionalDetailsSection(
            brand = uiState.brand,
            onBrandChange = { uiState.brand = it },
            selectedSize = uiState.selectedSize,
            onSizeChange = { uiState.selectedSize = it },
            selectedSeason = uiState.selectedSeason,
            onSeasonChange = { uiState.selectedSeason = it },
            price = uiState.price,
            onPriceChange = { uiState.price = it },
            purchaseDate = uiState.purchaseDate,
            onDateClick = { uiState.showDatePicker = true },
            selectedMaterial = uiState.selectedMaterial,
            onMaterialChange = { uiState.selectedMaterial = it },
            careInstructions = uiState.careInstructions,
            onCareInstructionsChange = { uiState.careInstructions = it }
        )

        // Tags
        TagsSection(
            selectedTags = uiState.selectedTags,
            onTagsChange = { uiState.selectedTags = it }
        )
    }
}

@Composable
private fun ImageSection(
    imageUrl: String,
    onAddImageClick: (String) -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ImagePreview(
                imageUrl = imageUrl,
                modifier = Modifier.size(200.dp)
            )

            TakeImage(
                onImageSelected = { uri ->
                    // Handle image URI
                  val storedUri=  copyUriToInternalStorage(context = context,uri)
                    onAddImageClick(storedUri)
                },
              //  modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RequiredInformationSection(
    name: String,
    onNameChange: (String) -> Unit,
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    selectedSubCategory: String,
    onSubCategoryChange: (String) -> Unit,
    selectedColor: String,
    onColorChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "Required Information",
        icon = Icons.Default.Info,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Item Name *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.AutoMirrored.Filled.Label, contentDescription = null)
                },
                singleLine = true,
                isError = name.isBlank(),
                supportingText = {
                    if (name.isBlank()) {
                        Text("Item name is required")
                    }
                }
            )

            // Category
            CategoryAndSubCategory(
                selectedCategory = selectedCategory,
                onCategoryChange = onCategoryChange,
                selectedSubCategory = selectedSubCategory,
                onSubCategoryChange = onSubCategoryChange
            )

            // Color Selection
            ColorSelection(
                selectedColor = selectedColor,
                onColorChange = onColorChange
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CategoryAndSubCategory(
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    selectedSubCategory: String,
    onSubCategoryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Category Dropdown
        DropdownField(
            value = selectedCategory,
            onValueChange = onCategoryChange,
            label = "Category *",
            items = ClothingCategories.categories,
            icon = Icons.Default.Category,
            isRequired = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Sub-category Dropdown (only shown if category has subcategories)
        ClothingCategories.subCategories[selectedCategory]?.let { subCategories ->
            if (subCategories.isNotEmpty()) {
                DropdownField(
                    value = selectedSubCategory,
                    onValueChange = onSubCategoryChange,
                    label = "Sub-category",
                    items = subCategories,
                    icon = Icons.AutoMirrored.Filled.List,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ColorSelection(
    selectedColor: String,
    onColorChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Color *",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ClothingColors.colors.forEach { color ->
                FilterChip(
                    selected = selectedColor == color,
                    onClick = { onColorChange(color) },
                    label = { Text(color) },
                    modifier = Modifier.padding(vertical = 2.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}

@Composable
private fun OptionalDetailsSection(
    brand: String,
    onBrandChange: (String) -> Unit,
    selectedSize: String,
    onSizeChange: (String) -> Unit,
    selectedSeason: String,
    onSeasonChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    purchaseDate: LocalDate?,
    onDateClick: () -> Unit,
    selectedMaterial: String,
    onMaterialChange: (String) -> Unit,
    careInstructions: String,
    onCareInstructionsChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "Optional Details",
        icon = Icons.Default.Settings,
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Brand
            OutlinedTextField(
                value = brand,
                onValueChange = onBrandChange,
                label = { Text("Brand") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Store, contentDescription = null) },
                singleLine = true
            )

            // Size and Season Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownField(
                    value = selectedSize,
                    onValueChange = onSizeChange,
                    label = "Size",
                    items = ClothingSizes.sizes,
                    icon = Icons.Default.Accessibility,
                    modifier = Modifier.weight(1f)
                )

                DropdownField(
                    value = selectedSeason,
                    onValueChange = onSeasonChange,
                    label = "Season",
                    items = Seasons.all,
                    icon = Icons.Default.WbSunny,
                    modifier = Modifier.weight(1f)
                )
            }

            // Price and Date Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        if (it.matches(Regex("^\\d*(\\.\\d{0,2})?$"))) {
                            onPriceChange(it)
                        }
                    },
                    label = { Text("Price") },
                    modifier = Modifier.weight(1f),
                    leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                OutlinedButton(
                    onClick = onDateClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = purchaseDate?.toString() ?: "Purchase Date",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Material
            DropdownField(
                value = selectedMaterial,
                onValueChange = onMaterialChange,
                label = "Material",
                items = Materials.all,
                icon = Icons.Default.Texture,
                modifier = Modifier.fillMaxWidth()
            )

            // Care Instructions
            OutlinedTextField(
                value = careInstructions,
                onValueChange = onCareInstructionsChange,
                label = { Text("Care Instructions") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                maxLines = 3,
                minLines = 2
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DropdownField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    items: List<String>,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text("$label${if (isRequired) " *" else ""}") },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            leadingIcon = {
                Icon(icon, contentDescription = null)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            isError = isRequired && value.isBlank(),
            supportingText = {
                if (isRequired && value.isBlank()) {
                    Text("$label is required")
                }
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun TagsSection(
    selectedTags: List<String>,
    onTagsChange: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "Tags",
        icon = Icons.Default.Tag,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        modifier = modifier
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Tags.availableTags.forEach { tag ->
                val isSelected = selectedTags.contains(tag)
                AssistChip(
                    onClick = {
                        onTagsChange(
                            if (isSelected) {
                                selectedTags - tag
                            } else {
                                selectedTags + tag
                            }
                        )
                    },
                    label = { Text(tag) },
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else null,
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (isSelected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = color
                )
            }

            content()
        }
    }
}

// Data Classes for Constants
object ClothingCategories {
    val categories = listOf(
        "Tops", "Bottoms", "Dresses", "Outerwear", "Activewear",
        "Swimwear", "Sleepwear", "Shoes", "Accessories", "Underwear"
    )

    val subCategories = mapOf(
        "Tops" to listOf("T-Shirt", "Shirt", "Blouse", "Sweater", "Hoodie", "Tank Top"),
        "Bottoms" to listOf("Jeans", "Pants", "Shorts", "Skirt", "Leggings"),
        "Dresses" to listOf("Casual", "Formal", "Cocktail", "Maxi", "Mini"),
        "Outerwear" to listOf("Jacket", "Coat", "Blazer", "Vest", "Parka"),
        "Shoes" to listOf("Sneakers", "Boots", "Sandals", "Heels", "Flats", "Loafers")
    )
}

object ClothingColors {
    val colors = listOf(
        "Black", "White", "Gray", "Navy", "Blue", "Light Blue",
        "Green", "Light Green", "Red", "Pink", "Purple", "Yellow",
        "Orange", "Brown", "Beige", "Khaki", "Multi-color", "Other"
    )
}

object Seasons {
    val all = listOf("Spring", "Summer", "Fall/Autumn", "Winter", "All Season")
}

object Materials {
    val all = listOf(
        "Cotton", "Polyester", "Silk", "Wool", "Denim", "Leather",
        "Linen", "Cashmere", "Nylon", "Rayon", "Spandex", "Velvet"
    )
}

object Tags {
    val availableTags = listOf(
        "Formal", "Casual", "Work", "Gym", "Party", "Comfort", "Vintage", "Trendy"
    )
}

object ClothingSizes {
    val sizes = listOf("XS", "S", "M", "L", "XL", "XXL")
}

// Extension function for ViewModel
@RequiresApi(Build.VERSION_CODES.O)
private fun ClothingViewModel.saveItem(
    uiState: AddItemScreenState,
    initialItem: ClothingItem?
) {
    val newItem = ClothingItem(
        id = initialItem?.id ?: generateId(), // You need to implement generateId()
        name = uiState.name,
        category = uiState.selectedCategory,
        subCategory = uiState.selectedSubCategory.takeIf { it.isNotBlank() },
        color = uiState.selectedColor,
        imageUrl = uiState.imageUrl,
        brand = uiState.brand.takeIf { it.isNotBlank() },
        size = uiState.selectedSize.takeIf { it.isNotBlank() },
        season = uiState.selectedSeason.takeIf { it.isNotBlank() },
        price = uiState.price.toDoubleOrNull(),
        purchaseDate = uiState.purchaseDate?.atStartOfDay(ZoneId.systemDefault())
            ?.toEpochSecond()?.times(1000),
        material = uiState.selectedMaterial.takeIf { it.isNotBlank() },
        careInstructions = uiState.careInstructions.takeIf { it.isNotBlank() },
        tags = uiState.selectedTags,
        wearCount = initialItem?.wearCount ?: 0,
        isFavorite = initialItem?.isFavorite ?: false
    )

    if (uiState.isEditMode) {
        editItem(newItem)
    } else {
        addItem(newItem)
    }
}

private fun generateId(): String {
    // Implement your ID generation logic here
    return System.currentTimeMillis().toString()
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Select Purchase Date",
                    style = MaterialTheme.typography.titleLarge
                )

                // Custom Date Picker (simplified)
                // In real app, use DatePicker from Accompanist or AndroidX

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            onDateSelected(selectedDate)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Select")
                    }
                }
            }
        }
    }
}

