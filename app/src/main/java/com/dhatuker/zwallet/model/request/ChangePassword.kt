package com.dhatuker.zwallet.model.request

data class ChangePassword(
    val old_password : String,
    val new_password : String
)
