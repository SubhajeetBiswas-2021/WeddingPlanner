package com.subhajeet.weddingplanner.repo

import com.subhajeet.weddingplanner.Utils.ResultState
import com.subhajeet.weddingplanner.GuestModel.Guest
import com.subhajeet.weddingplanner.GuestModel.GuestDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GuestRepository @Inject constructor(
    private val guestDao: GuestDao
) {
    fun getAllGuests(): Flow<ResultState<List<Guest>>> = flow {
        emit(ResultState.Loading)
        try {
            guestDao.getAllGuests().collect { list ->
                emit(ResultState.Success(list))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Error loading guests"))
        }
    }

    suspend fun insertGuest(guest: Guest): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)
        try {
            guestDao.insertGuest(guest)
            emit(ResultState.Success(true))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Insert error"))
        }
    }

    suspend fun updateGuest(guest: Guest): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)
        try {
            guestDao.updateGuest(guest)
            emit(ResultState.Success(true))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Update error"))
        }
    }

    suspend fun deleteGuest(guest: Guest): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)
        try {
            guestDao.deleteGuest(guest)
            emit(ResultState.Success(true))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Delete error"))
        }
    }
}