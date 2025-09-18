package com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CustomDropdownBox
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonWhiteButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey


@Composable
fun ReportDialog(
    title: String,
    reasons: List<String>,
    selectedReason: String,
    message: String,
    onReasonSelected: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onSendReport: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(top = 64.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                CloseButton(onDismiss)

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.785f),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        DialogHeader(title)
                        Divider(color = PrimaryColor, thickness = 1.dp)
                        DialogContent(
                            reasons = reasons,
                            selectedReason = selectedReason,
                            onReasonSelected = onReasonSelected,
                            message = message,
                            onMessageChange = onMessageChange,
                            onCancel = onCancel,
                            onSendReport = onSendReport
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CloseButton(onDismiss: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.ic_cross_iconx),
            contentDescription = "Close",
            modifier = Modifier
                .clickable(onClick = onDismiss)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .padding(8.dp)
        )
    }
}

@Composable
private fun DialogHeader(title: String = "Report Comment") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

@Composable
private fun DialogContent(
    reasons: List<String>,
    selectedReason: String,
    onReasonSelected: (String) -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
    onCancel: () -> Unit,
    onSendReport: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.Why_are_you_reporting_this_comment),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Makes list scrollable without pushing buttons
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reasons) { reason ->
                ReportReasonOption(
                    text = reason,
                    isSelected = selectedReason == reason,
                    onClick = { onReasonSelected(reason) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.message_optional),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = message,
            onValueChange ={ onMessageChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 90.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.Write_something_here),
                    color = TextGrey,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 15.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = TextGrey
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = false,
            maxLines = 4
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CommonWhiteButton(
                text = stringResource(R.string.cancel),
                onClick = onCancel,
                modifier = Modifier.weight(1f),
            )
            CommonBlueButton(
                text = stringResource(R.string.send_report),
                fontSize = 16.sp,
                onClick = onSendReport,
                modifier = Modifier.weight(1f),
            )
        }
    }
}



@Composable
fun ReportReasonOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = if (isSelected) Color.White else Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = if (isSelected) PrimaryColor else Color.Transparent,
            )
            .padding(horizontal = 0.dp, vertical = 5.dp)
    )
}

@Composable
fun ReportBusinessChat(
    onDismiss: () -> Unit,
    title: String,
    reasons: List<String>,
    selectedReason: String,
    message: String,
    onReasonSelected: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onCancel: () -> Unit,
    onSendReport: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(top = 64.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                CloseButton(onDismiss)

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() //  take only needed height
                        .padding(bottom = 16.dp)
                        .then(
                            Modifier.heightIn(
                                max = (LocalConfiguration.current.screenHeightDp.dp * 0.85f) // ✅ cap at 85% of screen height
                            )
                        ),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        DialogHeader(title)
                        Divider(color = PrimaryColor, thickness = 1.dp)

                        // If content grows too tall, make it scrollable
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 16.dp)
                        ) {
                            BusinessDialogContent(
                                reasons = reasons,
                                selectedReason = selectedReason,
                                onReasonSelected = onReasonSelected,
                                message = message,
                                onMessageChange = onMessageChange,
                                onCancel = onCancel,
                                onSendReport = onSendReport
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BusinessDialogContent(
    reasons: List<String>,
    selectedReason: String,
    onReasonSelected: (String) -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
    onCancel: () -> Unit,
    onSendReport: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title: Reason
        Text(
            text = stringResource(R.string.reason),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        CustomDropdownBox(
            label = selectedReason.ifEmpty { stringResource(R.string.select) },
            items = reasons,
            selectedItem = selectedReason,
            onItemSelected = onReasonSelected
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Message Label
        Text(
            text = stringResource(R.string.message_optional),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Message Field
        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 90.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.Write_something_here),
                    color = TextGrey,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 15.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = TextGrey
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = false,
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CommonWhiteButton(
                text = stringResource(R.string.cancel),
                onClick = onCancel,
                modifier = Modifier.weight(1f),
            )
            CommonBlueButton(
                text = stringResource(R.string.send_report),
                fontSize = 16.sp,
                onClick = onSendReport,
                modifier = Modifier.weight(1f),
            )
        }
    }
}


@Preview
@Composable
fun ReportCommentDialogPreview() {
    var showReportDialog by remember { mutableStateOf(false) }
    ReportDialog(
        onDismiss = { showReportDialog = false },
        onCancel = { showReportDialog = false },
        onSendReport = { showReportDialog = false },
        reasons = listOf("Spam", "Harassment", "Hateful Content"),
        selectedReason = "",
        message = "",
        onReasonSelected = { /* Handle reason selection */ },
        onMessageChange = { /* Handle message change */ },
        title = "Report Comment"
    )
}
