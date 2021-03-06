package com.dhatuker.zwallet.data

import androidx.lifecycle.liveData
import com.dhatuker.zwallet.data.api.ZWalletApi
import com.dhatuker.zwallet.model.request.*
import com.dhatuker.zwallet.util.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

class ZWalletDataSource @Inject constructor(private val apiClient: ZWalletApi) {
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

    fun signup(username:String,email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try{
            val value = RegisterRequest(username, email, password)
            val response = apiClient.signup(value)
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

    fun getProfile() = liveData {
        emit(Resource.loading(null))
        try {
            val response = apiClient.getProfile()
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun getContact() = liveData {
        emit(Resource.loading(null))
        try {
            val response = apiClient.getContact()
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun setPin(pin:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val value = PinRequest(pin)
            val response = apiClient.setPin(value)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun checkPin(pin:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.checkPin(pin)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun transfer(data : TransferRequest, pin: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.transfer(data, pin)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun changePassword(old: String, new : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val value = ChangePassword(old, new)
            val response = apiClient.changePassword(value)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun activateOtp(email: String, otp:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.activateOtp(email, otp)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun changePhone(phone : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val value = ChangePhoneRequest(phone)
            val response = apiClient.changePhone(value)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }
}