package com.dhatuker.zwallet.ui.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dhatuker.zwallet.data.ZWalletDataSource
import com.dhatuker.zwallet.data.api.ZWalletApi
import com.dhatuker.zwallet.model.ApiResponse
import com.dhatuker.zwallet.model.User
import com.dhatuker.zwallet.network.NetworkConfig
import com.dhatuker.zwallet.util.Resource

class LoginViewModel(app: Application): AndroidViewModel(app) {
    private var apiClient: ZWalletApi = NetworkConfig(app).buildApi()
    private var dataSource = ZWalletDataSource(apiClient)

    fun login(email: String, password: String) : LiveData<Resource<ApiResponse<User>>> {
        return dataSource.login(email, password)
    }
}