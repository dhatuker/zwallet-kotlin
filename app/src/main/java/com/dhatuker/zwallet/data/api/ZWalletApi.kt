package com.dhatuker.zwallet.data.api

import com.dhatuker.zwallet.model.*
import com.dhatuker.zwallet.model.request.*
import retrofit2.Call
import retrofit2.http.*

interface ZWalletApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest) : ApiResponse<User>

    @POST("auth/signup")
    suspend fun signup(@Body request: RegisterRequest) : ApiResponse<RegisterRequest>

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

    @GET("auth/checkPIN/{pin}")
    suspend fun checkPin(@Path("pin") pin: String) : ApiResponse<String>

    @POST("tranfer/newTranfer")
    suspend fun transfer(@Body transfer: TransferRequest, @Header("x-access-PIN") pin : String) : ApiTransferRequest<Transfer>

    @PATCH("user/changePassword")
    suspend fun  changePassword(@Body changePassword: ChangePassword) : ApiResponse<ChangePassword>

    @GET("auth/activate/{email}/{otp}")
    suspend fun activateOtp(@Path("email") email:String, @Path("otp") otp:String) : ApiResponse<String>

    @PATCH("user/changeInfo")
    suspend fun changePhone(@Body value: ChangePhoneRequest) : ApiResponse<ChangePhoneRequest>

}