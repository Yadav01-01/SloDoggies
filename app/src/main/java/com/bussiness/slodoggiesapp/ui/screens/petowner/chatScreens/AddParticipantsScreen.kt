package com.bussiness.slodoggiesapp.ui.screens.petowner.chatScreens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.petOwner.AddParticipant
import com.bussiness.slodoggiesapp.ui.component.petOwner.IconHeadingText


@Composable
fun AddParticipantsScreen(navController: NavController = rememberNavController()) {
    var searchText by remember { mutableStateOf("") }
    var selectedParticipants by remember { mutableStateOf(listOf<AddParticipant>()) }

    val suggestedParticipants = listOf(
        AddParticipant("1", "Lydia Vasquez", backgroundColor = Color(0xFF8B4513)),
        AddParticipant("2", "Chandler Spencer", backgroundColor = Color(0xFF4A90E2)),
        AddParticipant("3", "Grayham Carter", backgroundColor = Color(0xFF2E7D32)),
        AddParticipant("4", "Madelyn Franco", backgroundColor = Color(0xFFE91E63)),
        AddParticipant("5", "Marilyn Press", backgroundColor = Color(0xFF9C27B0))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar
        IconHeadingText(
            textHeading = "Add Participants",
            onBackClick = {

            },
            onIconClick = {

            },
            rightSideIcon = R.drawable.ic_check_icon_blue,
            iconColor = Color(0xFF258694),
            dividerColor = Color(0xFF656565),
            displayRightIcon = true
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Enter Name") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selected Participants Row
            if (selectedParticipants.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(selectedParticipants) { participant ->
                        SelectedParticipantChip(
                            participant = participant,
                            onRemove = {
                                selectedParticipants = selectedParticipants.filter { it.id != participant.id }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Suggested Section
            Text(
                text = "Suggested",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Suggested Participants List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(suggestedParticipants.filter { participant ->
                    !selectedParticipants.any { it.id == participant.id }
                }) { participant ->
                    ParticipantItem(
                        participant = participant,
                        onClick = {
                            selectedParticipants = selectedParticipants + participant
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ParticipantItem(
    participant: AddParticipant,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image/Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(participant.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = participant.name.split(" ").map { it.first() }.take(2).joinToString(""),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Name
        Text(
            text = participant.name,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SelectedParticipantChip(
    participant: AddParticipant,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                Color.LightGray.copy(alpha = 0.3f),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Small profile avatar
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(participant.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = participant.name.split(" ").map { it.first() }.take(2).joinToString(""),
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = participant.name,
            fontSize = 12.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            Icons.Default.Close,
            contentDescription = "Remove",
            modifier = Modifier
                .size(16.dp)
                .clickable { onRemove() },
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddParticipantsScreenPreview() {
    MaterialTheme {
        AddParticipantsScreen()
    }
}