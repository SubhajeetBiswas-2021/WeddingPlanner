package com.subhajeet.weddingplanner

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.subhajeet.weddingplanner.repo.VenueRepository
import com.subhajeet.weddingplanner.ui.theme.WeddingPlannerTheme
import com.subhajeet.weddingplanner.ui.theme.screen.VenueListScreen
import com.subhajeet.weddingplanner.ui.theme.screen.nav.NavApp
import com.subhajeet.weddingplanner.viewModel.VenueViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeddingPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavApp()
                }
            }
        }
    }
}

