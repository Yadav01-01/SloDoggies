package com.bussiness.slodoggiesapp.ui.screens.petowner.chatScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.petOwner.Participant
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.UpdateNameCommunityDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.EventCommunityScreenHeader
import com.bussiness.slodoggiesapp.ui.component.petOwner.IconHeadingText


@Composable
fun EventCommunityScreen(
    navController: NavController = rememberNavController(),
    onBackPressed: () -> Unit = {},
    onSharePressed: () -> Unit = {},
    onEditPressed: () -> Unit = {},
    onParticipantMenuPressed: (String) -> Unit = {}
) {
    val showDialog = remember { mutableStateOf(false) } // Add this line
    val participants = listOf(
        Participant("Lydia Vaccaro", R.drawable.dummy_baby_pic),
        Participant("Anika Torff", R.drawable.dummy_baby_pic),
        Participant("Zain Dorwart", R.drawable.dummy_baby_pic),
        Participant("Ryan Dias", R.drawable.dummy_baby_pic),
        Participant("Marcus Culhane", R.drawable.dummy_baby_pic),
        Participant("Cristofer Torff", R.drawable.dummy_baby_pic),
        Participant("Kierra Westervelt", R.drawable.dummy_baby_pic),
        Participant("Adison Dias", R.drawable.dummy_baby_pic)
    )

    Scaffold(
        topBar = {

            EventCommunityScreenHeader(
                textHeading = "",
                onBackClick = {
                    navController.popBackStack()
                },
                onIconClick = {

                },
                rightSideIcon = R.drawable.ic_share_icon,

                displayRightIcon = true
            )

        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Community Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Community Avatar with overlapping circles

                    Box(
                        modifier = Modifier
                            .border(
                                width = 8.dp,
                                shape = CircleShape,
                                color = Color(0xFFE5EFF2)
                            )
                            .size(147.dp)
                            .clip(CircleShape)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.ic_community_icons),
                            contentDescription = "Community Icon", modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp)
                                .clip(CircleShape)
                        )
                    }


                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Event Community 1",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_edit_icon_again),
                        contentDescription = "Edit",
                        modifier = Modifier.size(20.dp).clickable{
                            showDialog.value = true

                        })
                }
            }

            // Participants Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Participants",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_user_add_icon),
                        contentDescription = "Add participants",
                        modifier = Modifier
                            .width(20.dp)
                            .height(21.81.dp)
                    )
                }

                Divider(
                    color = Color(0xFF258694),
                    thickness = 1.5.dp
                )
            }

            Spacer(Modifier.height(10.dp))

            // Participants List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items(participants) { participant ->
                    ParticipantItem(
                        participant = participant,
                        onMenuPressed = { onParticipantMenuPressed(participant.name) }
                    )
                }
            }
        }
    }

    if (showDialog.value) {
        UpdateNameCommunityDialog(
            onDismiss = { showDialog.value = false },
            onRename = { newName ->
                // Handle rename logic here
                showDialog.value = false
            },
            onCancel = { showDialog.value = false }
        )
    }
}

@Composable
fun ParticipantItem(
    participant: Participant,
    onMenuPressed: () -> Unit
) {
    Column ( modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp)){


    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar

        Image(
            painter = painterResource(id = participant.avatarRes),
            contentDescription = "Community Icon", modifier = Modifier
                .size(38.dp)
                .clip(CircleShape), contentScale = ContentScale.Crop
        )


        Spacer(modifier = Modifier.width(12.dp))

        // Name
        Text(
            text = participant.name,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        // Menu button
        IconButton(
            onClick = onMenuPressed,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.Black
            )
        }
    }
        Spacer(Modifier.height(10.dp))
        Divider(thickness = 1.dp, color = Color(0xFFE1EDEF))
    }
}

@Preview(showBackground = true)
@Composable
fun EventCommunityScreenPreview() {
    MaterialTheme {
        EventCommunityScreen()
    }
}