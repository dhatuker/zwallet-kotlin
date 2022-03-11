package com.dhatuker.zwallet.data.api

import com.dhatuker.zwallet.model.*
import com.dhatuker.zwallet.model.request.RefreshTokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ZWalletApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest) : ApiResponse<User>

    @POST("auth/signup")
    fun signup(@Body request: RegisterRequest) : Call<ApiResponse<String>>

//    @GET("user/myProfile")
//    fun getProfile(@Body request: Profile) : Call<ApiResponse<String>>

    @GET("home/getBalance")
    suspend fun getBalance() : ApiResponse<List<BalanceRequest>>

    @POST("auth/refresh-token")
    fun refreshToken(@Body request: RefreshTokenRequest) : Call<ApiResponse<User>>

    @GET("home/getInvoice")
    suspend fun getInvoice() : ApiResponse<List<Invoice>>

}