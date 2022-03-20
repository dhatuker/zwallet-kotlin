package com.dhatuker.zwallet.model


import com.google.gson.annotations.SerializedName

data class ApiTransferRequest<T>(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("receiver")
    val `receiver`: String?
)