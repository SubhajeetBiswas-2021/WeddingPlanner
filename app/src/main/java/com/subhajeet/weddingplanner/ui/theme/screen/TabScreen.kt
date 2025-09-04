package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.subhajeet.weddingplanner.ui.theme.screen.nav.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(navController: NavController) {
    val tabs = listOf(
        TableItem(
            title = "CheckList",
            icon = Icons.Default.Create,
            filledIcon = Icons.Filled.Create
        ),
        TableItem(title = "Venues", icon = Icons.Default.Home, filledIcon = Icons.Filled.Home)
        //Add more tabs as needed
    )

    val pagerState =
        rememberPagerState(pageCount = { tabs.size }) //It is a state it knows currently which state is selected

    val scope =
        rememberCoroutineScope() //made custom couroutine as the animateScrollToPage can only function in couroutine

    // Drawer state
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
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
                    },colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Cyan,   // 🔵 Background color
                        titleContentColor = Color.White,       // 📝 Title color
                        navigationIconContentColor = Color.White, // ≡ Menu icon color
                        actionIconContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.fillMaxSize()) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(modifier = Modifier.fillMaxWidth().padding(0.dp, 86.dp, 0.dp, 0.dp),
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(text = tab.title, fontWeight = FontWeight.Bold)
                            },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = ""
                                )
                            }
                        )
                    }
                }
                HorizontalPager(state = pagerState) {

                    when (it) {
                        0 -> WeddingChecklistScreen(
                            navController = navController
                        )

                        1 -> VenueListScreen(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()


    val currentBackStackEntry = navController.currentBackStackEntryAsState()  //for giving color to the navigate screen in the navigation drawer
    val currentRoute = currentBackStackEntry.value?.destination?.route   //for giving color to the navigate screen in the navigation drawer


    ModalDrawerSheet {
        Column(Modifier.padding(16.dp)) {
            listOf(
                "TabScreen" to Routes.TabScreen,
                "BudgetCalculatorScreen" to Routes.BudgetCalculatorScreenRoute,
                //"Settings" to Routes.SignUpScreenRoute, // Example extra screen

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



data class TableItem(
    val title : String,
    val icon : ImageVector,
    val filledIcon: ImageVector
)