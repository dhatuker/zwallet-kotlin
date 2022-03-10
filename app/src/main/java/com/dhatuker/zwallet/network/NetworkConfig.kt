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

class NetworkConfig(val context: Context?) {
    fun getInterceptor(): OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val token = prefs?.getString(KEY_USER_TOKEN, "")
        val client = OkHttpClient.Builder().addInterceptor(logging)

        if (!token.isNullOrEmpty()){
            client.addInterceptor(TokenInterceptor(context))
        }

        return client.build()
    }

    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() :ZWalletApi{
        return getRetrofit().create(ZWalletApi::class.java)
    }



}