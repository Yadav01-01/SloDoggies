package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.promotion

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitPreviewButton
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.PreviewHeading
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.PreviewSubHeading
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.SponsorPostCaption
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.SponsorPostHeader
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.SponsorPostMedia
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel

@Composable
fun PreviewAdsScreen(navController: NavHostController) {

    // Get the ViewModel scoped to the previous screen
    val viewModel: PostContentViewModel = hiltViewModel(
        navController.getBackStackEntry(Routes.POST_SCREEN)
    )

    val uiStateAddCreate by viewModel.uiState.collectAsState()
    Log.d("******", uiStateAddCreate.adTitle.toString()+uiStateAddCreate.budget) // now you get the filled data
    val context = LocalContext.current
    val sessionManager = SessionManager.getInstance(context)
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        BackHandler {
            navController.navigate(Routes.BUDGET_SCREEN){
                launchSingleTop = true
                popUpTo(Routes.BUDGET_SCREEN){
                    inclusive = false
                }
            }
        }

        HeadingTextWithIcon(textHeading = "Preview Ad",
            onBackClick = { navController.navigate(Routes.BUDGET_SCREEN){
                launchSingleTop = true
                popUpTo(Routes.BUDGET_SCREEN){
                    inclusive = false
                }
            } })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SponsorPostHeader(userImage = "${sessionManager.getUserImage()}", user = "${sessionManager.getUserName()}", time = "", onReportClick = { })
                SponsorPostCaption(caption = "${uiStateAddCreate.adTitle}", description = "${uiStateAddCreate.adDescription}")
                SponsorPostMedia(mediaList = uiStateAddCreate.image)
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE5EFF2), modifier = Modifier.padding(vertical = 12.dp))
            }

            // --- Ad Details Section ---
            item {
                Section(
                    title = "• Ad Details",
                    items = {
                        PreviewSubHeading(stringResource(R.string.adtitle), uiStateAddCreate.adTitle?:"")
                        PreviewSubHeading(stringResource(R.string.description__), uiStateAddCreate.adDescription?:"")
                        PreviewSubHeading(stringResource(R.string.Category_), (uiStateAddCreate.category ?: emptyList()).joinToString(","))
                        PreviewSubHeading(stringResource(R.string.Service_), uiStateAddCreate.service?:"")
                        PreviewSubHeading(stringResource(R.string.exp_date_time), "${uiStateAddCreate.expiryDate} - ${uiStateAddCreate.expiryTime}")
                        PreviewSubHeading(stringResource(R.string.terms_con), uiStateAddCreate.termAndConditions?:"")

                    }
                )
            }

            // --- Engagement & Location Section ---
            item {
                Section(
                    title = "• Engagement & Location",
                    items = {
                        PreviewSubHeading(stringResource(R.string.location_type), uiStateAddCreate.serviceLocation?:"")
                        PreviewSubHeading(
                            stringResource(R.string.contact_info),
                            if (uiStateAddCreate.mobile_visual == "1") "✅ Yes" else "❌ No"
                        )
                        PreviewSubHeading(stringResource(R.string.mobile_no), uiStateAddCreate.contactNumber?:"")
                    }
                )
            }

            // --- Pricing Section ---
            item {
                Section(
                    title = "• Pricing & Reach Estimates",
                    items = {
                        PreviewSubHeading(stringResource(R.string.daliy_budget), "$${uiStateAddCreate.budget}")
                        PreviewSubHeading(stringResource(R.string.ads_budget), "$${uiStateAddCreate.budget} per day")
                    }
                )
            }

            // --- Submit Button ---
            item {
                Spacer(Modifier.height(15.dp))
                SubmitPreviewButton(buttonText = "Submit", onClickButton = {


                    viewModel.createAd(context=context,
                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                        onSuccess = {
                            navController.navigate(Routes.SPONSORED_ADS_SCREEN + "?fromPreview=true")
                        }
                    )
                })
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun Section(title: String, items: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PreviewHeading(title)
        items()
    }
}
