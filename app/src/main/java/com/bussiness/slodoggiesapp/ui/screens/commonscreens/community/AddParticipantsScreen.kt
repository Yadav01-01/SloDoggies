package com.bussiness.slodoggiesapp.ui.screens.commonscreens.community

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.AddParticipant
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ParticipantTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.AddParticipantViewModel

@Composable
fun AddParticipantsScreen(
    navController: NavHostController,
    viewModel: AddParticipantViewModel = hiltViewModel(),
    onDone: (List<AddParticipant>) -> Unit = {}
) {
    val query by viewModel.query.collectAsState()
    val selected by viewModel.selectedParticipants.collectAsState()
    val suggestions by viewModel.filteredSuggestions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        BackHandler {
            navController.navigate(Routes.COMMUNITY_PROFILE_SCREEN) {
                popUpTo(Routes.ADD_PARTICIPANTS_SCREEN) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
        // Top Heading
        ParticipantTextWithIcon(
            textHeading = "Add Participants",
            onBackClick = {
                navController.navigate(Routes.COMMUNITY_PROFILE_SCREEN) {
                    popUpTo(Routes.ADD_PARTICIPANTS_SCREEN) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            onClick = { navController.navigate(Routes.COMMUNITY_PROFILE_SCREEN){
                popUpTo(Routes.ADD_PARTICIPANTS_SCREEN) {
                    inclusive = true
                }
                launchSingleTop = true
            } },
            selected = selected.isNotEmpty()
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(PrimaryColor)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Spacer(Modifier.height(10.dp))

            // Search Bar
            com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar(
                query = query,
                onQueryChange = { viewModel.updateQuery(it) },
                placeholder = "Enter Name"
            )

            Spacer(Modifier.height(10.dp))

            // Selected participants row
            if (selected.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(selected) { participant ->

                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.size(60.dp), contentAlignment = Alignment.Center) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                ) {
                                    AsyncImage(
                                        model = participant.imageUrl,
                                        contentDescription = participant.name,
                                        placeholder = painterResource(R.drawable.ic_person_icon),
                                        error = painterResource(R.drawable.ic_person_icon),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .matchParentSize()
                                            .clip(CircleShape)
                                    )
                                }

                                Image(
                                    painter = painterResource(id = R.drawable.ic_cross_icon),
                                    contentDescription = "Add Photo",
                                    modifier = Modifier
                                        .size(18.dp)
                                        .align(Alignment.TopEnd)
                                        .clickable { viewModel.removeParticipant(participant) }
                                )
                            }
                        }
                    }
                }
            }

            // Suggested List
            Text(
                text = "Suggested",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium))
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(PrimaryColor)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(suggestions) { participant ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectParticipant(participant) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = participant.imageUrl,
                            contentDescription = participant.name,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = participant.name,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
