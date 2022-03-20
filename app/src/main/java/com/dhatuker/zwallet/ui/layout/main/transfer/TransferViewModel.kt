package com.dhatuker.zwallet.ui.layout.main.transfer

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhatuker.zwallet.data.ZWalletDataSource
import com.dhatuker.zwallet.data.api.ZWalletApi
import com.dhatuker.zwallet.model.*
import com.dhatuker.zwallet.model.request.TransferRequest
import com.dhatuker.zwallet.network.NetworkConfig
import com.dhatuker.zwallet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private var dataSource : ZWalletDataSource) : ViewModel() {

    private var selectedContact = MutableLiveData<Contact>()
    private var transfer = MutableLiveData<TransferRequest>()

    fun getBalance(): LiveData<Resource<ApiResponse<List<Balance>>>> {
        return dataSource.getBalance()
    }

    fun getContact(): LiveData<Resource<ApiResponse<List<Contact>>>> {
        return dataSource.getContact()
    }

    fun setSelectedContact(value : Contact){
        selectedContact.value = value
    }

    fun getSelectedContact() : MutableLiveData<Contact> {
        return selectedContact
    }

    fun setTransferParameter(data : TransferRequest) {
        transfer.value = data
    }

    fun getTransferParameter() : MutableLiveData<TransferRequest>{
        return transfer
    }

    fun transfer(data : TransferRequest, pin: String): LiveData<Resource<ApiTransferRequest<Transfer>>> {
        return dataSource.transfer(data, pin)
    }


}