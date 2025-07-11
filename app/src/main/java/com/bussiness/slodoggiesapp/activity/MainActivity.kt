package com.bussiness.slodoggiesapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.navigation.NavGraph
import com.bussiness.slodoggiesapp.ui.theme.SlodoggiesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val mainNavController = rememberNavController()
                val authNavController = rememberNavController()
                NavGraph(
                    navController = mainNavController,
                    authNavController = authNavController
                )
            }

        }
    }
}
