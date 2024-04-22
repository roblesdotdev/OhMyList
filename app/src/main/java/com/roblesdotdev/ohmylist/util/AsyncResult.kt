package com.roblesdotdev.ohmylist.util

/**
 * A generic class that holds a loading signal or the result of an async operation.
 */
sealed class AsyncResult<out T> {
    data object Loading : AsyncResult<Nothing>()

    data class Error(val errorMessage: String) : AsyncResult<Nothing>()

    data class Success<out T>(val data: T) : AsyncResult<T>()
}
