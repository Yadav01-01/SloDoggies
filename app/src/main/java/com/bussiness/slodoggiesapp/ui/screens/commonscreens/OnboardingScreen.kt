package com.bussiness.slodoggiesapp.ui.screens.commonscreens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.OnboardingPage
import kotlinx.coroutines.launch

val onboardingPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.onboard1, // Replace with actual drawables
        title = "Welcome to SloDoggies",
        description = "Discover a tail-wagging world where dog lovers and trusted local services connect."
    ),
    OnboardingPage(
        imageRes = R.drawable.onboard2,
        title = "Sniff Out Local Pet Pros",
        description = "Search for groomers, walkers, trainers, and more—powered by real reviews."
    ),
    OnboardingPage(
        imageRes = R.drawable.logo,
        title = "Paws, Play & Participate",
        description = "Join dog-friendly events, explore pet places and make friends."
    )
)

@Composable
fun OnboardingScreen(navController: NavHostController, onFinish: () -> Unit) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(
                page = onboardingPages[page],
                currentPage = pagerState.currentPage,
                totalPages = onboardingPages.size
            )
        }




        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            val isLastPage = pagerState.currentPage == onboardingPages.lastIndex

            Button(
                onClick = {
                    scope.launch {
                        if (isLastPage) {
                            onFinish()
                        } else {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00897B))
            ) {
                Text(
                    text = if (isLastPage) "Get Started" else "Next",
                    color = Color.White
                )
            }

            if (!isLastPage) {
                TextButton(
                    onClick = onFinish,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Skip", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    currentPage: Int,
    totalPages: Int
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Top Image
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = "Onboarding Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            contentScale = ContentScale.Crop
        )

        // Bottom Rounded White Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.38f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(Modifier.height(25.dp))
                // Custom Page Indicator ABOVE the title
                PageIndicator(currentPage = currentPage, totalPages = totalPages)

                Text(
                    text = page.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun PageIndicator(currentPage: Int, totalPages: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        repeat(totalPages) { index ->
            val isSelected = index == currentPage
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(10.dp)
                    .width(if (isSelected) 24.dp else 16.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (isSelected) Color(0xFF00897B) else Color.Transparent
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFF00897B) else Color.Gray,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}




@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    val navController = rememberNavController()
    OnboardingScreen(
        navController = navController,
        onFinish = { /* No-op for preview */ }
    )
}
