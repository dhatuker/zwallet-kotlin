package com.dhatuker.zwallet.data

import androidx.lifecycle.liveData
import com.dhatuker.zwallet.data.api.ZWalletApi
import com.dhatuker.zwallet.model.*
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ZWalletDataSource(private val apiClient: ZWalletApi) {
    fun login(email: String, password: String) = liveData<ApiResponse<User>>(Dispatchers.IO) {
        try{
            val loginRequest = LoginRequest(email,password)
            val response = apiClient.login(loginRequest)
            emit(response)
        } catch (e: Exception){
            emit(ApiResponse(400, e.localizedMessage, null))
        }
    }

    fun getInvoice() = liveData<ApiResponse<List<Invoice>>>(Dispatchers.IO) {
        try {
            val response = apiClient.getInvoice()
            emit(response)
        } catch (e: Exception) {
            emit(ApiResponse(400, e.localizedMessage, null))
        }
    }

    fun getBalance() = liveData<ApiResponse<List<BalanceRequest>>> {
        try {
            val response = apiClient.getBalance()
            emit(response)
        } catch (e: Exception) {
            emit(ApiResponse(400, e.localizedMessage, null))
        }
    }
}