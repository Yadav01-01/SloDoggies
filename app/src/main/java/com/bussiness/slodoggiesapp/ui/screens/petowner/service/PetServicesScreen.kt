package com.bussiness.slodoggiesapp.ui.screens.petowner.service

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.petOwner.SearchBar
import com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceContent.PetServiceCard
import com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceContent.ServiceTypeChip
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.AppConstant
import com.bussiness.slodoggiesapp.viewModel.petOwner.PetServicesViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.UUID

@Composable
fun PetServicesScreen(navController: NavHostController, viewModel: PetServicesViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val services = uiState.services

    uiState.error?.let { errorMsg ->
        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
        return
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        BackHandler {
            if (!navController.popBackStack(Routes.HOME_SCREEN, false)) {
                navController.navigate(Routes.HOME_SCREEN) {
                    launchSingleTop = true
                }
            }
        }

        HeadingTextWithIcon(
            textHeading = stringResource(R.string.services),
            onBackClick = {
                if (!navController.popBackStack(Routes.HOME_SCREEN, false)) {
                    navController.navigate(Routes.HOME_SCREEN) { launchSingleTop = true }
                }
            }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(modifier = Modifier.height(20.dp))

        SearchBar(
            searchText = uiState.searchText,
            onSearchTextChange = { viewModel.onSearchTextChange(it) },
            placeholder = stringResource(R.string.search),
            backgroundColor = Color(0xFFF4F4F4),
            cornerRadius = 12.dp,
            modifier = Modifier.padding(horizontal = 15.dp)
                .shadow(2.dp, RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.serviceCategory) { category ->
                ServiceTypeChip(
                    serviceType = category.categoryName ?: "",
                    isSelected = uiState.selectedServiceType == category.categoryName,
                    onClick = {
                        viewModel.onServiceTypeSelected(
                            if (uiState.selectedServiceType == category.categoryName) null else category.categoryName
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Top Providers",
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.outfit_semibold)),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        if (services.isEmpty()) {
            // Empty view
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(R.drawable.ic_pet_post_icon),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "OOps!,No services found",
                        style = MaterialTheme.typography.titleMedium,
                        color = PrimaryColor,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 18.sp
                    )
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                state = rememberLazyStaggeredGridState(),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = services,
                    key = { it.serviceId }
                ) { service ->
                    PetServiceCard(
                        service = service,
                        navController = navController,
                        onInquire = {
                            val receiverId = service.providerId
                            val receiverImage = URLEncoder.encode(service.image, StandardCharsets.UTF_8.toString())
                            val receiverName = URLEncoder.encode(service.providerName, StandardCharsets.UTF_8.toString())
                            val type = AppConstant.SINGLE

                            navController.navigate(
                                "${Routes.CHAT_SCREEN_FUNCTIONAL}/$receiverId/$receiverImage/$receiverName/$type"
                            )
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PetServicesScreenPreview() {
    val navController = rememberNavController()
    PetServicesScreen(navController = navController)
}