package com.dhatuker.zwallet.ui.layout.main.transfer

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhatuker.zwallet.data.ZWalletDataSource
import com.dhatuker.zwallet.data.api.ZWalletApi
import com.dhatuker.zwallet.model.ApiResponse
import com.dhatuker.zwallet.model.Balance
import com.dhatuker.zwallet.model.Contact
import com.dhatuker.zwallet.network.NetworkConfig
import com.dhatuker.zwallet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private var dataSource : ZWalletDataSource) : ViewModel() {

    fun getBalance(): LiveData<Resource<ApiResponse<List<Balance>>>> {
        return dataSource.getBalance()
    }

    fun getContact(): LiveData<Resource<ApiResponse<List<Contact>>>> {
        return dataSource.getContact()
    }


}