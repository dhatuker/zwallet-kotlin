package com.dhatuker.zwallet.network

import android.content.Context
import com.dhatuker.zwallet.data.api.ZWalletApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.dhatuker.zwallet.util.BASE_URL
import com.dhatuker.zwallet.util.KEY_USER_TOKEN
import com.dhatuker.zwallet.util.PREFS_NAME
import okhttp3.Authenticator

class NetworkConfig(val context: Context?) {
    private val preferences = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun getInterceptor(authenticator: Authenticator? = null): OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val token = prefs?.getString(KEY_USER_TOKEN, "")
        val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

        if (!token.isNullOrEmpty()){
            client.addInterceptor(TokenInterceptor(token))
        }
        if (authenticator != null){
            client.authenticator(authenticator)
        }

        return client.build()
    }

    fun getService() :ZWalletApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build()
            .create(ZWalletApi::class.java)
    }

    fun buildApi(): ZWalletApi {
        val authenticator = RefreshTokenInterceptor(context, getService(), preferences!!)
        println("authenticator: ${authenticator.context}")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getInterceptor(authenticator))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ZWalletApi::class.java)
    }



}