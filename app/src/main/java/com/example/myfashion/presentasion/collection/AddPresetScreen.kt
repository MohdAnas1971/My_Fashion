package com.example.myfashion.presentasion.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myfashion.presentasion.navigation.NavRoutes
import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.data.local.room.preset.Preset


@Composable
fun AddEditPresetScreen(
    presetId: Int? = null,
    onSave: (Preset) -> Unit ={},
    navController: NavController,
   addEditPresetViewModel: AddEditPresetViewModel
) {

    val uiState by addEditPresetViewModel.uiState.collectAsState()
    val selectedItems by addEditPresetViewModel.selectedItems.collectAsState()

    var showItemSelector by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            AddEditPresetTopBar(
                isEditing = presetId != null,
                onSave = { addEditPresetViewModel.savePreset();navController.navigateUp() },
                onCancel = {navController.navigateUp()},
                isValid = addEditPresetViewModel.isFormValid()
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Required field
            OutlinedTextField(
                value = uiState.name,
                onValueChange = { addEditPresetViewModel.updateName(it) },
                label = { Text("Preset Name *") },
                placeholder = { Text("e.g., Casual Friday, Workout Gear") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.name.isBlank(),
                supportingText = {
                    if (uiState.name.isBlank()) {
                        Text("Name is required")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Items selection card
            ItemsSelectionCard(
                selectedItems = selectedItems,
                onClick = { navController.navigate(NavRoutes.CategoryScreen("selection"))},
                modifier = Modifier.fillMaxWidth()
            )



            Spacer(modifier = Modifier.height(16.dp))

            // Optional description
            OutlinedTextField(
                value = uiState.description ?: "",
                onValueChange = { addEditPresetViewModel.updateDescription(it) },
                label = { Text("Description") },
                placeholder = { Text("Optional description...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp, max = 120.dp),
                maxLines = 3,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tags input
            TagsInputSection(
                tags = uiState.tags,
                onAddTag = { addEditPresetViewModel.addTag(it) },
                onRemoveTag = { addEditPresetViewModel.removeTag(it) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Favorite toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add to Favorites",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = uiState.isFavorite,
                    onCheckedChange = { addEditPresetViewModel.toggleFavorite() }
                )
            }

            // Helper text
            Text(
                text = "* Required field",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }

    // Launch item selector
    if (showItemSelector) {
      /*  ItemSelectorBottomSheet(
            initialSelection = selectedItems,
            onConfirm = { items ->
                viewModel.updateSelectedItems(items)
                showItemSelector = false
            },
            onDismiss = { showItemSelector = false }
        )*/
    }
}

@Composable
fun ItemsSelectionCard(
    selectedItems: List<ClothingItem>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Checklist,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Clothing Items",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (selectedItems.isEmpty()) {
                Text(
                    text = "Tap to select items",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(selectedItems) { item ->
                        SelectedItemChip(item = item)
                    }
                }

                Text(
                    text = "${selectedItems.size} item${if (selectedItems.size != 1) "s" else ""} selected",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SelectedItemChip(item: ClothingItem) {
    Card(
        modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Item image or placeholder
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Overlay with item name
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0.5f
                        )
                    )
                    .padding(4.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}

@Composable
fun TagsInputSection(
    tags: List<String>,
    onAddTag: (String) -> Unit,
    onRemoveTag: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newTag by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        Text(
            text = "Tags",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Input for new tag
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = newTag,
                onValueChange = { newTag = it },
                placeholder = { Text("Add a tag...") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (newTag.isNotBlank()) {
                            onAddTag(newTag.trim())
                            newTag = ""
                        }
                    }
                )
            )

            IconButton(
                onClick = {
                    if (newTag.isNotBlank()) {
                        onAddTag(newTag.trim())
                        newTag = ""
                    }
                },
                enabled = newTag.isNotBlank()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add tag")
            }
        }

        // Display tags
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            tags.forEach { tag ->
                InputChip(
                    selected = false,
                    onClick = { onRemoveTag(tag) },
                    label = { Text(tag) },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Remove tag",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPresetTopBar(
    isEditing: Boolean,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    isValid: Boolean
) {
    TopAppBar(
        title = { Text(if (isEditing) "Edit Preset" else "New Preset") },
        navigationIcon = {
            IconButton(onClick = onCancel) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        },
        actions = {
            TextButton(
                onClick = onSave,
                enabled = isValid
            ) {
                Text(if (isEditing) "Update" else "Save")
            }
        }
    )
}