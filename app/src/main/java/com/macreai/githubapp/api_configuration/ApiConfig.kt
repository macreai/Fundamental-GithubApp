package com.macreai.githubapp.api_configuration

import com.macreai.githubapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            //Memberikan info log
            val loggingInterceptor = if (BuildConfig.DEBUG){
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            //Logging dengan OkHttp
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            //menyambungkan url api dengan retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            //build retrofit dengan interface ApiService
            return retrofit.create(ApiService::class.java)
        }
    }
}