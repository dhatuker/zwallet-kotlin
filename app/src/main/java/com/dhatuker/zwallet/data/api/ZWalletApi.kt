package com.dhatuker.zwallet.data.api

import com.dhatuker.zwallet.model.*
import com.dhatuker.zwallet.model.request.LoginRequest
import com.dhatuker.zwallet.model.request.PinRequest
import com.dhatuker.zwallet.model.request.RefreshTokenRequest
import com.dhatuker.zwallet.model.request.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ZWalletApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest) : ApiResponse<User>

    @POST("auth/signup")
    fun signup(@Body request: RegisterRequest) : Call<ApiResponse<String>>

//    @GET("user/myProfile")
//    fun getProfile(@Body request: Profile) : Call<ApiResponse<String>>

    @GET("home/getBalance")
    suspend fun getBalance() : ApiResponse<List<Balance>>

    @POST("auth/refresh-token")
    fun refreshToken(@Body request: RefreshTokenRequest) : Call<ApiResponse<User>>

    @GET("home/getInvoice")
    suspend fun getInvoice() : ApiResponse<List<Invoice>>

    @GET("user/myProfile")
    suspend fun getProfile() : ApiResponse<Profile>

    @GET("tranfer/contactUser")
    suspend fun getContact() : ApiResponse<List<Contact>>

    @PATCH("auth/PIN")
    suspend fun setPin(@Body request: PinRequest) : ApiResponse<String>

}