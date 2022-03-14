package com.dhatuker.zwallet.data

import androidx.lifecycle.liveData
import com.dhatuker.zwallet.data.api.ZWalletApi
import com.dhatuker.zwallet.model.*
import com.dhatuker.zwallet.util.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ZWalletDataSource(private val apiClient: ZWalletApi) {
    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try{
            val loginRequest = LoginRequest(email,password)
            val response = apiClient.login(loginRequest)
            emit(Resource.success(response))
        } catch (e: Exception){
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun getInvoice() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.getInvoice()
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun getBalance() = liveData {
        emit(Resource.loading(null))
        try {
            val response = apiClient.getBalance()
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }
}