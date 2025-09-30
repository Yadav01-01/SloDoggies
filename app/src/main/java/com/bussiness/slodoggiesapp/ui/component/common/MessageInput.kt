package com.bussiness.slodoggiesapp.ui.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.ChatHeaderData
import com.bussiness.slodoggiesapp.model.common.Message
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.util.SessionManager

@Composable
fun MessageItem(message: Message, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp).clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = message.profileImageUrl,
            contentDescription = "Profile",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            placeholder = painterResource(id = R.drawable.grp_ic_error),
            error = painterResource(id = R.drawable.grp_ic_error)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.username,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 1
                )

                Text(
                    text = message.time,
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 10.sp,
                    color = TextGrey,
                    maxLines = 1
                )
            }

            Text(
                text = message.description,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 14.sp,
                color = TextGrey,
                maxLines = 1
            )
        }
    }
    HorizontalDivider(thickness = 1.dp, color = Color(0xFFF4F4F4))
}


@Composable
fun ChatHeaderItem(
    chatData: ChatHeaderData,
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit,
    onReportClick: () -> Unit,
    onBlockClick: () -> Unit,
    onFeedbackClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left Section: Back Icon, Image, Name, Status
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryColor
                )
            }

            Spacer(modifier = Modifier.width(2.dp))

            AsyncImage(
                model = chatData.imageUrl,
                contentDescription = "User Image",
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0F7FA)),
                placeholder = painterResource(id = R.drawable.dmy_ic),
                error = painterResource(id = R.drawable.dmy_ic)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = chatData.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.active_dot),
                        contentDescription = null,
                        modifier = Modifier.size(10.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = chatData.status,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 10.sp,
                            color = Color.Black
                        )
                    )
                }
            }
        }

        // Right Section: More Menu Icon + Dropdown
        Box {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options"
                )
            }

            OnMenuClick(
                menuExpanded = menuExpanded,
                onDismiss = { menuExpanded = false },
                onDeleteClick = {
                    menuExpanded = false
                    onDeleteClick()
                },
                onReportClick = {
                    menuExpanded = false
                    onReportClick()
                },
                onBlockClick = {
                    menuExpanded = false
                    onBlockClick()
                },
                onFeedbackClick = {
                    menuExpanded = false
                    onFeedbackClick()
                }
            )
        }
    }
}


@Composable
fun OnMenuClick(
    menuExpanded: Boolean,
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
    onReportClick: () -> Unit,
    onBlockClick: () -> Unit,
    onFeedbackClick: () -> Unit
) {
    val context = LocalContext.current
    val sessionManager = SessionManager.getInstance(context)

    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = onDismiss,
        modifier = Modifier.width(180.dp),
        shape = RoundedCornerShape(10.dp), //  this controls the popup shape
        containerColor = Color.White
    ) {
        MenuItem(
            iconRes = R.drawable.delete_mi,
            label = "Delete",
            onClick = {
                onDismiss()
                onDeleteClick()
            }
        )

        MenuItem(
            iconRes = R.drawable.ic_report_icon,
            label = "Report User",
            onClick = {
                onDismiss()
                onReportClick()
            }
        )

        MenuItem(
            iconRes = R.drawable.block_mi,
            label = "Block User",
            onClick = {
                onDismiss()
                onBlockClick()
            }
        )

        MenuItem(
            iconRes = R.drawable.req_fb,
            label = if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER) "Req. Feedback"
            else "Give Feedback",
            onClick = {
                onDismiss()
                onFeedbackClick()
            }
        )
    }

}

@Composable
fun MenuItem(
    @DrawableRes iconRes: Int,
    label: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = label,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                )
            }
        }
    )
}


