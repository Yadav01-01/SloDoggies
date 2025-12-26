package com.bussiness.slodoggiesapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.settings.SubscriptionScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.DiscoverScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.PersonDetailScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.notification.NotificationScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.PostScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.promotion.BudgetScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.promotion.PreviewAdsScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.FollowerScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.ProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.EditBusinessScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.ServiceScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.addOrEdit.EditAddServiceScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.SponsoredAdsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.ClickedProfile
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.community.AddParticipantsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.community.CommunityChatScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.community.CommunityProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.HomeScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.VerifyAccount
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.message.ChatScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.message.MessageScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.message.NewMessageScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.profilepost.EditPostScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.profilepost.UserPost
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.AboutUsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.AccountPrivacy
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.FAQScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.HelpAndSupportScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.MyEventScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.PrivacyPolicyScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.SavedItemScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.SettingsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.TermsAndConditionsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.TransactionScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.event.EventScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.notification.PetNotificationsScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.PetNewPostScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens.EditPetProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens.EditProfileScreenPet
import com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens.PetProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.service.PetServicesScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceProviderDetailsScreen.ServiceProviderDetailsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    authNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Helper function to safely navigate without duplicating backstack
    fun NavHostController.safeNavigate(route: String) {
        this.navigate(route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(this@safeNavigate.graph.startDestinationId) {
                saveState = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME_SCREEN,
        modifier = modifier
    ) {
        composable(Routes.HOME_SCREEN) { HomeScreen(navController) }
        composable(Routes.DISCOVER_SCREEN) { DiscoverScreen(navController) }
        composable(Routes.SERVICES_SCREEN) { ServiceScreen(navController) }
        composable(Routes.PROFILE_SCREEN) { ProfileScreen(navController) }
//        composable(Routes.PERSON_DETAIL_SCREEN) { PersonDetailScreen(navController) }
        composable(Routes.SPONSORED_ADS_SCREEN+ "?fromPreview={fromPreview}",
            arguments = listOf(
                navArgument("fromPreview") { defaultValue = false }
            )) { SponsoredAdsScreen(navController) }
        composable(Routes.POST_SCREEN) { PostScreen(navController) }
        composable(Routes.EDIT_BUSINESS_SCREEN) { EditBusinessScreen(navController) }
        composable(Routes.SETTINGS_SCREEN) { SettingsScreen(navController,authNavController) }
        composable(Routes.SAVED_ITEM_SCREEN) { SavedItemScreen(navController) }
        composable(Routes.ABOUT_US_SCREEN) { AboutUsScreen(navController) }
        composable(Routes.TERMS_AND_CONDITION_SCREEN) { TermsAndConditionsScreen(navController) }
        composable(Routes.PRIVACY_POLICY_SCREEN) { PrivacyPolicyScreen(navController) }
        composable(Routes.FAQ_SCREEN) { FAQScreen(navController) }
        composable(Routes.HELP_AND_SUPPORT_SCREEN) { HelpAndSupportScreen(navController) }
        composable(Routes.MY_EVENT_SCREEN) { MyEventScreen(navController) }
        composable(Routes.COMMUNITY_CHAT_SCREEN) { CommunityChatScreen(navController) }
        composable(Routes.NOTIFICATION_SCREEN) { NotificationScreen(navController) }
        composable(Routes.MESSAGE_SCREEN) { MessageScreen(navController) }
        composable(Routes.CHAT_SCREEN) { ChatScreen(navController) }
        composable(Routes.SUBSCRIPTION_SCREEN) { SubscriptionScreen(navController) }
        composable(Routes.COMMUNITY_PROFILE_SCREEN) { CommunityProfileScreen(navController) }
        composable(Routes.ADD_PARTICIPANTS_SCREEN) { AddParticipantsScreen(navController) }
        composable(Routes.PET_NEW_POST_SCREEN) { PetNewPostScreen(navController) }
        composable(Routes.PET_SERVICES_SCREEN) { PetServicesScreen(navController) }
        composable(Routes.PET_PROFILE_SCREEN) { PetProfileScreen(navController) }
//        composable(Routes.SERVICE_PROVIDER_DETAILS) { ServiceProviderDetailsScreen(navController) }
//        composable(Routes.EDIT_PET_PROFILE_SCREEN) { EditPetProfileScreen(navController) }
        composable(Routes.PET_ADD_PARTICIPANTS_SCREEN) { AddParticipantsScreen(navController) }
        composable(Routes.EDIT_PROFILE_SCREEN_PET) { EditProfileScreenPet(navController) }
        composable(Routes.PET_NOTIFICATION_SCREEN) { PetNotificationsScreen(navController) }
        composable(Routes.PET_EVENT_SCREEN) { EventScreen(navController) }
        composable(Routes.ACCOUNT_PRIVACY_SCREEN) { AccountPrivacy(navController,authNavController) }
//        composable(Routes.USER_POST_SCREEN) { UserPost(navController) }
        composable(
            route = Routes.USER_POST_SCREEN + "/{postId}/{type}/{userId}",
            arguments = listOf(
                navArgument("postId") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType }
            )
        ) {
            val postId = it.arguments?.getString("postId")
            val type = it.arguments?.getString("type") ?: ""
            val userId = it.arguments?.getString("userId") ?: ""
            UserPost(navController, postId?:"",type,userId)
        }
        composable(Routes.EDIT_POST_SCREEN) { EditPostScreen(navController) }
        composable(Routes.BUDGET_SCREEN) { BudgetScreen(navController) }
        composable(Routes.PREVIEW_ADS_SCREEN) { PreviewAdsScreen(navController) }
        composable(Routes.NEW_MESSAGE_SCREEN) { NewMessageScreen(navController) }
        composable(Routes.TRANSACTION_SCREEN) { TransactionScreen(navController) }
       // composable(Routes.CLICKED_PROFILE_SCREEN) { ClickedProfile(navController) }
        composable(
            route = Routes.CLICKED_PROFILE_SCREEN + "/{userId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?:""
            ClickedProfile(navController, userId)
        }

        // ----------------- Screens with arguments -----------------
        composable(
            route = "${Routes.VERIFY_ACCOUNT_SCREEN}?type={type}&data={data}",
            arguments = listOf(
                navArgument("type") { type = NavType.StringType; defaultValue = "default" },
                navArgument("data") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "default"
            val data = backStackEntry.arguments?.getString("data") ?: ""
            VerifyAccount(navController, type = type, data = data)
        }

        composable(
            route = "${Routes.EDIT_ADD_SERVICE_SCREEN}/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType; defaultValue = "" })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: ""
            EditAddServiceScreen(navController, type)
        }

        composable(
            route = "${Routes.FOLLOWER_SCREEN}/{type}/{userId}",
            arguments = listOf(navArgument("type") { type = NavType.StringType }, navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "Follower"
            val userId = backStackEntry.arguments?.getString("userId").orEmpty()
            FollowerScreen(navController = navController, type = type, userId = userId)
        }



        composable(
            route = "${Routes.EDIT_PET_PROFILE_SCREEN}/{petId}",
            arguments = listOf(navArgument("petId") { type = NavType.StringType; defaultValue = " " })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: "Follower"
            EditPetProfileScreen(navController, petId)
        }

        composable(
            route = "${Routes.SERVICE_PROVIDER_DETAILS}/{serviceId}",
            arguments = listOf(navArgument("serviceId") {
                    type = NavType.StringType
                    defaultValue = "" }
            )
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getString("serviceId").orEmpty()
            ServiceProviderDetailsScreen(navController = navController, serviceId = serviceId)
        }

        composable(
            route = "${Routes.PERSON_DETAIL_SCREEN}/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
                defaultValue = "" }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId").orEmpty()
            PersonDetailScreen(navController = navController, userId = userId)
        }

    }
}