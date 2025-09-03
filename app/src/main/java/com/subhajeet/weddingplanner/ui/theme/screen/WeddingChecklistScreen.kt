package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

        Column(modifier = Modifier.padding(35.dp)) {
            Text(
                "Wedding Checklist",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
            LazyColumn {

                items(tasks) { task ->
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
           /* Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newTask,
                onValueChange = { newTask = it },
                label = { Text("New Task") }
            )
            Button(onClick = {
                if (newTask.isNotBlank()) {
                    viewModel.addTask(newTask)
                    newTask = ""
                }
            }) {
                Text("Add Task")
            }*/
        }
        LaunchedEffect(tasks) {
            println("📋 Tasks updated: ${tasks.map { it.title }}")
        }

    }
}