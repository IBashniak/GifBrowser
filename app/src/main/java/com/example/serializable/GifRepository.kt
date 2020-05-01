package com.example.serializable

import android.util.Log
import com.example.serializable.data.network.dto.Gif

sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}

interface GifRepository {
    // Suspend is used to await the result from Deferred
    suspend fun getGifList(): UseCaseResult<List<Gif>>
}

class GifRepositoryImpl(private val GifApi: GifApi) : GifRepository {

    private val TAG= "GifRepositoryImpl"
    companion object{
        private var gifs: List<Gif> = listOf()
    }

    override suspend fun getGifList(): UseCaseResult<List<Gif>> {
        Log.d("GifRepositoryImpl", " result ${gifs.size}")
        if (!gifs.isEmpty())
         {
             Log.d(TAG,"gifs.!isEmpty()")
             return UseCaseResult.Success(gifs)
         }

        /*
         We try to return a list of Gifs from the API
         Await the result from web service and then return it
         */
        return try {
            gifs = GifApi.getGifsAsync(15).await()
            Log.d("GifRepositoryImpl", " result ${gifs.size}")
            UseCaseResult.Success(gifs)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}