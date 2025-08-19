package com.bussiness.slodoggiesapp.ui.component.common

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.Community
import com.bussiness.slodoggiesapp.model.common.AddParticipant
import com.bussiness.slodoggiesapp.model.common.Participants
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun CommunityHeader(
    community: Community,
    onBackClick: () -> Unit ,
    onViewProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryColor
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            AsyncImage(
                model = community.imageUrl,
                contentDescription = "Community Icon",
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0F7FA)),
                placeholder = painterResource(id = R.drawable.grp_ic_error),
                error = painterResource(id = R.drawable.grp_ic_error)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = community.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.group_ic),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${community.membersCount} members",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }

        Box {
            var menuExpanded by remember { mutableStateOf(false) }

            IconButton(onClick = { menuExpanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options",
                    tint = Color.Black
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                offset = DpOffset(x = (-70).dp, y = 0.dp),
                modifier = Modifier
                    .background(Color.White)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "View Profile",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular))
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onViewProfileClick()
                    }
                )
            }
        }

    }
}


@Composable
fun CommunityProfileSection(
    communityName: String,
    onEditClick: () -> Unit,
    image : String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile image
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .border(4.dp, Color(0xFFE5EFF2), CircleShape)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.grp_ic_error),
            error = painterResource(id = R.drawable.grp_ic_error)
        )

        Spacer(Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = communityName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 18.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                painter = painterResource(R.drawable.ic_edit_icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { onEditClick() }
            )
        }
    }
}

@Composable
fun CommunityParticipantsText(onAddIconClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,

    ) {
        Text(
            text = "Participants",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontSize = 16.sp,
                color = Color.Black
            )
        )

        Icon(
            painter = painterResource(R.drawable.add_ic),
            contentDescription = "Add Participant",
            tint = Color.Unspecified,
            modifier = Modifier
                .wrapContentSize()
                .clickable { onAddIconClick() }
        )
    }
    Spacer(Modifier.height(10.dp))

    HorizontalDivider(thickness = 1.dp, color = PrimaryColor)
}


@Composable
fun ParticipantsItem(
    participants: Participants,
    onMenuClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 12.dp), // Add vertical padding
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = participants.image,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0F7FA)),
                    placeholder = painterResource(id = R.drawable.grp_ic_error),
                    error = painterResource(id = R.drawable.grp_ic_error)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = participants.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu"
                )
            }
        }

        HorizontalDivider(thickness = 1.dp, color = Color(0xFFCBDFE3))
    }
}


@Composable
fun CommunityHeadingText(
    textHeading: String,
    selectedParticipants: List<AddParticipant>,
    onBackClick: () -> Unit,
    onDone: (List<AddParticipant>) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.back_ic),
            contentDescription = "Back",
            tint = PrimaryColor,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = textHeading,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black,
            modifier = Modifier.weight(1f) // Pushes trailing icon to the right
        )

        IconButton(onClick = { onDone(selectedParticipants) }) {
            Icon(
                painter = painterResource(id = R.drawable.tick_ic),
                contentDescription = "Done",
                tint = PrimaryColor
            )
        }
    }
}



