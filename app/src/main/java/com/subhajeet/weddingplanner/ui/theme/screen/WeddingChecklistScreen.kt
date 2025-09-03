package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.subhajeet.weddingplanner.ui.theme.screen.nav.Routes
import com.subhajeet.weddingplanner.viewModel.MyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeddingChecklistScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {

    val tasks by viewModel.tasks.collectAsState()

    val originaltasks = tasks

    var query by rememberSaveable { mutableStateOf("") }

    var active by remember { mutableStateOf(false) } // Active state for SearchBar

    var searching by rememberSaveable { mutableStateOf(false) }  //very imaportant here for controlling the backbutton

    // Handle back button when search bar is active
    BackHandler(enabled = active || searching) {
        active = false
        query = ""
        searching = false
    }

    val filteredTasks = remember(originaltasks, query){ originaltasks.filter {
        it.title.contains(query, ignoreCase = true)

    }
    }

    var newTask by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.getAllTasks()
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    label = { Text("New Task") },
                    placeholder = { Text("Type here...") }, // 👈 Only visible when empty
                    modifier = Modifier.weight(1f).padding(0.dp,0.dp,0.dp,45.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color(0xFFE3F2FD), // light blue background
                        focusedBorderColor = Color.Blue,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.DarkGray
                )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (newTask.isNotBlank()) {
                        viewModel.addTask(newTask)
                        newTask = ""
                    }
                }) {
                    Text("Add")
                }
            }
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(0.dp)) {
            if(filteredTasks.isEmpty()){
                Text(text="No Contacts Available", modifier = Modifier.//padding(16.dp))
                padding(horizontal = 16.dp, vertical = 8.dp)) // 👈 cleaner spacing
            }else{
                SearchBar(modifier = Modifier.fillMaxWidth().padding(horizontal = 11.dp, vertical = 0.dp),//.padding(0.dp,25.dp,0.dp,0.dp),
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
                        Text(text = "Search contacts")
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

                    if (active && filteredTasks.isNotEmpty()) {
                        LazyColumn {
                            items(filteredTasks) { task ->
                                Text(
                                    text = task.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            query = task.title
                                            active = false
                                            searching = true  // <-- add this line
                                        }
                                        .padding(16.dp)
                                )
                            }
                        }

                    } else if (active && filteredTasks.isEmpty()) {
                        Text(
                            text = "No contact found by this name.",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }


                // 📋 Show filtered contacts or empty state
                if (filteredTasks.isEmpty() && query.isNotEmpty()) {
                    Text(
                        text = "No contact found by this name.",
                        modifier = Modifier.padding(16.dp)
                    )
                } else if (filteredTasks.isEmpty()) {
                    Text(
                        text = "No contacts available.",
                        modifier = Modifier.padding(16.dp)
                    )
                }else{
                    Text(
                        "Wedding Checklist",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold
                    )
                    LazyColumn {

                        items(filteredTasks) { task ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.toggleComplete(task) }
                                    .padding(16.dp)
                            ) {
                                Checkbox(
                                    checked = task.isCompleted,
                                    onCheckedChange = { viewModel.toggleComplete(task) }
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = task.title,
                                    style = if (task.isCompleted) {
                                        TextStyle(textDecoration = TextDecoration.LineThrough)
                                    } else {
                                        TextStyle.Default
                                    }
                                )
                                Spacer(Modifier.width(70.dp))
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Icon(imageVector = Icons.Default.Create,
                                        contentDescription = "Update",
                                        modifier = Modifier.align(Alignment.TopEnd)
                                            .padding(8.dp).clip(CircleShape)
                                            .border(1.dp, Color.Gray, CircleShape).clickable {
                                                navController.navigate(
                                                    Routes.UpdataChecklistRoute(
                                                        title = task.title,
                                                        id = task.id,
                                                        isCompleted = task.isCompleted
                                                    )
                                                )
                                            })
                                }
                            }


                        }
                    }

                }
            }


        }
        LaunchedEffect(tasks) {
            println("📋 Tasks updated: ${tasks.map { it.title }}")
        }

    }
}