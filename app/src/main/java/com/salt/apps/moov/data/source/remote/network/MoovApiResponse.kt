package com.salt.apps.moov.data.source.remote.network

sealed class MoovApiResponse<out R> {
    data class Success<out T>(val data: T) : MoovApiResponse<T>()
    data class Error(val errorMessage: String) : MoovApiResponse<Nothing>()
    object Empty : MoovApiResponse<Nothing>()
}