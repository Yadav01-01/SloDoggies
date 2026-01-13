package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.aboutus.AboutUsViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AboutUsScreen(navController: NavHostController) {

    var isNavigating by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    val viewModel: AboutUsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    fun loadData() {
        viewModel.aboutUsRequest(
            onError = { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                isRefreshing = false
            },
            onSuccess = {
                isRefreshing = false
            }
        )
    }
    LaunchedEffect(Unit) { loadData() }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            loadData()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ---------------------- TOP BAR -------------------------
        HeadingTextWithIcon(
            textHeading = "About Us",
            onBackClick = {
                if (!isNavigating) {
                    isNavigating = true
                    navController.popBackStack()
                }
            }
        )
        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)
        Spacer(Modifier.height(10.dp))
        // ---------------------- PULL TO REFRESH STARTS HERE -------------------------
        Box(modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)) {
            val termsText = uiState.data?.content
            if (termsText.isNullOrBlank()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "No Data Found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
//                    AndroidView(
//                        factory = { context ->
//                            TextView(context).apply {
//                                setTextColor(android.graphics.Color.parseColor("#252E32"))
//                                textSize = 16f
//                                movementMethod = android.text.method.LinkMovementMethod.getInstance()
//                            }
//                        },
//                        update = { view ->
//                            view.text = HtmlCompat.fromHtml(
//                                termsText,
//                                HtmlCompat.FROM_HTML_MODE_LEGACY
//                            )
//                        }
//                    )

                    val htmlWithCss = """
                    <html>
                    <head>
                        <style>
                            img { max-width: 100%; height: auto; }
                            body { margin: 0; padding: 0; font-size: 16px; color: #252E32; }
                        </style>
                    </head>
                    <body>
                        $termsText
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

                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

