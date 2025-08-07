package com.bussiness.slodoggiesapp.ui.screens.commonscreens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.Participants
import com.bussiness.slodoggiesapp.ui.component.common.CommunityParticipantsText
import com.bussiness.slodoggiesapp.ui.component.common.CommunityProfileSection
import com.bussiness.slodoggiesapp.ui.component.common.ParticipantsItem
import com.bussiness.slodoggiesapp.ui.dialog.BottomDialogWrapper
import com.bussiness.slodoggiesapp.ui.dialog.EditCommunityName
import com.bussiness.slodoggiesapp.ui.dialog.RemoveParticipantDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun CommunityProfileScreen(navController: NavHostController) {

    var menuDialog by remember { mutableStateOf(false) }
    var showEditNameDialog by remember { mutableStateOf(false) }
    var communityName by remember { mutableStateOf("") }
    var removeDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = PrimaryColor,
            modifier = Modifier
                .size(24.dp)
                .clickable { navController.popBackStack() }
        )

        CommunityProfileSection(
            communityName = "Event Community 1",
            onEditClick = { showEditNameDialog = true },
            image = "https://picsum.photos/200/300"
        )

        Spacer(Modifier.height(18.dp))

        CommunityParticipantsText(onAddIconClick = { /* Add participant action */ })

        LazyColumn(
            contentPadding = PaddingValues(vertical = 0.dp)
        ) {
            items(participantsList) { participant ->
                ParticipantsItem(participants = participant, onMenuClick = { menuDialog = true })
            }
        }


        if (menuDialog) {
            BottomDialogWrapper(
                onDismissRequest = { menuDialog = false },
                onRemoveUserClick = { removeDialog = true },
                onViewProfileClick = {
                    // Your logic
                }
            )
        }

        if (showEditNameDialog) {
            EditCommunityName(
                communityName = communityName,
                onDismiss = { showEditNameDialog = false },
                onClickSubmit = {
                    // Do something with communityName
                    showEditNameDialog = false
                },
                onNameChange = { communityName = it }
            )
        }

        if (removeDialog) {
            RemoveParticipantDialog(
                onDismiss = { removeDialog = false },
                onClickRemove = {
                    // Your logic
                },
                iconResId = R.drawable.remove_ic_user,
                text = "Are you sure?",
                description = "You want to remove Lydia Vaccaro?"
            )
        }


    }
}


val participantsList = listOf(
    Participants(R.drawable.lady_dm, "Lydia Vaccaro"),
    Participants(R.drawable.lady_dm, "Anika Torff"),
    Participants(R.drawable.lady_dm, "Zain Dorwart"),
    Participants(R.drawable.lady_ic, "Lydia Vaccaro"),
    Participants(R.drawable.lady_dm, "Kierra Westervelt"),
    Participants(R.drawable.lady_dm, "Ryan Dias"),
    Participants(R.drawable.lady_ic, "Lydia Vaccaro"),
    Participants(R.drawable.lady_dm, "Ryan Dias"),
    Participants(R.drawable.lady_dm, "Kierra Westervelt"),
)


@Preview
@Composable
fun CommunityProfileScreenPreview() {
    CommunityProfileScreen(navController = NavHostController(LocalContext.current))
}