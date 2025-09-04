package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.subhajeet.weddingplanner.ui.theme.screen.nav.Routes
import com.subhajeet.weddingplanner.viewModel.BudgetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetCalculatorScreen(
    viewModel: BudgetViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val budget by viewModel.budget.collectAsState()
    val split by viewModel.split.collectAsState()

    var input by remember { mutableStateOf("") }

    // Drawer state
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope =
        rememberCoroutineScope()

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
                    title = { Text("Budget Calculator") },
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
            }
        ) {innerPadding->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(23.dp))
                Text("Wedding Budget Calculator", style = MaterialTheme.typography.headlineSmall)

                // TextField to enter budget
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("Enter Budget without any special  character(₹)") }
                )

                // Button to update budget
                Button(onClick = {
                    val value = input.toDoubleOrNull()
                    if (value != null) {
                        viewModel.setBudget(value)
                    }
                }) {
                    Text("Calculate")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Total Budget: ₹$budget", style = MaterialTheme.typography.bodyLarge)

                HorizontalDivider()

                // Show split
                Text("Venue: ₹${split.venue}")
                Text("Catering: ₹${split.catering}")
                Text("Décor: ₹${split.decor}")
            }
        }

    }
/*
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(23.dp))
        Text("Wedding Budget Calculator", style = MaterialTheme.typography.headlineSmall)

        // TextField to enter budget
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter Budget without any special  character(₹)") }
        )

        // Button to update budget
        Button(onClick = {
            val value = input.toDoubleOrNull()
            if (value != null) {
                viewModel.setBudget(value)
            }
        }) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Total Budget: ₹$budget", style = MaterialTheme.typography.bodyLarge)

        HorizontalDivider()

        // Show split
        Text("Venue: ₹${split.venue}")
        Text("Catering: ₹${split.catering}")
        Text("Décor: ₹${split.decor}")
    }*/
}


@Composable
fun DrawerContents(navController: NavController, drawerState: DrawerState) {
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

