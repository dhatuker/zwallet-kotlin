package com.dhatuker.zwallet.network

import okhttp3.Interceptor
import okhttp3.Response
import android.content.Context
import com.dhatuker.zwallet.util.KEY_USER_TOKEN
import com.dhatuker.zwallet.util.PREFS_NAME

class TokenInterceptor(val token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            request.header("Authorization", "Bearer $token")
        }

        return chain.proceed(request.build())
    }
}