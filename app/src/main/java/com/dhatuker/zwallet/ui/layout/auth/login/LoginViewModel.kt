package com.dhatuker.zwallet.ui.layout.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dhatuker.zwallet.data.ZWalletDataSource
import com.dhatuker.zwallet.model.ApiResponse
import com.dhatuker.zwallet.model.User
import com.dhatuker.zwallet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var dataSource : ZWalletDataSource) : ViewModel(){

    fun login(email: String, password: String) : LiveData<Resource<ApiResponse<User>>> {
        return dataSource.login(email, password)
    }

    fun setPin(pin: String) : LiveData<Resource<ApiResponse<String>>> {
        return dataSource.setPin(pin)
    }
}