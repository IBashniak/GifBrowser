package com.example.serializable

import android.util.Log
import com.example.serializable.data.network.dto.GifDTO

sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}

interface GifRepository {
    // Suspend is used to await the result from Deferred
    suspend fun getGifList(search : String): UseCaseResult<List<GifDTO>>
}

class GifRepositoryImpl(private val GifApi: GifApi) : GifRepository {

    private val TAG= "GifRepositoryImpl"
    companion object{
        private var gifDTOs: List<GifDTO> = listOf()
        private var lastRequest=""
    }

    override suspend fun getGifList(search : String): UseCaseResult<List<GifDTO>> {
        Log.d("GifRepositoryImpl", " result ${gifDTOs.size}  lastRequest= $lastRequest,  search= $search ")
        if (gifDTOs.isNotEmpty() && lastRequest == search)
         {
             Log.d(TAG,"gifs.isNotEmpty()")
             lastRequest = search
             return UseCaseResult.Success(gifDTOs)
         }

        /*
         We try to return a list of Gifs from the API
         Await the result from web service and then return it
         */
        return try {
            gifDTOs = GifApi.getGifsAsync(search,55).await()
            Log.d("GifRepositoryImpl", " result ${gifDTOs.size}")
            UseCaseResult.Success(gifDTOs)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}