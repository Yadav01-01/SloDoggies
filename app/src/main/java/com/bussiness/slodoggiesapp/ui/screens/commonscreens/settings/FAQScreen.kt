package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.faq.FaqItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.FAQViewModel

@Composable
fun FAQScreen(navController: NavHostController,viewModel: FAQViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect (uiState.error){
        if (uiState.error != null) {
           Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
        }
    }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.White) ){

        HeadingTextWithIcon(textHeading = "FAQs", onBackClick = {  if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(Modifier.height(10.dp))

        Column  (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ){
            if (uiState.data.isEmpty()) {
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
                            tint = Color(0XFF258694),
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "OOps! Something Went Wrong",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0XFF258694),
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontSize = 18.sp
                        )
                    }
                }
            }else{
                uiState.data.forEachIndexed { index, faq ->
                    FAQItemCard(
                        faq = faq,
                        isExpanded = viewModel.isExpanded(index),
                        onClick = { viewModel.toggleExpanded(index) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun FAQItemCard(faq: FaqItem, isExpanded: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        // Header Section
        Card(
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = if (isExpanded) 0.dp else 10.dp, bottomEnd = if (isExpanded) 0.dp else 10.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = faq.question,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = if (isExpanded) R.drawable.up_ic else R.drawable.down_ic),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }

        // Expanded Content Section
        if (isExpanded) {
            Card(
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE5EFF2)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = faq.answer,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun FAQScreenPreview() {
    FAQScreen(navController = NavHostController(LocalContext.current))
}