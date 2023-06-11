package com.uppermoon.touristaapp.data

sealed class DestinationResult<out R> private constructor(){
    data class Success<out T>(val data: T): DestinationResult<T>()
    data class Error(val error: String) : DestinationResult<Nothing>()
    object Loading: DestinationResult<Nothing>()
}