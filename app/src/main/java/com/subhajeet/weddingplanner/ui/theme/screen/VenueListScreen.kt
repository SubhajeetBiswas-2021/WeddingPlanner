package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.subhajeet.weddingplanner.model.Venue
import com.subhajeet.weddingplanner.viewModel.VenueViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueListScreen(modifier: Modifier,viewModel: VenueViewModel = hiltViewModel(), navController: NavController) {

    val venues by viewModel.venue.collectAsState()

    val originalVenues = venues

    var query by rememberSaveable { mutableStateOf("") }

    var active by remember { mutableStateOf(false) } // Active state for SearchBar

    var searching by rememberSaveable { mutableStateOf(false) }  //very important here for controlling the backbutton

    // Handle back button when search bar is active
    BackHandler(enabled = active || searching) {
        active = false
        query = ""
        searching = false
    }

    val filteredVenue = remember(originalVenues, query) {
        originalVenues.filter {
            it.priceRange.contains(query, ignoreCase = true) || it.name.contains(query, ignoreCase = true) ||
                    it.capacity.toString().contains(query)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
            //.padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        if(filteredVenue.isEmpty()){
            Text(text="No Venue Available", modifier = Modifier.padding(16.dp))
        }else{

            SearchBar(modifier = Modifier.fillMaxWidth(),
                query = query,
                onQueryChange = {
                    query = it
                },
                onSearch = {
                    //   active = false
                },
                active = active,
                onActiveChange = {
                    active = it
                    searching = it || query.isNotEmpty()
                },
                placeholder = {
                    Text(text = "Search Venues")
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (query.isNotEmpty()) {
                                    query = ""
                                } else {
                                    active = false
                                    searching = false
                                }
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close icon"
                        )
                    }
                }){
                if (active && filteredVenue.isNotEmpty()) {
                    LazyColumn {
                        items(filteredVenue) { venue ->
                           /* Text(
                                text = venue.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        query = venue.name
                                        active = false
                                        searching = true  // <-- add this line
                                    }
                                    .padding(16.dp)
                            )*/
                            VenueItem(venue)
                        }
                    }

                }else if (active && filteredVenue.isEmpty()) {
                    Text(
                        text = "No Venue found by this name.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            // 📋 Show filtered contacts or empty state
            if (filteredVenue.isEmpty() && query.isNotEmpty()) {
                Text(
                    text = "No Venue found by this name.",
                    modifier = Modifier.padding(16.dp)
                )
            } else if (filteredVenue.isEmpty()) {
                Text(
                    text = "No Venue available.",
                    modifier = Modifier.padding(16.dp)
                )
            }else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(filteredVenue) { venue ->
                        VenueItem(venue)
                    }
                }

            }
        }

       /* LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(venues) { venue ->
                VenueItem(venue)
            }
        }*/
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