package com.example.animeflix.data.datasource


// out and nothing keyword used here , interview ques
sealed class ApiResult< out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val error: Throwable) : ApiResult<T>()
    data object Loading : ApiResult<Nothing>()
}

suspend fun <T> getResult(suspend: suspend () -> T): ApiResult<T> {
    return try {
        val response = suspend()
        ApiResult.Success(response)
    } catch (e: Exception) {
        e.printStackTrace()
        ApiResult.Error(e)
    }
}