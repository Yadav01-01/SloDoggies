package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.Review
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AddServiceButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.PetSitterCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TypeButton
import com.bussiness.slodoggiesapp.ui.dialog.CommentReplyDialog
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content.ReviewContent
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content.ServicePackageSection
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.petOwner.servicesVM.ServiceDetailViewModel
import com.bussiness.slodoggiesapp.viewModel.servicebusiness.BusinessServicesViewModel
import com.bussiness.slodoggiesapp.viewModel.serviceslist.ServicesListViewModel

@Composable
fun ServiceScreen(navController: NavHostController) {

    val viewModel: BusinessServicesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val viewModelService: ServicesListViewModel = hiltViewModel()
    val uiStateService by viewModelService.uiState.collectAsState()

    val serviceProviderDetailViewModel : ServiceDetailViewModel = hiltViewModel()
    val uiStateServiceProvider by serviceProviderDetailViewModel.serviceDetail.collectAsState()




    val context = LocalContext.current

    var selected by remember { mutableStateOf("Services") }
    var commentReply by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val deleteDialog by serviceProviderDetailViewModel.showDeleteDialog.collectAsState()

    val reviewListData = listOf(
        Review(
            name = "Courtney Henry",
            rating = 5,
            timeAgo = "2 mins ago",
            review = "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco exercitation. Ullamco tempor adipisicing et voluptate duis sit commodo aliqua",
            avatar = R.drawable.dummy_baby_pic,
            reply = com.bussiness.slodoggiesapp.data.model.businessProvider.ReviewReply(
                providerName = "Rosy Morgan",
                providerImage = "https://example.com/provider1.png",
                providerRole = "Provider",
                pTimeAgo = "Just Now",
                pMessage = "Thanks so much for the kind words, Courtney! We're thrilled your pup enjoyed their visit."
            )
        ),

        Review(
            name = "Coy Hetry",
            rating = 3,
            timeAgo = "30 mins ago",
            review = "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco exercitation. Ullamco tempor adipisicing et voluptate duis sit commodo aliqua",
            avatar = R.drawable.dummy_baby_pic
        )
    )

    LaunchedEffect(Unit) {
        serviceProviderDetailViewModel.getServiceDetail()
       // viewModel.getBusinessDetail("")
    }

    // Inside your Composable
    // when this effect work then selected change to services
    LaunchedEffect(selected) {
        if (selected == "Services") {
            viewModelService.servicesList()
        } else if (selected == "Rating & Reviews") {
             Log.d("Api call pending","******")
        }
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
        }
    }

    BackHandler {
        navController.navigate(Routes.HOME_SCREEN) {
            launchSingleTop = true
            popUpTo(Routes.HOME_SCREEN) {
                inclusive = false
            }
        }
    }

    Column ( modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        HeadingTextWithIcon(textHeading = stringResource(R.string.services),
            onBackClick = { navController.navigate(Routes.HOME_SCREEN){
                launchSingleTop = true
                popUpTo(Routes.HOME_SCREEN) {
                    inclusive = false
                }
            } },)

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            val data=uiStateServiceProvider.serviceDetail
            PetSitterCard(
                name = data?.businessName?:"Unknown",
                image=data?.profileImage?:"",
                rating = data?.rating?:0.0f,
                ratingCount = 100,
                providerName = data?.providerName?:"Unknown",
                about = data?.businessDescription?:"Bio",
                phone = data?.phone?:"Unknown",
                website = data?.website?:"Unknown",
                address = data?.address?:"Unknown",
                onEditClick = { navController.navigate(Routes.EDIT_BUSINESS_SCREEN)}
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFE5EFF2))
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("Services", "Rating & Reviews").forEach { label ->
                    TypeButton(
                        modifier = Modifier.weight(1f),
                        text = label,
                        isSelected = selected == label,
                        onClick = { selected = label }
                    )
                }
            }

            when (selected) {
                "Services" -> {
                    uiStateServiceProvider.serviceDetail?.let {
                        ServicePackageSection(
                            uiStateService = uiStateServiceProvider.serviceDetail!!.services.toMutableList(),
                            navController,
                            onClickDelete = { id->
                                serviceProviderDetailViewModel.selectedServiceId= id
                                serviceProviderDetailViewModel.showDeleteDialog()

                            })
                    }
                }
                "Rating & Reviews" -> ReviewContent(//reviewListData,
                    uiStateServiceProvider.serviceDetail?.ratingsAndReviews,
                 onClickReply = { commentReply = true })
            }

            AddServiceButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClickButton = {
                  //  navController.navigate("${Routes.EDIT_ADD_SERVICE_SCREEN}/add/${null}")
                    navController.navigate(Routes.ADD_SERVICE_NEW)
                }
            )
        }
    }



    if (commentReply) {
        CommentReplyDialog(
            onDismiss = { commentReply = false },
            onClickSubmit = { /* Handle submit */ },
            message = message,
            onMessageChange = { message = it }
        )
    }
    if (deleteDialog){

        DeleteChatDialog(
            onDismiss = {  serviceProviderDetailViewModel.hideDeleteDialog() },
            onClickRemove = {
                val sessionManager : SessionManager = SessionManager(context)
                val userId = sessionManager.getUserId()
                serviceProviderDetailViewModel.deleteService(userId.toInt(), serviceProviderDetailViewModel.selectedServiceId)

                            },
            iconResId = R.drawable.delete_mi,
            text = stringResource(R.string.delete_service),
            description = stringResource(R.string.service_desc)
        )

    }

}

@Preview
@Composable
fun PreviewService(){
    ServiceScreen(rememberNavController())
}