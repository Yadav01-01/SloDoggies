package com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DetailText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FilledCustomButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.OutlineCustomButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.PetOwnerDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingText
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun PersonDetailScreen(navController: NavHostController) {
    var selectedIndex by remember { mutableStateOf(0) }

    val images = listOf(
        R.drawable.sample_user,
        R.drawable.sample_user,
        R.drawable.sample_user,
        R.drawable.sample_user,
    )

    Column(modifier = Modifier.fillMaxSize()) {

        TopHeadingText(textHeading = "Jimmi", onBackClick = { navController.popBackStack() })

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(PrimaryColor)
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {

            // Profile Image Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                itemsIndexed(images) { index, imageRes ->
                    val isSelected = index == selectedIndex

                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Profile $index",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) PrimaryColor else Color(0xFFE5EFF2),
                                shape = CircleShape
                            )
                            .clickable { selectedIndex = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Dog Profile Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(20.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(R.drawable.dog_ic),
                    contentDescription = "dog image",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color(0xFFE5EFF2), CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Jimmi",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(6.dp))

                    Row {
                        DetailText("Golden Retriever", Color(0xFFE5EFF2), PrimaryColor)
                        Spacer(Modifier.width(6.dp))
                        DetailText("6 Years Old", Color(0xFFFFF1E8), Color(0xFFFF771C))
                    }

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed.",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black,
                        maxLines = 6
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row (modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceEvenly) {

                ProfileDetail(label = "20", value = "Posts")

                VerticalDivider(Modifier.width(2.dp).height(40.dp).background(PrimaryColor))

                ProfileDetail(label = "27k", value = "Followers")

                VerticalDivider(Modifier.width(2.dp).height(40.dp).background(PrimaryColor))

                ProfileDetail(label = "219", value = "Following")

            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilledCustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(35.dp),
                    buttonText = "Follow",
                    onClickFilled = { /* Handle Follow */ },
                    buttonTextSize = 14
                )

                OutlineCustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(35.dp),
                    text = "Message",
                    onClick = { /* Handle Message */ }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                PetOwnerDetail(
                    name = "Justin Bator",
                    label = "Pet Dad",
                    imageRes = R.drawable.sample_user,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed."
                )

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(PrimaryColor)
                )

                Spacer(modifier = Modifier.height(8.dp))

                PetOwnerDetail(
                    name = "Makenna Bator",
                    label = "Pet Mom",
                    imageRes = R.drawable.sample_user,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed."
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonDetailScreenPreview() {
    val navController = rememberNavController()
    PersonDetailScreen(navController = navController)
}
