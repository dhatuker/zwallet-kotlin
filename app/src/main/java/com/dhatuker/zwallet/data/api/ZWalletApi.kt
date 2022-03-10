package com.dhatuker.zwallet.data.api

import com.dhatuker.zwallet.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ZWalletApi {

    @POST("auth/login")
    fun login(@Body request: LoginRequest) : Call<ApiResponse<User>>

    @POST("auth/signup")
    fun signup(@Body request: RegisterRequest) : Call<ApiResponse<String>>

//    @GET("user/myProfile")
//    fun getProfile(@Body request: Profile) : Call<ApiResponse<String>>

    @GET("home/getBalance")
    fun getBalance() : Call<ApiResponse<List<BalanceRequest>>>

}