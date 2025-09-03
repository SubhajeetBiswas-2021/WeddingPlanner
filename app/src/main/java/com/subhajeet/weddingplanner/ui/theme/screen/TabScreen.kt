package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun TabScreen(navController: NavController) {
    val tabs = listOf(
        TableItem(title = "CheckList", icon = Icons.Default.Create, filledIcon = Icons.Filled.Create) ,
        TableItem(title = "Venues", icon = Icons.Default.Home, filledIcon = Icons.Filled.Home)
        //Add more tabs as needed
    )

    val pagerState = rememberPagerState(pageCount = {tabs.size}) //It is a state it knows currently which state is selected

    val scope = rememberCoroutineScope() //made custom couroutine as the animateScrollToPage can only function in couroutine

    Column(modifier=Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed{ index,tab ->
                Tab(modifier= Modifier.fillMaxWidth().padding(0.dp,18.dp,0.dp,0.dp),
                    selected = pagerState.currentPage == index,
                    onClick ={
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text=tab.title,  fontWeight = FontWeight.Bold)
                    },
                    icon = {
                        Icon(imageVector = tab.icon,
                            contentDescription ="" )
                    }
                )
            }
        }
        HorizontalPager(state = pagerState) {

            when(it){
                0 -> WeddingChecklistScreen(
                    navController = navController
                )
                1 -> VenueListScreen(modifier = Modifier.fillMaxSize(),navController = navController)
            }
        }
    }
}


data class TableItem(
    val title : String,
    val icon : ImageVector,
    val filledIcon: ImageVector
)