package com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceProviderDetailsScreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.AppConstant
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.petOwner.servicesVM.ServiceDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ServiceProviderDetailsScreen(
    navController: NavHostController,
    serviceId: String,
    viewModel: ServiceDetailViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
   // Fetch service details only once
    LaunchedEffect(serviceId) {
        if(viewModel.serviceId.isEmpty()){
            viewModel.serviceId = serviceId
        }
        viewModel.setServiceDetail(viewModel.serviceId)
        viewModel.callCheckFollowStatus(serviceId)
    }

//    LaunchedEffect(Unit) {
//        viewModel.reviewSubmitted.collect {
//            if (it) {
//                snackbarHostState.showSnackbar(message = "Review submitted successfully")
//            }
//        }



  //  }

    LaunchedEffect(Unit) {
        viewModel.errorMessage.collectLatest { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    // Collect UI state
    val data = viewModel.serviceDetail.collectAsState().value

    var selectedOption by remember { mutableStateOf("Services") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BackHandler {
            navController.navigate(Routes.PET_SERVICES_SCREEN) {
                popUpTo(Routes.PET_SERVICES_SCREEN) { inclusive = false }
                launchSingleTop = true
                restoreState = true
            }
        }

        HeadingTextWithIcon(
            textHeading = stringResource(R.string.services),
            onBackClick = {
                navController.navigate(Routes.PET_SERVICES_SCREEN) {
                    popUpTo(Routes.PET_SERVICES_SCREEN) { inclusive = false }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        // Main Content
        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color.White)
                .padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Log.d("TESTING_SERVICE", "Service Detail ois"+data.serviceDetail?.businessName)
            ProviderDetails(
                serviceType = data.serviceDetail?.category?.firstOrNull() ?: "Service",
                cardRating = (data.serviceDetail?.rating ?: 0).toString(),
                personImage = data.serviceDetail?.profileImage ?: "",
                businessName = data.serviceDetail?.businessName ?: "Unknown",
                providerName = data.serviceDetail?.providerName ?: "Unknown Provider",
                userVerified = data.serviceDetail?.verificationStatus ?: false,
                businessDescription = data.serviceDetail?.businessDescription ?: "No Description",
                phoneNumber = data.serviceDetail?.phone ?: "Undisclosed",
                website = data.serviceDetail?.website ?: "Undisclosed",
                miles = data.serviceDetail?.milesAway ?: "Undisclosed",
                address = data.serviceDetail?.address ?: "Undisclosed",
                iAmFollowing1 = false,id=serviceId,
                onClickInquire = {
                    val receiverId = serviceId
                    val receiverImage = URLEncoder.encode(data.serviceDetail?.profileImage?:"", StandardCharsets.UTF_8.toString())
                    val receiverName = URLEncoder.encode(data.serviceDetail?.providerName?:"", StandardCharsets.UTF_8.toString())
                    val type = AppConstant.SINGLE

                    navController.navigate(
                        "${Routes.CHAT_SCREEN_FUNCTIONAL}/$receiverId/$receiverImage/$receiverName/$type"
                    )

                    //navController.navigate(Routes.CHAT_SCREEN)

                }
            )

            Spacer(Modifier.height(20.dp))

            SwitchButton(
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }
            )

            when (selectedOption) {
                "Services" -> ServicesContent(data.serviceDetail?.services)
                "Rating & Reviews" -> ReviewInterface(data.serviceDetail?.ratingsAndReviews){
                    rating,review ->
                        val sessionManager: SessionManager = SessionManager(context)
                    if(rating.equals("0")){
                        Toast.makeText(context,"Please Give Rating",Toast.LENGTH_LONG).show()
                        return@ReviewInterface
                    }
                        viewModel.serviceReview(

                            serviceId.toInt(), sessionManager.getUserId().toInt(),
                            rating,review
                        )
                }
            }
        }
    }
}


@Composable
fun ProviderDetails(
    serviceType : String,
    cardRating : String,
    personImage : String,
    providerName : String,
    userVerified : Boolean,
    miles : String,
    businessName : String,
    businessDescription: String,
    phoneNumber: String,
    website: String,
    address: String,
    viewModel: ServiceDetailViewModel = hiltViewModel(),
    iAmFollowing1: Boolean=false,
    id:String,
    onClickInquire: () -> Unit) {
    val context = LocalContext.current // <-- this replaces "context"

    var iAmFollowing by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.isFollow.collect {
            Log.d("TESTING_FOLLOW",""+it)
            iAmFollowing = it
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {


            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = personImage,
                    placeholder = painterResource(id = R.drawable.paw_icon),
                    error =  painterResource(id = R.drawable.paw_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(10.dp)),
                    )
                Surface(
                    color = Color.White, // White background
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, Color(0xFFCDCDCD)), // Light gray border
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 8.dp)
                        .align(Alignment.TopEnd), // Outer spacing,
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .widthIn(min=50.dp, max = 150.dp)
                            .height(25.dp)
                    ) {
                        Text(
                            text = serviceType,
                            fontSize = 10.sp,
                            color = Color(0xFF8A8894),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = businessName,
                            fontSize = 14.sp,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 170.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        if (userVerified){
                            Image(
                                painter = painterResource(id = R.drawable.ic_check_mark),
                                contentDescription = "Verified",
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    // Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_star),
                            contentDescription = "Rating",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$cardRating/5",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.location_ic_icon),
                            contentDescription = "location",
                            tint = Color.Unspecified,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = miles,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Row(
                    modifier = Modifier
                        .width(121.dp).align(Alignment.TopEnd)
                        .clickable { onClickInquire() }
                        .border(
                            width = 1.dp,
                            color = Color(0xFF258694),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding( vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_inform_checked),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Inquire now",
                        color = PrimaryColor,
                        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp), // same vertical spacing for both
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = providerName,
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    modifier = Modifier.weight(1f) // push Surface to the end
                )

                Surface(
                    modifier = Modifier.width(121.dp)
                ) {
//                    Text(
//                        text = stringResource(R.string.follow),
//                        fontSize = 12.sp,
//                        color = Color.White,
//                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp, vertical = 8.dp)
//                    )
                    Log.d("TESTING_FOLLOW","test "+iAmFollowing)

                    OutlinedButton(
                        onClick = {
                            viewModel.addAndRemoveFollowers(id){
                                it-> Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier
                            .height(24.dp)
                            .padding(horizontal = 10.dp),
                        shape = RoundedCornerShape(6.dp),
                        border = if (iAmFollowing) BorderStroke(1.dp, PrimaryColor) else null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (iAmFollowing) Color.White else PrimaryColor,
                            contentColor = if (iAmFollowing) PrimaryColor else Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        androidx.compose.material3.Text(
                            text = if (iAmFollowing) "Following" else "Follow",
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 12.sp
                        )
                    }
                }
            }
            EnhancedExpandableInfoSpinner(phoneNumber = phoneNumber, website = website, address = address, businessDescription = businessDescription)
        }
    }
}


@Composable
fun EnhancedExpandableInfoSpinner(
    businessDescription: String,
    phoneNumber: String,
    website: String,
    address: String
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xFF258694)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 10.dp,
                horizontal = 4.dp
            )
    ) {
        Column(
            modifier = Modifier
                .animateContentSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(R.string.additional_info),
                    color = PrimaryColor,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                )

                Image(
                    painter = painterResource(id = if (expanded) R.drawable.ic_dropdown_open_icon else R.drawable.ic_dropdown_close_icon),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            }

            // Content
            if (expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            ) {
                                append("Business Description : ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            ) {
                                append(businessDescription)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Required for Alignment.CenterEnd to work
                        verticalAlignment = Alignment.CenterVertically // Optional: Vertically centers all children
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_phone_icon),
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.phone),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = phoneNumber,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Required for Alignment.CenterEnd to work
                        verticalAlignment = Alignment.CenterVertically // Optional: Vertically centers all children
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_web_icon),
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.website),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = website,
                            color = PrimaryColor,
                            maxLines = 1,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            textDecoration =  TextDecoration.Underline
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Required for Alignment.CenterEnd to work
                        verticalAlignment = Alignment.CenterVertically // Optional: Vertically centers all children
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_location_icon),
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.address),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier.weight(0.5f)
                        )
                        Text(
                            text = address,
                            color = Color.Black,
                            maxLines = 1,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular))
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SwitchButton(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf("Services", "Rating & Reviews")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color(0xFFE5EFF2),
                shape = RoundedCornerShape(12.dp)
            )

    ) {
        // Animated indicator
        val animatedOffset by animateFloatAsState(
            targetValue = if (selectedOption == "Services") 0f else 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
            label = "switch_animation"
        )

        BoxWithConstraints {
            val containerWidth = maxWidth
            val indicatorWidth = (containerWidth - 4.dp) / 2

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(indicatorWidth)
                    .offset(x = animatedOffset * indicatorWidth)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .background(
                        color = Color(0xFF258694),
                        shape = RoundedCornerShape(8.dp)
                    )

            )
        }


        // Option buttons
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onOptionSelected(option)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        color = if (selectedOption == option) Color.White else Color.Black,
                        fontSize = 12.sp,
                        fontFamily = if (selectedOption == option) FontFamily(Font(R.font.outfit_medium)) else FontFamily(Font(R.font.outfit_regular)),
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ServiceProviderDetailsScreenPreview() {
    val navController = rememberNavController()
    ServiceProviderDetailsScreen(navController = navController, serviceId = "0")
}

