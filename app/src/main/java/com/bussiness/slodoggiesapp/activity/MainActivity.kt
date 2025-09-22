package com.bussiness.slodoggiesapp.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.navigation.NavGraph
import com.bussiness.slodoggiesapp.ui.component.common.SetStatusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                SetStatusBarColor(color = Color.Transparent, darkIcons = true)
                val mainNavController = rememberNavController()
                NavGraph(navController = mainNavController)
            }
        }
    }
}

