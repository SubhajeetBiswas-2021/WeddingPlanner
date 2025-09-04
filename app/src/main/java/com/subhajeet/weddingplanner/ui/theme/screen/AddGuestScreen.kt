package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.subhajeet.weddingplanner.GuestModel.Guest
import com.subhajeet.weddingplanner.viewModel.GuestViewModel

@Composable
fun AddGuestScreen(viewModel: GuestViewModel = hiltViewModel(),navController: NavHostController, name:String?=null, rsvpStatus:String?=null, id:Int?=null ) {
    val guestId = remember { mutableStateOf(id) }
    val guestName = remember { mutableStateOf(name ?: "") }
    val guestRsvp = remember { mutableStateOf(rsvpStatus ?: "") }

    Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ){

        TextField(
            value = guestName.value,
            onValueChange = { guestName.value = it },
            label = { Text("Guest Name") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = guestRsvp.value,
            onValueChange = { guestRsvp.value = it },
            label = { Text("RSVP Status (Yes/No/Maybe)") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        ElevatedButton(
            onClick = {
                if (guestId.value == null) {
                    // ➕ Add new guest
                    viewModel.addGuest(
                        name = guestName.value,
                        rsvp = guestRsvp.value
                    )
                } else {
                    // ✏️ Update existing guest
                    viewModel.updateGuest(
                        Guest(
                            id = guestId.value!!,
                            name = guestName.value,
                            rsvpStatus = guestRsvp.value
                        )
                    )
                }
                navController.popBackStack() // Go back to guest list
            }
        ) {
            Text(text = if (guestId.value == null) "Add Guest" else "Update Guest")
        }
    }
}