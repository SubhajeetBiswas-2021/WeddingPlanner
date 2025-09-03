package com.subhajeet.weddingplanner.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.subhajeet.weddingplanner.R
import com.subhajeet.weddingplanner.ui.theme.screen.nav.Routes
import com.subhajeet.weddingplanner.viewModel.MyViewModel
import com.subhajeet.weddingplanner.viewModel.UserViewModel

@Composable
fun SignUpScreen(viewModel: UserViewModel = hiltViewModel(), navController: NavHostController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel.registerState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(35.dp),
        verticalArrangement = Arrangement.Center, // 👈 center everything vertically
        horizontalAlignment = Alignment.CenterHorizontally) {

        AnimationState()

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            },
            label={ Text(text = "UserName") },
            modifier=Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label={ Text(text = "Password") },
            modifier=Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { /*Handle login action*/
                /* viewModel?.login(email =email.value,
                     password = password.value)*/
                viewModel.register(username, password)},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text="SignUp")
        }

        when {
            state.isLoading -> Text("Loading...")
            state.success == true -> {
                Text("Registered successfully!")
                navController.navigate(Routes.LoginScreenRoute)
            }
            state.error != null -> Text("Error: ${state.error}")
        }
    }

}


@Composable
fun AnimationState() {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.weddingoutline))

    val progress by animateLottieCompositionAsState(
        isPlaying = true,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.9f
    )

    //Display the animation

    Box(modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp), contentAlignment = Alignment.TopCenter){
        LottieAnimation(composition=composition,
            progress= {progress})
    }

}