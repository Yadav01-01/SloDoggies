package com.bussiness.slodoggiesapp.ui.component.common

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun AuthBackButton(navController : NavHostController, modifier: Modifier){
    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .padding( top = 20.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = PrimaryColor
        )
    }
}


@Composable
fun MediaUploadSection() {
    val context = LocalContext.current
    val imageUris = remember { mutableStateListOf<Uri>() }
    val maxImages = 10
    var showDialog by remember { mutableStateOf(false) }

    // Gallery Picker
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxImages)
    ) { uris ->
        if (uris.isNotEmpty()) {
            val newUris = uris.filter { it !in imageUris } // Filter duplicates
            if (imageUris.size + newUris.size <= maxImages) {
                imageUris.addAll(newUris)
            } else {
                Toast.makeText(context, "You can only upload up to $maxImages images", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Camera Capture
    val cameraImageUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            cameraImageUri.value?.let { uri ->
                if (uri !in imageUris) { // Prevent duplicate
                    if (imageUris.size < maxImages) {
                        imageUris.add(uri)
                    } else {
                        Toast.makeText(context, "You can only upload up to $maxImages images", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Upload Box
        UploadPlaceholder {
            if (imageUris.size >= maxImages) {
                Toast.makeText(
                    context,
                    "Maximum $maxImages images allowed",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                showDialog = true
            }
        }

        // Compose Dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Select Media") },
                text = {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Text(
                            text = "Gallery",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    galleryLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                    showDialog = false
                                }
                                .padding(16.dp)
                        )
                        Text(
                            text = "Camera",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val uri = createImageUri(context)
                                    cameraImageUri.value = uri
                                    cameraLauncher.launch(uri)
                                    showDialog = false
                                }
                                .padding(16.dp)
                        )
                    }
                }
            )
        }


        Spacer(Modifier.height(12.dp))

        // Selected Images Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 450.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = imageUris,
                key = { it.hashCode() } // Stable key
            ) { uri ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_cross_icon),
                        contentDescription = "Remove",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(18.dp)
                            .offset(y= ((-6).dp))
                            .clickable { imageUris.remove(uri) }
                    )
                }
            }
        }
    }
}




@Composable
fun UploadPlaceholder(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .clip(RoundedCornerShape(10.dp)) // Clip first
            .background(color = Color(0xFFE5EFF2)) // Then apply background
            .dashedBorder(
                strokeWidth = 2.dp,
                color = PrimaryColor,
                cornerRadius = 10.dp,
                dashLength = 10.dp,
                gapLength = 8.dp
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.upload_ic),
                contentDescription = "upload",
                tint = PrimaryColor
            )
            Spacer(Modifier.height(3.dp))
            Text(
                "Upload Here",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                ),
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = PrimaryColor
            )
        }
    }
}



@SuppressLint("SuspiciousModifierThen")
fun Modifier.dashedBorder(
    strokeWidth: Dp,
    color: Color,
    cornerRadius: Dp = 0.dp,
    dashLength: Dp = 10.dp,
    gapLength: Dp = 5.dp
): Modifier = this.then(
    drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
            )
        )

        val corner = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        drawRoundRect(
            color = color,
            style = stroke,
            cornerRadius = corner,
            size = size
        )
    }
)



fun createImageUri(context: Context): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
}
