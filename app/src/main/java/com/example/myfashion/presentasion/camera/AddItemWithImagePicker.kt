package com.example.myfashion.presentasion.camera

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


// Function to open camera with permission check
private fun openCamera(context: Context, onImageSelected: (Uri?) -> Unit) {
    // Using TakePicturePreview which doesn't require FileProvider
    // This is simpler and works on all devices
}

// Save bitmap to file (alternative method)
private fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val filename = "image_$timeStamp.jpg"

        // Save to internal storage
        val file = File(context.filesDir, filename)
        val outputStream = file.outputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        outputStream.flush()
        outputStream.close()

        // Use FileProvider to get URI
        FileProvider.getUriForFile(
            context, "${context.packageName}.fileprovider", file
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}




// Alternative with BottomSheet dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakeImage(
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    // Create a temporary file for camera photo
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.externalCacheDir ?: context.cacheDir
    val tempFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val uri = getImageUri(context, tempFile)
            onImageSelected(uri)
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            onImageSelected(it)
        }
    }

    // Add image button
    FancyAddItemButton(onClick = {showBottomSheet = true})
    /*Button(
        onClick = { showBottomSheet = true },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("+")
            Text("Add Item", style = MaterialTheme.typography.titleMedium)
        }
    }*/

    // Bottom Sheet Dialog
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = rememberModalBottomSheetState(),
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Text(
                    text = "Add New Item",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Choose how you want to add an image for your item",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Camera option
                Card(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        showBottomSheet = false
                        val uri = getImageUri(context, tempFile)
                        cameraLauncher.launch(uri)
                    }, colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("📷", fontSize = 24.sp)
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Take Photo",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Use your camera to take a new photo",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Gallery option
                Card(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        showBottomSheet = false
                        galleryLauncher.launch("image/*")
                    }, colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("🖼️", fontSize = 24.sp)
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Choose from Gallery",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Select an existing photo from your gallery",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Cancel button
                OutlinedButton(
                    onClick = { showBottomSheet = false },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

// Function to get URI for camera image
private fun getImageUri(context: Context, file: File): Uri {
    return FileProvider.getUriForFile(
        context, "${context.packageName}.provider", file
    )
}

// Example usage in a screen
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExampleScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Clothing Item",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Use either version:
        // AddItemWithImagePicker(
        TakeImage(
            onImageSelected = { uri ->
                selectedImageUri = uri
                // Handle the selected image here
                // You can upload it, save it locally, etc.
            })

        // Show selected image if any
        selectedImageUri?.let { uri ->
            Spacer(modifier = Modifier.height(32.dp))
            Text("Image selected: ${uri.path}")
        }
    }
}


@Composable
fun FancyAddItemButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    shape = RoundedCornerShape(18.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.AddPhotoAlternate,
                    contentDescription = "Add Item",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(22.dp)
                )

                Text(
                    text = "Add Item",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
