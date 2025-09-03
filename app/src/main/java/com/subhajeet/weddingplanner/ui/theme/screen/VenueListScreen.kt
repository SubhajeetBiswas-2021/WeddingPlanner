package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.subhajeet.weddingplanner.model.Venue
import com.subhajeet.weddingplanner.viewModel.VenueViewModel

@Composable
fun VenueListScreen(modifier: Modifier,viewModel: VenueViewModel = hiltViewModel(), navController: NavController) {

    val venues by viewModel.venue.collectAsState()

    val originalVenues = venues

    var query by rememberSaveable { mutableStateOf("") }

    var active by remember { mutableStateOf(false) } // Active state for SearchBar

    var searching by rememberSaveable { mutableStateOf(false) }  //very imaportant here for controlling the backbutton

    // Handle back button when search bar is active
    BackHandler(enabled = active || searching) {
        active = false
        query = ""
        searching = false
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(venues) { venue ->
            VenueItem(venue)
        }
    }
}


@Composable
fun VenueItem(venue: Venue) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = venue.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Location: ${venue.location}")
            Text(text = "Price Range: ${venue.priceRange}")
            Text(text = "Capacity: ${venue.capacity}")
        }
    }
}