package com.example.materialhass.Services

import com.example.materialhass.API.HomeAssistantAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {

        @Volatile
        private var instance: HomeAssistantAPI? = null
        private val URL: String = "https://pavlovskhome.ru/"

        fun getAPI(): HomeAssistantAPI = instance ?: synchronized(this) {
                instance ?: generateAPIBuilder().create(HomeAssistantAPI::class.java).also { instance = it }
        }
        private fun generateAPIBuilder(): Retrofit {

            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }




    }
}