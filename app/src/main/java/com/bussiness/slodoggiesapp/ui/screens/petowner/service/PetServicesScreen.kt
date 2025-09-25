package com.bussiness.slodoggiesapp.ui.screens.petowner.service

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.bussiness.slodoggiesapp.viewModel.petOwner.PetServicesViewModel

@Composable
fun PetServicesScreen(navController: NavHostController, viewModel: PetServicesViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()

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
            val serviceTypes = listOf("Walking", "Grooming", "Sitting / Boarding", "Veterinary")
            items(serviceTypes) { serviceType ->
                ServiceTypeChip(
                    serviceType = serviceType,
                    isSelected = uiState.selectedServiceType == serviceType,
                    onClick = {
                        viewModel.onServiceTypeSelected(
                            if (uiState.selectedServiceType == serviceType) null else serviceType
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

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.filteredServices) { service ->
                PetServiceCard(service = service, navController = navController, onInquire = { navController.navigate(Routes.CHAT_SCREEN)})
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