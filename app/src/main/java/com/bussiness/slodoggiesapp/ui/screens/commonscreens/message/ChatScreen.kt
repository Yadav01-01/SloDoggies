package com.bussiness.slodoggiesapp.ui.screens.commonscreens.message

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.ChatHeaderData
import com.bussiness.slodoggiesapp.ui.component.common.BottomMessageBar
import com.bussiness.slodoggiesapp.ui.component.common.ChatHeaderItem
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.ReportDialog
import com.bussiness.slodoggiesapp.ui.dialog.BottomToast
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.community.CommunityChatSection
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.communityVM.CommunityChatViewModel
import kotlinx.coroutines.delay

@Composable
fun ChatScreen(
    navController: NavHostController,
    viewModel: CommunityChatViewModel = hiltViewModel()
) {
    var showToast by remember { mutableStateOf(false) }
    var isBlocked by remember { mutableStateOf(false) }
    var deleteDialog by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showReportDialog by remember { mutableStateOf(false) }
    val sessionManager = SessionManager.getInstance(LocalContext.current)

    val messages by viewModel.messages.collectAsState()
    val currentMessage by viewModel.currentMessage.collectAsState()
    val listState = rememberLazyListState()
    var isNavigating by remember { mutableStateOf(false) }

    // Scroll to latest message when list changes
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header stays
        ChatHeaderItem(
            chatData = chatHeader,
            onBackClick = {
                if (!isNavigating) {
                    isNavigating = true
                    navController.popBackStack()
                }
                          },
            onDeleteClick = { deleteDialog = true },
            onReportClick = { showReportDialog = true },
            onBlockClick = {
                isBlocked = !isBlocked
                showToast = true
            },
            onFeedbackClick = { /* handle feedback */ }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        // Chat list & input area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .imePadding()
        ) {
            CommunityChatSection(
                messages = messages,
                listState = listState,
                modifier = Modifier.fillMaxSize().padding(bottom = 65.dp)
            )

            if (!isBlocked){
                BottomMessageBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    viewModel = viewModel
                )
            }
            if (isBlocked){
                Surface(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 10.dp)
                        .align(Alignment.BottomCenter)
                        .clickable {  isBlocked = !isBlocked }, // Toggle on click
                    shape = RoundedCornerShape(10.dp),
                    color = PrimaryColor
                ) {
                    Text(
                        text = "Unblock Jane",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

        }

        // Dialogs remain outside main chat area
        if (showReportDialog) {
            ReportDialog(
                onDismiss = { showReportDialog = false },
                title = stringResource(R.string.report),
                reasons = listOf(
                    "Bullying or unwanted contact",
                    "Violence, hate or exploitation",
                    "False Information",
                    "Scam, fraud or spam"
                ),
                selectedReason = selectedReason,
                message = message,
                onReasonSelected = { reason -> selectedReason = reason },
                onMessageChange = { message = it },
                onCancel = { showReportDialog = false },
                onSendReport = { showReportDialog = false }
            )
        }

//        if (showToast) {
//
//            BottomToast(
//                isBlocked = isBlocked,
//                onToggle = { isBlocked = !isBlocked },
//                onDismiss = { showToast = false }
//            )
//        }

        if (deleteDialog) {
            DeleteChatDialog(
                onDismiss = { deleteDialog = false },
                onClickRemove = { /* handle delete */ },
                iconResId = R.drawable.delete_mi,
                text = stringResource(R.string.Delete_conversation),
                description = stringResource(R.string.Delete_it)
            )
        }
    }
}


var chatHeader = com.bussiness.slodoggiesapp.data.model.businessProvider.ChatHeaderData(
    imageUrl = "https://example.com/user2.jpg",
    name = "Merry",
    status = "Active Now"
)

