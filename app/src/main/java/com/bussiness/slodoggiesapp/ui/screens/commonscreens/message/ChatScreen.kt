package com.bussiness.slodoggiesapp.ui.screens.commonscreens.message

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.model.businessProvider.ChatHeaderData
import com.bussiness.slodoggiesapp.ui.component.common.ChatHeaderItem
import com.bussiness.slodoggiesapp.ui.dialog.CustomToast
import com.bussiness.slodoggiesapp.ui.dialog.ReportDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import kotlinx.coroutines.delay

@Composable
fun ChatScreen(navController: NavHostController) {

    var showDialog by remember { mutableStateOf(false) }
    var isBlocked by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        ChatHeaderItem(
            chatData = chatHeader,
            onBackClick = { navController.popBackStack() },
            onDeleteClick = { /* delete logic */ },
            onReportClick = { showDialog = true },
            onBlockClick = {
                isBlocked = !isBlocked
                if (isBlocked) {
                    showToast = true
                }
                Toast.makeText(context, "Blocked", Toast.LENGTH_SHORT).show()
            }
        )


        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(PrimaryColor)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                // TODO: Add first message item or input field
            }

            item {

            }
        }
        if (showDialog) {
            ReportDialog(
                onDismiss = { showDialog = false },
                onSend = { reason, message ->
                    // Handle send logic here
                    Log.d("Report", "Reason: $reason, Message: $message")
                    showDialog = false
                }
            )
        }
        if (showToast) {
            val toastMessage = if (isBlocked) "Blocked" else "Unblocked"
            CustomToast("$toastMessage ${chatHeader.name}")

            LaunchedEffect(showToast) {
                delay(2000)
                showToast = false
            }
        }
    }
}


var chatHeader = ChatHeaderData(imageUrl = "https://example.com/user2.jpg", name = "Merry", status = "Active Now")