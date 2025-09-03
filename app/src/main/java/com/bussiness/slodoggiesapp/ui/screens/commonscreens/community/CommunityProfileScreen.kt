package com.bussiness.slodoggiesapp.ui.screens.commonscreens.community

import androidx.compose.foundation.background
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
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.common.CommunityParticipantsText
import com.bussiness.slodoggiesapp.ui.component.common.CommunityProfileSection
import com.bussiness.slodoggiesapp.ui.component.common.ParticipantsItem
import com.bussiness.slodoggiesapp.ui.dialog.BottomDialogWrapper
import com.bussiness.slodoggiesapp.ui.dialog.EditCommunityName
import com.bussiness.slodoggiesapp.ui.dialog.RemoveParticipantDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.communityVM.CommunityProfileViewModel

@Composable
fun CommunityProfileScreen(
    navController: NavHostController,
    viewModel: CommunityProfileViewModel = hiltViewModel()
) {
    val communityName by viewModel.communityName.collectAsState()
    val participants by viewModel.participants.collectAsState()
    val menuDialog by viewModel.menuDialog.collectAsState()
    val showEditNameDialog by viewModel.showEditNameDialog.collectAsState()
    val removeDialog by viewModel.removeDialog.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
    ) {
        // Back Button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = PrimaryColor,
                modifier = Modifier.size(24.dp)
            )
        }

        // Profile Section
        CommunityProfileSection(
            communityName = communityName,
            onEditClick = { viewModel.toggleEditNameDialog(true) },
            image = "https://picsum.photos/200/300"
        )

        Spacer(Modifier.height(18.dp))

        // Participants Header
        CommunityParticipantsText(
            onAddIconClick = { navController.navigate(Routes.ADD_PARTICIPANTS_SCREEN) }
        )

        // Participants List
        LazyColumn(contentPadding = PaddingValues(vertical = 0.dp)) {
            items(participants) { participant ->
                ParticipantsItem(
                    participants = participant,
                    onMenuClick = { viewModel.toggleMenuDialog(true) }
                )
            }
        }

        // Menu Dialog
        if (menuDialog) {
            BottomDialogWrapper(
                onDismissRequest = { viewModel.toggleMenuDialog(false) },
                onRemoveUserClick = { viewModel.toggleRemoveDialog(true) },
                onViewProfileClick = { navController.navigate(Routes.PERSON_DETAIL_SCREEN) }
            )
        }

        // Edit Name Dialog
        if (showEditNameDialog) {
            EditCommunityName(
                communityName = communityName,
                onDismiss = { viewModel.toggleEditNameDialog(false) },
                onClickSubmit = { viewModel.toggleEditNameDialog(false) },
                onNameChange = { viewModel.updateCommunityName(it) }
            )
        }

        // Remove Participant Dialog
        if (removeDialog) {
            RemoveParticipantDialog(
                onDismiss = { viewModel.toggleRemoveDialog(false) },
                onClickRemove = { participants.toMutableList().removeAt(0) },
                iconResId = R.drawable.remove_ic_user,
                text = stringResource(R.string.are_you_sure),
                description = "You want to remove Lydia Vaccaro?"
            )
        }
    }
}


@Preview
@Composable
fun CommunityProfileScreenPreview() {
    CommunityProfileScreen(navController = NavHostController(LocalContext.current))
}