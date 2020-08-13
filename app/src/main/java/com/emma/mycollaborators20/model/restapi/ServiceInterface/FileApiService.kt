package com.emma.mycollaborators20.model.restapi.ServiceInterface

import android.util.Log
import com.emma.mycollaborators20.model.restapi.response.FileWebServiceResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
//url https://dl.dropboxusercontent.com/s/5u21281sca8gj94/ getFile.json?dl=0
const val  baseUrl = "https://dl.dropboxusercontent.com/s/5u21281sca8gj94/"
interface FileApiService {
    @GET("getFile.json?dl=0")
    fun getResponse(
    ) : Deferred<FileWebServiceResponse>
    companion object{
        operator fun invoke() : FileApiService{
            val requestInterceptor = Interceptor{chain ->
                var url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                Log.i("FileApiService", url.toString())
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FileApiService::class.java)
        }
    }

}