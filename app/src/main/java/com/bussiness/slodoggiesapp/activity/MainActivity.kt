package com.bussiness.slodoggiesapp.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.navigation.NavGraph
import com.bussiness.slodoggiesapp.ui.component.common.AppLottieLoader
import com.bussiness.slodoggiesapp.ui.component.common.SetStatusBarColor
import com.bussiness.slodoggiesapp.viewModel.common.GlobalLoaderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {

                val useDarkIcons =
                    MaterialTheme.colorScheme.background.luminance() > 0.5f

                SetStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )

                // Global loader from ViewModel
                val loaderViewModel: GlobalLoaderViewModel = hiltViewModel()
                val isLoading by loaderViewModel.isLoading.collectAsState()

                // Entire UI wrapped so overlay is above dialogs too
                Box(modifier = Modifier.fillMaxSize()) {

                    val mainNavController = rememberNavController()

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavGraph(navController = mainNavController)
                    }

                    // Loader Overlay (Always on top)
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(100f)
                                .background(Color(0x80000000))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { }
                        ) {
                            AppLottieLoader(true)
                        }
                    }
                }
            }
        }
    }
}

