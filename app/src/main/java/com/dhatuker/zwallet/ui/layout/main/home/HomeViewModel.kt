package com.dhatuker.zwallet.ui.layout.main.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dhatuker.zwallet.data.ZWalletDataSource
import com.dhatuker.zwallet.data.api.ZWalletApi
import com.dhatuker.zwallet.model.ApiResponse
import com.dhatuker.zwallet.model.Balance
import com.dhatuker.zwallet.model.Invoice
import com.dhatuker.zwallet.model.Profile
import com.dhatuker.zwallet.model.request.ChangePassword
import com.dhatuker.zwallet.network.NetworkConfig
import com.dhatuker.zwallet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var dataSource: ZWalletDataSource) :ViewModel(){

    fun getInvoice(): LiveData<Resource<ApiResponse<List<Invoice>>>> {
        return dataSource.getInvoice()
    }

    fun getBalance(): LiveData<Resource<ApiResponse<List<Balance>>>> {
        return dataSource.getBalance()
    }

    fun getProfile(): LiveData<Resource<ApiResponse<Profile>>> {
        return dataSource.getProfile()
    }

    fun changePassword(old : String, new : String): LiveData<Resource<ApiResponse<ChangePassword>>> {
        return dataSource.changePassword(old, new)
    }

    fun setPin(pin: String) : LiveData<Resource<ApiResponse<String>>> {
        return dataSource.setPin(pin)
    }

    fun checkPin(pin: String): LiveData<Resource<ApiResponse<String>>> {
        return dataSource.checkPin(pin)
    }


}