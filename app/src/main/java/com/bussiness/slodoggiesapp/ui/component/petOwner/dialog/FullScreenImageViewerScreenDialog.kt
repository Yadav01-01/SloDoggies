package com.bussiness.slodoggiesapp.ui.component.petOwner.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import kotlinx.coroutines.launch

@Composable
fun FullScreenImageViewerScreen(
    images: List<Int>,
    initialIndex: Int,
    onDismiss: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(initialIndex) }
    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        pageCount = { images.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        currentIndex = pagerState.currentPage
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x4D7BB6BE))
        ) {

            // This Box will hold the pager and close button together
            Box(
                modifier = Modifier
                    .size(450.dp)
                    .padding(horizontal = 10.dp)
                    .align(Alignment.Center)
            ) {
                // HorizontalPager
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                ) { page ->
                    AsyncImage(
                        model = images[page],
                        contentDescription = "Full Screen Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Fit
                    )
                }

                // Cross icon aligned to top-end of pager box
                Image(
                    painter = painterResource(id = R.drawable.ic_cross_iconx),
                    contentDescription = "Close",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onDismiss)
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(images.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .background(
                                    color = if (index == currentIndex) Color.White else Color.Gray,
                                    shape = CircleShape
                                )
                        )
                    }
                }
                val scope = rememberCoroutineScope()

                // Previous button
                if (currentIndex > 0) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_previos_arrow_icon) ,
                        contentDescription = "Previous",
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp).clickable {
                                currentIndex--
                                scope.launch {
                                    pagerState.animateScrollToPage(currentIndex)
                                }
                            }
                    )
                }

                // Next button
                if (currentIndex < images.size - 1) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_next_arrow_icon) ,
                        contentDescription = "Next",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp).clickable {
                                currentIndex++
                                scope.launch {
                                    pagerState.animateScrollToPage(currentIndex)
                                }
                            }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenImageViewerScreenPreview() {
    val sampleImages = listOf(
        R.drawable.ic_cross_icon,
        R.drawable.ic_cross_icon,
        R.drawable.ic_cross_icon
    )

    FullScreenImageViewerScreen(
        images = sampleImages,
        initialIndex = 0,
        onDismiss = {}
    )
}