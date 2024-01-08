package com.example.materialhass.Services

import android.util.Log
import com.example.materialhass.API.HomeAssistantAPI
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIService {
    companion object {

        @Volatile
        private var instance: HomeAssistantAPI? = null
        var URL: String = CredentialService().getURL()
        var token: String = CredentialService().getToken()
        fun getAPI(): HomeAssistantAPI = instance ?: synchronized(this) {
                instance ?: generateAPIBuilder().create(HomeAssistantAPI::class.java).also { instance = it }
        }

        private fun generateAPIBuilder(): Retrofit {
            Log.e("URL", URL)
            Log.e("TOKEN", token)
                val gson = GsonBuilder().setLenient().create()
                return Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(createOkHttpClient(token))
                    .build()
        }
        private fun createOkHttpClient(token: String): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(request)
                }
                .build()
        }
    }
}