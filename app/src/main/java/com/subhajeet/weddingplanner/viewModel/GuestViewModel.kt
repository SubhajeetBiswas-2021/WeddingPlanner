package com.subhajeet.weddingplanner.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subhajeet.weddingplanner.Utils.ResultState
import com.subhajeet.weddingplanner.GuestModel.Guest
import com.subhajeet.weddingplanner.repo.GuestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GuestViewModel @Inject constructor(
    private val repository: GuestRepository
) : ViewModel() {

    private val _guests = MutableStateFlow<ResultState<List<Guest>>>(ResultState.Success(emptyList()))
    val guests = _guests.asStateFlow()

    fun loadGuests() {
        viewModelScope.launch {
            repository.getAllGuests().collect { result ->
                _guests.value = result
            }
        }
    }

    fun addGuest(name: String, rsvp: String) {
        viewModelScope.launch {
            repository.insertGuest(Guest(name = name, rsvpStatus = rsvp)).collect { result ->
                if (result is ResultState.Success) loadGuests()
            }
        }
    }

    fun updateGuest(guest: Guest) {
        viewModelScope.launch {
            repository.updateGuest(guest).collect { result ->
                if (result is ResultState.Success) loadGuests()
            }
        }
    }

    fun deleteGuest(guest: Guest) {
        viewModelScope.launch {
            repository.deleteGuest(guest).collect { result ->
                if (result is ResultState.Success) loadGuests()
            }
        }
    }
}