package com.dhatuker.zwallet.ui.main.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhatuker.zwallet.data.ZWalletDataSource
import com.dhatuker.zwallet.data.api.ZWalletApi
import com.dhatuker.zwallet.model.ApiResponse
import com.dhatuker.zwallet.model.BalanceRequest
import com.dhatuker.zwallet.model.Invoice
import com.dhatuker.zwallet.network.NetworkConfig

class HomeViewModel(app: Application) :ViewModel(){
    private var apiClient: ZWalletApi = NetworkConfig(app).buildApi()
    private var dataSource = ZWalletDataSource(apiClient)

    fun getInvoice(): LiveData<ApiResponse<List<Invoice>>> {
        return dataSource.getInvoice()
    }

    fun getBalance(): LiveData<ApiResponse<List<BalanceRequest>>>{
        return dataSource.getBalance()
    }
}