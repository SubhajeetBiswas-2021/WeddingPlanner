package com.subhajeet.weddingplanner.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subhajeet.weddingplanner.Usermodel.User
import com.subhajeet.weddingplanner.Utils.ResultState
import com.subhajeet.weddingplanner.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val repo: UserRepository) : ViewModel()  {


    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()



    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.loginUser(username, password).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _loginState.value = LoginState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _loginState.value = LoginState(success = result.data) // result.data = User
                    }
                    is ResultState.Error -> {
                        _loginState.value = LoginState(error = result.message)
                    }
                }
            }
        }
    }




    fun register(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newUser = User(username = username, password = password)
            repo.registerUser(newUser).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _registerState.value = RegisterState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _registerState.value = RegisterState(success = result.data) // true if success
                    }
                    is ResultState.Error -> {
                        _registerState.value = RegisterState(error = result.message)
                    }
                }
            }
        }
    }

}


data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: User? = null
)

data class RegisterState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean? = null
)