package com.dhatuker.zwallet.model

data class ApiResponse<T>(
    var status: Int,
    var message: String,
    var data: T?
)
