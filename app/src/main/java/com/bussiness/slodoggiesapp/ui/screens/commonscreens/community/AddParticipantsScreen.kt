package com.bussiness.slodoggiesapp.ui.screens.commonscreens.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.AddParticipant
import com.bussiness.slodoggiesapp.model.common.Participant
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchResultItem
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
        // Top Heading
        HeadingTextWithIcon(
            textHeading = "Add Participants",
            onBackClick = { navController.popBackStack() },

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

            // Selected participants row
            if (selected.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(selected) { participant ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box {
                                AsyncImage(
                                    model = participant.imageUrl,
                                    contentDescription = participant.name,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                )
                                IconButton(
                                    onClick = { viewModel.removeParticipant(participant) },
                                    modifier = Modifier
                                        .size(151.dp)
                                        .background(Color.Red, CircleShape)
                                        .align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Text(
                                text = participant.name,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
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
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
