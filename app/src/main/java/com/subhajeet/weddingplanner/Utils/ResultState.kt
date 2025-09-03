package com.subhajeet.weddingplanner.Utils

sealed class ResultState<out T> {

    //sealed class stores a single type of data
    data class Success<out T>(val data:T):ResultState<T>()   //it will hold the data of success

    data class Error<T>(val message: String):ResultState<T>()

    object Loading:ResultState<Nothing>()


}