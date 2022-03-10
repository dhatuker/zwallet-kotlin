package com.dhatuker.zwallet.model

data class BalanceRequest(
    val id: Int,
    val name: String,
    val phone: String,
    val balance: Int,
    val image: String,
)
