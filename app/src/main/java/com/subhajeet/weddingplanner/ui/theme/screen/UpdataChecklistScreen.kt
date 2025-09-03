package com.subhajeet.weddingplanner.ui.theme.screen

import android.annotation.SuppressLint
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
import androidx.navigation.NavController
import com.subhajeet.weddingplanner.viewModel.MyViewModel


@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun UpdataChecklistScreen(
    navController: NavController,
    title: String,
    viewModel: MyViewModel = hiltViewModel(),
    id: Int,
    isCompleted: Boolean
) {
    val titleState = remember { mutableStateOf(title) }

    val idState  = remember { mutableStateOf(id) }

    val isCompletedState = remember { mutableStateOf(isCompleted) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = titleState.value,
            onValueChange = {
                titleState.value = it
            },
            label = { Text("New Title") }
        )

        Spacer(modifier = Modifier.height(30.dp))

        ElevatedButton(
            onClick = {
                viewModel.updateTaskById(

                    newTitle = titleState.value,
                    taskId = idState.value,        //   order according to viewModel
                    isCompleted = isCompletedState.value
                )

               navController.popBackStack() //  this is important to Goback
            }
        ) {
            Text(text = "Update Task")
        }

        Spacer(modifier = Modifier.height(16.dp))


        ElevatedButton(
            onClick = {
                viewModel.deleteTaskById(idState.value)
                navController.popBackStack()
            }
        ) {
            Text(text = "Delete Task")
        }




    }
}