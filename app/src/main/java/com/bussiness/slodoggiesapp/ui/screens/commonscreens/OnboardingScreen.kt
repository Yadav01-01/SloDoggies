package com.bussiness.slodoggiesapp.ui.screens.commonscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.OnboardingPage
import com.bussiness.slodoggiesapp.navigation.Routes
import kotlinx.coroutines.launch

val onboardingPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.onboard1, // Replace with actual drawables
        title = "Welcome to SloDoggies",
        description = "Discover a tail-wagging world where dog lovers and trusted local services connect. SLO county's pet scene just got its own home."
    ),
    OnboardingPage(
        imageRes = R.drawable.onboard1,
        title = "Sniff Out Local Pet Pros",
        description = "Search for groomers, walkers, trainers, and more -powered by real reviews, geolocation, and a love for all things furry."
    ),
    OnboardingPage(
        imageRes = R.drawable.onboard3,
        title = "Paws, Play & Participate",
        description = "Join dog friendly events, follow favorite providers, explore pet places and make lifelong friends - bothtwo-and four-legged. "
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

            // "Next" or "Get Started" button
            Button(
                onClick = {
                    scope.launch {
                        if (isLastPage) {
                            navController.navigate(Routes.JOIN_THE_PACK)
                        } else {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF258694))
            ) {
                Text(
                    text = if (isLastPage) "Get Started" else "Next",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Always present "Skip" button (hidden with alpha on last page)
            Button(
                onClick = onFinish,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .alpha(if (isLastPage) 0f else 1f), // Hide visually, reserve space
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECEBEB)),
                enabled = !isLastPage // Disable interaction when invisible
            ) {
                Text(
                    text = "Skip",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color(0xFF949494)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
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
                .fillMaxHeight(0.35f)
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

                Spacer(Modifier.height(20.dp))

                Text(
                    text = page.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.outfit_bold)),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
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
                    .width(if (isSelected) 20.dp else 20.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (isSelected) Color(0xFF258694) else Color.Transparent
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFF258694) else Color(0xFF949494),
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
