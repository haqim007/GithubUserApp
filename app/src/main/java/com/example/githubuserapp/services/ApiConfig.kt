package com.example.githubuserapp.services

import com.example.githubuserapp.helpers.data.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService{
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain
                        .request().newBuilder()
                        .addHeader("Accept", Constants.HEADER_ACCEPT)
                        .build()
                    chain.proceed(request)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}