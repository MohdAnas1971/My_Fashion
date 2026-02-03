package com.example.myfashion.presentasion.preview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myfashion.R
import com.example.myfashion.data.local.room.ClothingItem
import com.example.myfashion.domain.model.Collection
import com.example.myfashion.domain.model.WearOption
import com.example.myfashion.presentasion.FashionViewModel

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun PreviewScreen(
    itemId: String, viewModel: FashionViewModel, navController: NavController
) {
    val previewViewModel: PreviewViewModel = hiltViewModel()

    val allItems by previewViewModel.allItems.collectAsState(emptyList())

    remember { viewModel.selectedClothItem }
    val wearOptions = previewViewModel.wearOption
    val selectedWearOption = previewViewModel.currentWearOption

    val itemsToShow = allItems.filter {
        it.category.equals(selectedWearOption?.name, ignoreCase = true)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        // 3D Viewer Area
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // This would be your 3D model viewer (Sceneform, Filament, etc.)
            // For now, placeholder
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top =70.dp)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.10f)),
                contentAlignment = Alignment.TopCenter
            ) {
                // 3D Model placeholder
                Image(
                    painter = painterResource(id = R.drawable.ic_clothing),
                    contentDescription = "3D Model",
                    modifier = Modifier.height(400.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Wear Options Panel

        WearOptionsWithPreviewRow(
            itemsToShow = itemsToShow,
            wearOptions = wearOptions,
            selectedWearOption = selectedWearOption,
            onWearOptionSelected = { previewViewModel.selectWearOption(it) },
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        // Control Panel
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 44.dp)
        ) {
            ControlButton(
                icon = R.drawable.ic_rotate, onClick = { /* Rotate model */ })

            Spacer(modifier = Modifier.height(8.dp))

            ControlButton(
                icon = R.drawable.ic_zoom, onClick = { /* Zoom model */ })

            Spacer(modifier = Modifier.height(8.dp))

            ControlButton(
                icon = R.drawable.ic_reset, onClick = { /* Reset view */ })
        }

        // Close Button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 32.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun WearOptionsWithPreviewRow(
    itemsToShow: List<ClothingItem>,
    wearOptions: List<WearOption>,
    selectedWearOption: WearOption?,
    onWearOptionSelected: (WearOption) -> Unit,
    modifier: Modifier = Modifier
) {
    // Wear Options Panel
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp)
            .height(300.dp) // ⬅ increased height to fit preview row
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent, Color.Black.copy(alpha = 0.8f)
                    )
                )
            ),
        verticalArrangement = Arrangement.Bottom
    ) {

        // 🔹 TOP: Preview Items Row
        if (itemsToShow.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = itemsToShow, key = { it.id }) { item ->
                    ClothingItemCard(
                        item = item, onClick = {})
                }
            }
        }else{
            Spacer(Modifier.height(80.dp))
        }

        Spacer(Modifier.height(8.dp))
        // 🔹 WEAR OPTIONS title
        Text(
            text = "WEAR OPTIONS",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 24.dp, top = 12.dp)
        )

        // 🔹 Wear Options Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(wearOptions) { option ->
                WearOptionChip(
                    option = option,
                    isSelected = selectedWearOption?.id == option.id,
                    onClick = { onWearOptionSelected(option) })
            }
        }
    }

}


@Composable
fun WearOptionChip(
    option: WearOption, isSelected: Boolean, onClick: () -> Unit
) {
    Card(
        modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) option.color else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = option.iconRes),
                contentDescription = option.name,
                modifier = Modifier.size(32.dp),
            )

            Text(
                text = option.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


// ControlButton.kt
@Composable
fun ControlButton(
    icon: Int, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(48.dp), shape = CircleShape, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Control",
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

// Also, let me add the missing components:

@Composable
fun CollectionCard(
    collection: Collection, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(180.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Gradient background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = collection.name, style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ), color = Color.White, maxLines = 2, overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = collection.description ?: "Description",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Badge(
                        containerColor = Color.White.copy(alpha = 0.2f), contentColor = Color.White
                    ) {
                        Text(
                            text = "${collection.itemCount} items",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Icon(
                        Icons.AutoMirrored.Filled.ArrowRightAlt,
                        contentDescription = "View",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ClothingItemCard(
    item: ClothingItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x20FFFFFF)
        ),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            if (item.imageUrl.isNullOrBlank()) {
                // 🔹 Placeholder (your existing UI)
                PlaceholderContent(item)
            } else {
                // 🔹 Coil Image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize(),
                    error = painterResource(R.drawable.ic_clothing),
                    placeholder = painterResource(R.drawable.ic_clothing)
                )
            }
        }
    }
}

@Composable
private fun PlaceholderContent(item: ClothingItem) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2D3047),
                        Color(0xFF1A1A2E)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_clothing),
            contentDescription = item.name,
            modifier = Modifier.size(60.dp),
            colorFilter = ColorFilter.tint(
                Color.White.copy(alpha = 0.3f)
            )
        )
    }
}





