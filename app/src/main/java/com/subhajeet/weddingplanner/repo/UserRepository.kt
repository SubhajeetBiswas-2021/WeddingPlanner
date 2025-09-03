package com.subhajeet.weddingplanner.repo

import com.subhajeet.weddingplanner.Usermodel.User
import com.subhajeet.weddingplanner.Usermodel.UserDao
import com.subhajeet.weddingplanner.Utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepository  @Inject constructor(private val userDao: UserDao) {

    suspend fun registerUser(user: User): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)
        try {
            val existing = userDao.getUserByUsername(user.username)
            if (existing == null) {
                userDao.insertUser(user)
                emit(ResultState.Success(true))
            } else {
                emit(ResultState.Error("User already exists"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Database error"))
        }
    }

    suspend fun loginUser(username: String, password: String): Flow<ResultState<User>> = flow {
        emit(ResultState.Loading)
        try {
            val user = userDao.loginUser(username, password)
            if (user != null) {
                emit(ResultState.Success(user))
            } else {
                emit(ResultState.Error("Invalid username or password"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Database error"))
        }
    }

}