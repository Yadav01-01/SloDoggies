package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.common.DisplaySupportData
import com.bussiness.slodoggiesapp.ui.component.common.SupportContactFAQTextCard
import com.bussiness.slodoggiesapp.ui.component.common.SupportContactTextCard
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.helpsupport.HelpSupportViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HelpAndSupportScreen(navController: NavHostController) {

    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    var isNavigating by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }

    val viewModel: HelpSupportViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    // ------------------- Load Data -------------------
    fun loadData() {
        viewModel.helpSupportRequest(
            onError = { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                isRefreshing = false
            },
            onSuccess = {
                isRefreshing = false
            }
        )
    }

    // Pull Refresh State
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            loadData()
        }
    )

    // First Load
    LaunchedEffect(Unit) { loadData() }

    // Back Handler
    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    // ------------------- UI -------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ----- HEADER (Not Refreshable) -----
        HeadingTextWithIcon(
            textHeading = "Help & Support",
            onBackClick = {
                if (!isNavigating) {
                    isNavigating = true
                    navController.popBackStack()
                }
            }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(modifier = Modifier.height(10.dp))

        // ----- REFRESHABLE AREA STARTS HERE -----
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // ---------------- Box Card ----------------
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = PrimaryColor,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(24.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                        Text(
                            text = "Need a paw? We're here for you!",
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            color = Color.Black
                        )

                        // HTML Content
                        val contentText = uiState.data?.content ?: ""

                        val htmlWithCss = """
                    <html>
                    <head>
                        <style>
                            img { max-width: 100%; height: auto; }
                            body { margin: 0; padding: 0; font-size: 16px; color: #252E32; }
                        </style>
                    </head>
                    <body>
                        $contentText
                    </body>
                    </html>
                """.trimIndent()

                        AndroidView(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            factory = { context ->
                                WebView(context).apply {
                                    settings.javaScriptEnabled = true
                                    settings.domStorageEnabled = true
                                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                                }
                            },
                            update = { webView ->
                                webView.loadDataWithBaseURL(
                                    null,
                                    htmlWithCss,
                                    "text/html",
                                    "utf-8",
                                    null
                                )
                            }
                        )

//                        AndroidView(
//                            factory = { context ->
//                                TextView(context).apply {
//                                    setTextColor(android.graphics.Color.parseColor("#252E32"))
//                                    textSize = 16f
//                                    movementMethod = android.text.method.LinkMovementMethod.getInstance()
//                                }
//                            },
//                            update = { view ->
//                                view.text = HtmlCompat.fromHtml(
//                                    contentText,
//                                    HtmlCompat.FROM_HTML_MODE_LEGACY
//                                )
//                            }
//                        )

                        // Professional User Editable
                        if (sessionManager.getUserType() == UserType.Professional) {

                            InputField(
                                input = uiState.data?.phone ?: "+1 (555) 123 456",
                                onValueChange = {},
                                placeholder = "+1 (555) 123 456",
                                height = 46.dp,
                                fontSize = 12
                            )

                            InputField(
                                input = uiState.data?.email ?: "help@slodoggies.com",
                                onValueChange = {},
                                placeholder = "help@slodoggies.com",
                                height = 46.dp,
                                fontSize = 12
                            )
                        } else {
                            // Customer info display only
                            DisplaySupportData(
                                icon = R.drawable.filled_call_ic,
                                text = uiState.data?.phone ?: "(555) 123 456"
                            )
                            DisplaySupportData(
                                icon = R.drawable.filled_mail,
                                text = uiState.data?.email ?: "help@slodoggies.com"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                SupportContactFAQTextCard(
                    heading = "Need Quick Answers?",
                    onFaqClick = { navController.navigate(Routes.FAQ_SCREEN) }
                )

                Spacer(modifier = Modifier.height(15.dp))

                SupportContactTextCard(
                    heading = "Feedback & Suggestions",
                    subHeading = "We’d love to hear from you! Help us make Slo doggies better by sharing your feedback and ideas."
                )
            }

            // Pull-to-refresh indicator UNDER the header
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Composable
fun HelpAndSupportPreview() {
    HelpAndSupportScreen(navController = NavHostController(LocalContext.current))
}
