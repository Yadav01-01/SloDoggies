package com.bussiness.slodoggiesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.EditBusinessScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.DiscoverScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.HomeScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.SubscriptionScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.ProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.ServiceScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.PersonDetailScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.PostScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.EditProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.FollowerScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.SponsoredAdsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.community.CommunityChatScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.AboutUsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.FAQScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.HelpAndSupportScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.MyEventScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.PrivacyPolicyScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.SavedItemScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.SettingsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.TermsAndConditionsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.message.MessageScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.notification.NotificationScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.community.AddParticipantsScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.community.CommunityProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.message.ChatScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME_SCREEN,
        modifier = modifier
    ) {
        composable(Routes.HOME_SCREEN) { HomeScreen(navController) }
        composable(Routes.DISCOVER_SCREEN) { DiscoverScreen(navController) }
        composable(Routes.SERVICES_SCREEN) { ServiceScreen(navController) }
        composable(Routes.PROFILE_SCREEN) { ProfileScreen(navController) }
        composable(Routes.PERSON_DETAIL_SCREEN) { PersonDetailScreen(navController) }
        composable(Routes.FOLLOWER_SCREEN) { FollowerScreen(navController) }
        composable(Routes.SPONSORED_ADS_SCREEN) { SponsoredAdsScreen(navController) }
        composable(Routes.EDIT_PROFILE_SCREEN) { EditProfileScreen(navController) }
        composable(Routes.POST_SCREEN) { PostScreen(navController) }
        composable(Routes.EDIT_BUSINESS_SCREEN) { EditBusinessScreen(navController) }
        composable(Routes.SETTINGS_SCREEN) { SettingsScreen(navController) }
        composable(Routes.SAVED_ITEM_SCREEN) { SavedItemScreen(navController) }
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
    }
}
