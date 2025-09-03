package com.subhajeet.weddingplanner.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subhajeet.weddingplanner.model.Venue
import com.subhajeet.weddingplanner.repo.VenueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueViewModel @Inject constructor(private val repository: VenueRepository) : ViewModel()  {

    private val _venues = MutableStateFlow<List<Venue>>(emptyList())
    val venue = _venues.asStateFlow()

    init {
        fetchVenues()
    }

    private fun fetchVenues() {
        viewModelScope.launch {
            _venues.value = repository.getVenues()
        }
    }

}