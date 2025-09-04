package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.subhajeet.weddingplanner.Utils.ResultState
import com.subhajeet.weddingplanner.GuestModel.Guest
import com.subhajeet.weddingplanner.ui.theme.screen.nav.Routes
import com.subhajeet.weddingplanner.viewModel.GuestViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestListScreen(viewModel: GuestViewModel = hiltViewModel(), navController: NavHostController) {

    val guestState = viewModel.guests.collectAsState().value

    // Drawer state
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope =
        rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadGuests()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContents(
                navController = navController,
                drawerState = drawerState
            )
        }
    ) {


    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("Wedding Planner") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Cyan,   // 🔵 Background color
                    titleContentColor = Color.White,       // 📝 Title color
                    navigationIconContentColor = Color.White, // ≡ Menu icon color
                    actionIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            IconButton(
                onClick = {
                    navController.navigate(
                        Routes.AddGuestRoute(
                            id = null,
                            name = "",
                            rsvpStatus = "",
                        )
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Contact",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }


    ) { innerPadding ->

        when (guestState) {
            is ResultState.Loading -> {
                Text("Loading guests...", modifier = Modifier.padding(16.dp))
            }

            is ResultState.Error -> {
                Text(
                    text = "Error: ${guestState.message}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is ResultState.Success -> {
                LazyColumn (contentPadding = innerPadding){
                    items(guestState.data) { guest ->
                        EachCard(
                            guest = guest,
                            onDelete = { viewModel.deleteGuest(guest) },
                            onClick = {
                                navController.navigate(
                                    Routes.AddGuestRoute(
                                        id = guest.id,
                                        name = guest.name,
                                        rsvpStatus = guest.rsvpStatus
                                    )
                                )
                            }
                        )
                    }
                }
            }

            else -> { // covers any unhandled states
                Text("Idle...", modifier = Modifier.padding(16.dp))
            }
        }

    }
}
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EachCard(guest: Guest, onDelete:() -> Unit, onClick:()-> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
        onClick()
    }) {

        Box(modifier = Modifier.fillMaxWidth()) {


            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {



                Column(
                    modifier = Modifier

                        .padding(16.dp),
                ) {


                    Text(text = guest.name, fontSize = 20.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold )
                    Text(text = "RSVP: ${guest.rsvpStatus}")


                    Button(
                        onClick = onDelete
                    ) {
                        Text(text = "Delete Contact")
                    }
                }




            }

            // 📞 Update Icon at Top Right
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val context = LocalContext.current
                IconButton(
                    onClick = onClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp).clip(CircleShape).border(2.dp, Color.Gray, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Update",
                        tint= Color.Black
                    )
                }
            }


        }
    }
}


@Composable
fun Drawer(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    val currentBackStackEntry = navController.currentBackStackEntryAsState()  //for giving color to the navigate screen in the navigation drawer
    val currentRoute = currentBackStackEntry.value?.destination?.route   //for giving color to the navigate screen in the navigation drawer

    ModalDrawerSheet {
        Column(Modifier.padding(16.dp)) {
            listOf(
                "TabScreen" to Routes.TabScreen,
                "BudgetCalculatorScreen" to Routes.BudgetCalculatorScreenRoute,
                "GuestListScreen" to Routes.GuestListScreenRoute

            ).forEach { (label, route) ->
                val isSelected = currentRoute == route::class.qualifiedName  //for giving color to the navigate screen in the navigation drawer
                Text(
                    text = label,
                    color = if (isSelected) Color.Red else Color.Black, // 🔵 Highlighted text,   //for giving color to the navigate screen in the navigation drawer
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .background(if (isSelected) Color(0xFFE0F7FA) else Color.Transparent) // light cyan bg
                        .clickable {
                            navController.navigate(route) {
                                popUpTo(Routes.TabScreen) { inclusive = false }
                            }
                            scope.launch { drawerState.close() }
                        }
                )
            }
        }
    }
}

