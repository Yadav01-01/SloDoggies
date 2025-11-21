package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import android.annotation.SuppressLint
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.termscondition.TermsAndConditionViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@SuppressLint("ContextCastToActivity")
@Composable
fun TermsAndConditionsScreen(navController: NavHostController) {
    ShowDataTermCondition(navController,"Settings")
}
// ==================================================================
//                    AUTH TERMS SCREEN (UNCHANGED)
// ==================================================================
@Composable
fun AuthTermsAndConditionsScreen(navController: NavHostController) {
    ShowDataTermCondition(navController,"Login")
}

@Composable
fun ShowDataTermCondition(navController: NavHostController, type: String) {
    var isNavigating by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    val viewModel: TermsAndConditionViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // ---------------- BACK HANDLER ----------------
    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    // ---------------- DATA LOADER FUNCTION ----------------
    fun loadData() {
        viewModel.termsConditionRequest(
            onError = { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                isRefreshing = false
            },
            onSuccess = {
                isRefreshing = false
            }
        )
    }

    // FIRST LOAD
    LaunchedEffect(Unit) { loadData() }
    val parentModifier = if (type == "Login") {
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    } else {
        Modifier
            .fillMaxSize()
            .background(Color.White)
    }
    // ---------------- UI ----------------
    Column(modifier = parentModifier) {

        HeadingTextWithIcon(
            textHeading = "Terms & Conditions",
            onBackClick = {
                if (!isNavigating) {
                    isNavigating = true
                    navController.popBackStack()
                }
            }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(Modifier.height(10.dp))

        // ------------ PULL TO REFRESH WRAPPER --------------
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                isRefreshing=true
                loadData() }
        ) {
            val termsText = uiState.data?.content

            if (termsText.isNullOrBlank()) {
                // ------------ NO DATA FOUND VIEW ------------
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
                // ------------ MAIN HTML CONTENT ------------
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    AndroidView(
                        factory = { context ->
                            TextView(context).apply {
                                setTextColor(android.graphics.Color.parseColor("#252E32"))
                                textSize = 16f

                                // Optional: Enable clickable links
                                movementMethod = android.text.method.LinkMovementMethod.getInstance()
                            }
                        },
                        update = { view ->
                            view.text = HtmlCompat.fromHtml(
                                termsText,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )
                        }
                    )
                }

            }
        }

    }

}


@Preview
@Composable
fun TermConditionPreview(){
    TermsAndConditionsScreen(navController = NavHostController(LocalContext.current))
}