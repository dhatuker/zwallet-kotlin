package com.dhatuker.zwallet.network

import android.content.Context
import android.content.SharedPreferences
import com.dhatuker.zwallet.data.api.ZWalletApi
import com.dhatuker.zwallet.model.request.RefreshTokenRequest
import com.dhatuker.zwallet.util.KEY_USER_EMAIL
import com.dhatuker.zwallet.util.KEY_USER_REFRESH_TOKEN
import com.dhatuker.zwallet.util.KEY_USER_TOKEN
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.net.ssl.HttpsURLConnection

class RefreshTokenInterceptor(
    val context: Context?,
    private val client: ZWalletApi,
    private val prefs: SharedPreferences
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val updatedToken = getNewToken()
        print("authenticate updatedToken $updatedToken")
        return if (updatedToken == null)
            null
        else
            response.request.newBuilder()
                .header("Authorization", "Bearer $updatedToken")
                .build()
    }

    private fun getNewToken(): String? {
        val request = RefreshTokenRequest(
            prefs.getString(KEY_USER_EMAIL, "")!!,
            prefs.getString(KEY_USER_REFRESH_TOKEN, "")!!
        )

        val response = client.refreshToken(request).execute().body()
        print("getNewToken response ${response.toString()}")
        return if (response?.status != HttpsURLConnection.HTTP_CREATED)
            null
        else {
            with (prefs.edit()) {
                putString(KEY_USER_TOKEN, response.data?.token)
                apply()
            }

            response.data?.token
        }
    }
}