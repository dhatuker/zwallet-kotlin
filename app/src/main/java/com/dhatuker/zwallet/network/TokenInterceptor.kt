package com.dhatuker.zwallet.network

import okhttp3.Interceptor
import okhttp3.Response
import android.content.Context
import com.dhatuker.zwallet.util.KEY_USER_TOKEN
import com.dhatuker.zwallet.util.PREFS_NAME

class TokenInterceptor(val context: Context?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val pref = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val token = pref?.getString(KEY_USER_TOKEN, "")

        if (!token.isNullOrEmpty()) {
            request.header("Authorization", "Bearer $token")
        }

        return chain.proceed(request.build())
    }
}