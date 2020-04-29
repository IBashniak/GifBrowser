package com.example.serializable

import android.util.Log

sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}

interface GifRepository {
    // Suspend is used to await the result from Deferred
    suspend fun getGifList(): UseCaseResult<List<Gif>>
}

class GifRepositoryImpl(private val GifApi: GifApi) : GifRepository {
    override suspend fun getGifList(): UseCaseResult<List<Gif>> {
        /*
         We try to return a list of Gifs from the API
         Await the result from web service and then return it
         */
        return try {
            val result = GifApi.getGifsAsync(15).await()
            Log.d("GifRepositoryImpl"," result ${result.size}")
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}