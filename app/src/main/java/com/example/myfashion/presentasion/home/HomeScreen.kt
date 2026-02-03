package com.example.myfashion.presentasion.home

import android.util.Log
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myfashion.presentasion.navigation.NavRoutes
import com.example.myfashion.R
import com.example.myfashion.data.local.MockData
import com.example.myfashion.presentasion.Category
import com.example.myfashion.presentasion.FashionViewModel
import com.example.myfashion.presentasion.ui_utils.AppBottomBar
import com.example.myfashion.ui.theme.Blue50
import com.example.myfashion.ui.theme.DeepPurple700
import com.example.myfashion.ui.theme.Green50
import com.example.myfashion.ui.theme.Green500
import com.example.myfashion.ui.theme.Green700
import com.example.myfashion.ui.theme.MyFashionTheme
import com.example.myfashion.ui.theme.Orange50
import com.example.myfashion.ui.theme.Orange500
import com.example.myfashion.ui.theme.Orange700
import com.example.myfashion.ui.theme.Purple50
import com.example.myfashion.ui.theme.Purple500


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomePreview() {
    val viewModel: FashionViewModel = hiltViewModel()
    val navController: NavController = rememberNavController()
    MyFashionTheme {
        HomeScreen(viewModel = viewModel, navController = navController)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FashionViewModel, navController: NavController
) {
    val context = LocalContext.current

    val colorScheme = MaterialTheme.colorScheme
    var selectedCategory by remember { mutableIntStateOf(1) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isLargeScreen = screenWidth > 600.dp

    val categories = remember { MockData.categories }
    val wardrobeItems = remember { MockData.categories }


   val weatherViewModel: WeatherViewModel =hiltViewModel()
    val weatherUiState by weatherViewModel.uiState.collectAsState()

    // ✅ Remember helper (created once)
    val locationHelper = remember {
        LocationHelper(context)
    }

    // ✅ Side-effect: runs ONLY ONCE
    LaunchedEffect(Unit) {
        locationHelper.getCurrentLocation(
            onLocation = { lat, lon ->
                Log.d("HomeScreen", "lat:$lat, lon:$lon")
                weatherViewModel.loadWeather(lat, lon)
                weatherViewModel.startAutoRefresh(10)
            },
            onError = {
                Log.d("HomeScreen", "Location Error")
            }
        )
    }

                Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            SimpleTopBar()
        },
        bottomBar = {
            AppBottomBar(navController = navController, "Home")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .background(colorScheme.background)
        ) {
            // Header Section
            HeaderSection()

            Spacer(modifier = Modifier.height(24.dp))


            // AddItemWithImagePicker(
           /* TakeImage(
                onImageSelected ={uri ->

                }
            )*/
            // Add item button
            OutlinedButton(
                onClick = { navController.navigate(NavRoutes.AddItemScreen) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = "Add",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Add Clothing Item",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))



            // Weather & Reminder Card
            WeatherReminderCard(weatherUiState)

            Spacer(modifier = Modifier.height(16.dp))

            // Wardrobe Grid
            WardrobeGrid(
                items = wardrobeItems,
                isLargeScreen = isLargeScreen,
                getItemCount = {viewModel.getItemCount(it)},
                onCategorySelected = {categoryName->
                    viewModel.selectCategory(categoryName)
                    navController.navigate(
                        NavRoutes.CategoryScreen(categoryName))}
            )

            Spacer(modifier = Modifier.height(80.dp)) // Space for bottom nav

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Composable
fun HeaderSection() {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Good Morning,",
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Text(
            text = "Muhammad ALi  Husnain",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = colorScheme.onSurface
        )
    }
}

@Composable
fun WeatherReminderCard(weatherUiState: WeatherUiState) {

    val colorScheme = MaterialTheme.colorScheme


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {

                when (weatherUiState) {
                    is WeatherUiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is WeatherUiState.Success -> {
                        val data = (weatherUiState as WeatherUiState.Success).data
                        val weatherCondition = data.weather.firstOrNull()

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WeatherIcon(weatherCondition?.icon ?:"03d")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${data.main.temp}°C",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = colorScheme.onSurface
                            )
                        }
                        Text(
                            text = "Wear light outfit today",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    is WeatherUiState.Error -> {
                        Text("Error loading weather",color=colorScheme.error)
                    }
                }

            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Meeting at",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "09:20 PM",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun WeatherIcon(iconCode: String) {

    val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(iconUrl)
            .crossfade(true)
            .build(),
        contentDescription = "Weather Icon",
        modifier = Modifier.size(32.dp)
    )
}

@Composable
fun CategoriesSection(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        val quickAction =getQuickActions()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {

            items(quickAction) { action ->
                QuickActionCard(action) {
                    when (action.title) {
                        "Privacy" -> navController.navigate("privacy")
                        "Upload Image" -> navController.navigate("upload")
                        "Category" -> navController.navigate(NavRoutes.CategoryScreen(""))
                        "Select Outfit" -> navController.navigate("outfits")
                    }
                }
            }
        }
    }
}

@Composable
fun GetStartedCard(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ready to Organize?",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start organizing your wardrobe with our smart tools",
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onPrimary.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("get_started") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.onPrimary,
                    contentColor = colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopBar() {
    val colorScheme = MaterialTheme.colorScheme

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF302F2F)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_picture),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(44.dp)
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorScheme.background,
            scrolledContainerColor = colorScheme.background
        )
    )
}
// Data class remains the same
data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val iconBackgroundColor: Color,
    val iconColor: Color,
    val textColor: Color
)
@Composable
fun QuickActionCard(
    action: QuickAction, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(140.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.White, MaterialTheme.colorScheme.primaryContainer,)))){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(action.iconBackgroundColor), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = action.title,
                        tint = action.iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = action.title, style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ), color = action.textColor, textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun WardrobeGrid(
    items: List<Category>,
    isLargeScreen: Boolean,
    getItemCount: (String) -> Int,
    onCategorySelected: (String) -> Unit,

) {
    val gridItems = if (isLargeScreen) {
        items.chunked(3) // 3 columns for large screens
    } else {
        items.chunked(2) // 2 columns for mobile
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        gridItems.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    WardrobeCategoryCard(item = item,
                        getItemCount,
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = {
                                onCategorySelected(item.name)
                            }
                            ))
                }
                // Add empty space if row has less than max columns
                repeat(maxOf(0, (if (isLargeScreen) 3 else 2) - rowItems.size)) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun WardrobeCategoryCard(
    item: Category,
    getItemCount: (String) -> Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface//item.backgroundColor
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Inner card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder for 3D model/image
                Image(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.name,
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Item info
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = getItemCount(item.name).toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun getQuickActions(): List<QuickAction> {
    val colorScheme = MaterialTheme.colorScheme

    return listOf(
        QuickAction(
            title = "Collection",
            icon = Icons.Default.Category,
            backgroundColor = Purple50,
            iconBackgroundColor = Purple500,
            iconColor = Color.White,
            textColor = DeepPurple700
        ),
        QuickAction(
            title = "Upload Image",
            icon = Icons.Default.AddPhotoAlternate,
            backgroundColor = Blue50,
            iconBackgroundColor = colorScheme.primary,
            iconColor = Color.White,
            textColor = colorScheme.primary
        ),
        QuickAction(
            title = "Select Outfit",
            icon = Icons.Default.Checkroom,
            backgroundColor = Orange50,
            iconBackgroundColor = Orange500,
            iconColor = Color.White,
            textColor = Orange700
        ),
        QuickAction(
            title = "Privacy",
            icon = Icons.Default.Security,
            backgroundColor = Green50,
            iconBackgroundColor = Green500,
            iconColor = Color.White,
            textColor = Green700
        ),
    )
}

