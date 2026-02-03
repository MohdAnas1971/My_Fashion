package com.example.myfashion.presentasion.category

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.infittron.presentation.navigation.NavRoutes
import com.example.myfashion.data.local.MockData
import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.presentasion.Category
import com.example.myfashion.presentasion.FashionViewModel
import com.example.myfashion.presentasion.collection.AddEditPresetViewModel
import com.example.myfashion.presentasion.crde_operation.ClothingViewModel
import com.example.myfashion.ui.theme.CloudyColor
import com.example.myfashion.ui.theme.SunnyColor
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    viewModel: FashionViewModel,
    addEditPresetViewModel: AddEditPresetViewModel,
    navController: NavController,
    isSelectionOn: Boolean,
) {
    val clothingViewModel: ClothingViewModel = hiltViewModel()

    viewModel.uiState
    val selectedCategory = viewModel.selectedCategory
    val categories = MockData.categories/*by remember { derivedStateOf {
        (uiState as? FashionUiState.Success)?.categories ?: emptyList()
    } }*/

    val allItems by clothingViewModel.allItems.collectAsState(
        emptyList()
    )

    Log.d("CategoryScreen", "allItems :${allItems.size}")
    val items by remember(allItems, selectedCategory) {
        derivedStateOf {
            val selected = selectedCategory?.name ?: "All"

            if (selected == "All") {
                allItems
            } else {
                allItems.filter { it.category == selected }
            }
        }
    }

    Log.d("CategoryScreen", "items:${items.size}")

    val selectedItems by addEditPresetViewModel.selectedItems.collectAsState()

    // Auto-select first category if none selected
    LaunchedEffect(categories) {
        if (selectedCategory == null && categories.isNotEmpty()) {
            viewModel.selectCategory(categories.first().name)
        }
    }

    Scaffold(
        topBar = {
            CategoryTopBar(navController, isSelectionOn, onSelectionDone = {})
        },/*  bottomBar = {
            AppBottomBar(navController,"Collection")
        },*/
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background)

        ) {
            // Animated Category Header
            AnimatedCategoryHeader(selectedCategory, getItemCount = { viewModel.getItemCount(it) })

            Spacer(modifier = Modifier.height(16.dp))

            // Categories Lazy Row
            CategoriesLazyRow(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    viewModel.selectCategory(category.name)
                })

            Spacer(modifier = Modifier.height(24.dp))

            // Items Grid
            ItemsGridSection(
                items = items,
                selectedCategory = selectedCategory,
                onItemClick = { item ->
                    viewModel.selectClothingItem(item)
                    navController.navigate(NavRoutes.PreviewScreen(item.id))
                },
                navController = navController,
                isSelectionOn = isSelectionOn,
                addEditPresetViewModel = addEditPresetViewModel,
               selectedItems= selectedItems
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCategoryHeader(selectedCategory: Category?, getItemCount: (String) -> Int) {

    AnimatedContent(
        targetState = selectedCategory, transitionSpec = {
            fadeIn(
                animationSpec = tween(
                    300,
                    delayMillis = 100
                )
            ) with fadeOut(animationSpec = tween(100))
        }, label = "CategoryHeader"
    ) { category ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (category != null) {
                // Animated Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                            shape = CircleShape
                        ), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = category.iconRes),
                        contentDescription = category.name,
                        modifier = Modifier.size(40.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Category Name with Animation
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.animateContentSize()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Item Count
                Text(
                    text = "${getItemCount(category.name)} items",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            } else {
                // Placeholder when no category selected
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape
                        ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Category,
                        contentDescription = "Select Category",
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}


@Composable
fun CategoriesLazyRow(
    categories: List<Category>, selectedCategory: Category?, onCategorySelected: (Category) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.height(120.dp)
    ) {
        items(categories) { category ->
            CategoryCard(
                category = category,
                isSelected = selectedCategory?.name == category.name,
                onClick = { clickedCategory -> onCategorySelected(clickedCategory) })
        }
    }
}

@Composable
fun CategoryCard(
    category: Category, isSelected: Boolean, onClick: (Category) -> Unit
) {

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f, animationSpec = tween(300), label = "CardScale"
    )

    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(onClick = { onClick(category) }),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            BorderStroke(2.dp, Brush.linearGradient(listOf(SunnyColor, CloudyColor)))
        } else {
            BorderStroke(0.dp, Color.Transparent)
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surface.copy(
                            alpha = 0.3f
                        )
                    ), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = category.iconRes),
                    contentDescription = category.name,
                    modifier = Modifier.size(20.dp),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = category.name, style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                ), color = if (isSelected) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }, textAlign = TextAlign.Center, maxLines = 2, fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ItemsGridSection(
    items: List<ClothingItem>,
    selectedCategory: Category?,
    onItemClick: (ClothingItem) -> Unit,
    navController: NavController,
    isSelectionOn: Boolean,
    addEditPresetViewModel: AddEditPresetViewModel,
    selectedItems: List<ClothingItem>
) {
    val fashionViewModel: FashionViewModel = hiltViewModel()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedCategory?.name ?: "All Items",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "${items.size} items",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (items.isEmpty()) {
            EmptyStateView(onAddItemClick = { navController.navigate(NavRoutes.AddItemScreen) })
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(500.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    ClothingItemCard(
                        item = item,
                        isSelectionOn = isSelectionOn,
                        isSelected = selectedItems.any{it.id==item.id},
                        onClick = { onItemClick(it) },
                        onFavouriteClick = { fashionViewModel.toggleFavorite(item.id)/* mark favourite */ },
                        onDeleteClick = { fashionViewModel.deleteItem(item.id)/* delete item */ },
                        onSelectClick = { addEditPresetViewModel.toggleSelection(item)/* toggle selection */ },
                        addEditPresetViewModel=addEditPresetViewModel
                    )
                }
            }
        }
    }
}


@Composable
fun ClothingItemCard(
    item: ClothingItem,
    isSelectionOn: Boolean,
    addEditPresetViewModel: AddEditPresetViewModel,
    isSelected: Boolean =addEditPresetViewModel.isItemSelected(item.id),
    onClick: (ClothingItem) -> Unit,
    onFavouriteClick: (ClothingItem) -> Unit,
    onDeleteClick: (ClothingItem) -> Unit,
    onSelectClick: (ClothingItem) -> Unit
) {
    val itemColor = MaterialTheme.colorScheme.primary
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f)
            .clickable { if (!isSelectionOn){onClick(item)} },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = itemColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(item.imageUrl)
                        .crossfade(true).build(),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // 🔹 Selection Button (Top Left)
                if (isSelectionOn) {
                    IconButton(
                        onClick = { onSelectClick(item) },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp)
                    ) {
                        Icon(
                            imageVector = if (isSelected) Icons.Default.CheckCircle
                            else Icons.Default.RadioButtonUnchecked,
                            contentDescription = "Select",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // ❤️ Favourite Icon (Top Right)
                IconButton(
                    onClick = { onFavouriteClick(item) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favourite",
                        tint = if (item.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                    )
                }

            }

            // Item Info (UNCHANGED)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.brand ?: "Unknown Brand",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.color ?: "",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )


                    // 🗑 Delete Icon  if Selection off
                    if (!isSelectionOn) {

                        IconButton(
                            onClick = { showDeleteDialog = true }, modifier = Modifier.padding(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }

                        if (showDeleteDialog) {
                            AlertDialog(onDismissRequest = { showDeleteDialog = false }, title = {
                                Text("Delete Item")
                            }, text = {
                                Text("Are you sure you want to delete this clothing item?")
                            }, confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDeleteDialog = false
                                        onDeleteClick(item)
                                    }) {
                                    Text(
                                        text = "Delete", color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }, dismissButton = {
                                TextButton(
                                    onClick = { showDeleteDialog = false }) {
                                    Text("Cancel")
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateView(onAddItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Inventory2,
                contentDescription = "Empty",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No Items Yet", style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            ), color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Add items to this category to see them here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Navigate to add item */ onAddItemClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Item",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add First Item", style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryTopBar(
    navController: NavController,
    isSelectionOn: Boolean,
    onSelectionDone: () -> Unit
) {
    TopAppBar(
        title = {
        Text(
            text = "Categories", style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold
            ), color = MaterialTheme.colorScheme.onSurface
        )
    }, navigationIcon = {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
            // .background(MaterialTheme.colorScheme.surface)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }, actions = {

        Row(Modifier.padding(end = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            if (!isSelectionOn) {
                TextButton(
                    onClick = { /*Handel Add Item*/ navController.navigate(NavRoutes.AddItemScreen) },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .height(32.dp)

                ) {
                    Text("Add Item", color = MaterialTheme.colorScheme.onPrimary)
                }
            } else {
                TextButton(
                    onClick = { onSelectionDone() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .height(32.dp)

                ) {
                    Text("Done", color = MaterialTheme.colorScheme.onPrimary)
                }

            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        scrolledContainerColor = MaterialTheme.colorScheme.background
    )
    )
}

