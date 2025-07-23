package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.PetSitterCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TypeButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun ServiceScreen(navController: NavHostController) {

    var selected by remember { mutableStateOf("Services") }

    Column ( modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon(textHeading = "Services", onBackClick = { navController.popBackStack() })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Column ( modifier = Modifier.fillMaxSize().background(Color.White).verticalScroll(
            rememberScrollState()).padding(16.dp)){
            PetSitterCard(
                name = "Pet Sitter Name",
                rating = 4.5f,
                ratingCount = 100,
                providerName = "Provider Name",
                about = "About the sitter",
                phone = "123-456-7890",
                website = "www.example.com",
                address = "123 Main St, City, Country",
                onEditClick = {}
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE5EFF2))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("Services", "Rating & Reviews").forEach { label ->
                    TypeButton(
                        modifier = Modifier
                            .weight(1f),
                        text = label,
                        isSelected = selected == label,
                        onClick = { selected = label }
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            FormHeadingText("Available Services-")

            Spacer(Modifier.height(10.dp))

        }

    }
}