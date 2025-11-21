package com.bussiness.slodoggiesapp.ui.component.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun AuthBackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = PrimaryColor
        )
    }
}

@Composable
fun MediaUploadSection(
    maxImages: Int = 6,
    imageList: MutableList<Uri> = mutableListOf(),
    onMediaSelected: (Uri) -> Unit = { }, // Callback for API
    onMediaUnSelected: (Uri) -> Unit = { }
) {
    val context = LocalContext.current
//    val imageUris = remember { mutableStateListOf<Uri>() }
    val imageUris = remember {
        if (imageList.isNotEmpty()) {
            mutableStateListOf(*imageList.toTypedArray())
        } else {
            mutableStateListOf()
        }
    }
    var showDialog by remember { mutableStateOf(false) }
    val cameraImageUri = remember { mutableStateOf<Uri?>(null) }
    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            cameraImageUri.value?.let { uri ->
                if (uri !in imageUris && imageUris.size < maxImages) {
                    imageUris.add(uri)          // Update grid
                    onMediaSelected(uri)        // Callback for API
                }
            }
        }
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createImageUri(context)
            cameraImageUri.value = uri
            if (uri != null) {
                cameraLauncher.launch(uri)
            }
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxImages)
    ) { uris ->
        val newUris = uris.filter { it !in imageUris }
        if (imageUris.size + newUris.size <= maxImages) {
            imageUris.addAll(newUris)
            newUris.forEach { onMediaSelected(it) }
        } else {
            Toast.makeText(
                context,
                "You can only upload up to $maxImages images",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Camera permission check
    fun launchCameraWithPermissionCheck() {
        val permission = Manifest.permission.CAMERA
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, permission) -> {
                val uri = createImageUri(context) //  always MediaStore
                if (uri != null) {
                    cameraImageUri.value = uri
                    cameraLauncher.launch(uri)
                } else {
                    Toast.makeText(context, "Failed to create image file", Toast.LENGTH_SHORT).show()
                }
            }
            else -> permissionLauncher.launch(permission)
        }
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        // Upload placeholder
        UploadPlaceholder {
            if (imageUris.size >= maxImages) {
                Toast.makeText(context, "Maximum $maxImages images allowed", Toast.LENGTH_SHORT).show()
            } else {
                showDialog = true
            }
        }

        // Media selection dialog
        if (showDialog) {
            androidx.compose.material.AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Select Option") },
                buttons = {
                    Column(Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)) {
                        Text("Camera", Modifier
                            .clickable {
                                launchCameraWithPermissionCheck()
                                showDialog = false
                            }.fillMaxWidth()
                            .padding(8.dp))

                        Text("Gallery", Modifier
                            .clickable {
                                galleryLauncher.launch(
//                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                                )
                                showDialog = false
                            }.fillMaxWidth()
                            .padding(8.dp))
                    }
                }
            )
        }

        Spacer(Modifier.height(12.dp))

        // Selected images grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 450.dp),
        ) {
            items(imageUris, key = { it.hashCode() }) { uri ->
                Box(
                    modifier = Modifier.size(105.dp).padding(5.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
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
                            .offset(x = (6).dp, y = (-6).dp) //overlap outside image
                            .size(20.dp)
                            .clickable { imageUris.remove(uri)
                                        onMediaUnSelected(uri)}
                    )
                }

            }
        }
    }
}

/**
 * Create a new image URI in MediaStore
 */
fun createImageUri(context: Context): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/YourAppName")
    }

    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )
}



@Composable
fun UploadPlaceholder(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFFE5EFF2))
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

