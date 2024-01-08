package com.example.materialhass.Services

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.materialhass.SharedPreference
import com.example.materialhass.viewmodel.RoomsViewmodel
import kotlinx.coroutines.launch

class CredentialService {
    companion object {
        lateinit var ctx: Context // This should be initialized before usage
    }
    fun getURL(): String
    {
        val sharedPreference = SharedPreference(ctx)
        return sharedPreference.GetString("ServerUrl")!!
    }

    fun getToken(): String
    {
        val sharedPreference = SharedPreference(ctx)
        return sharedPreference.GetString("APIkey")!!
    }
}