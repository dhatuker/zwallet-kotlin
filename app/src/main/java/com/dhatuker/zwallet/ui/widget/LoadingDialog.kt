package com.dhatuker.zwallet.ui.widget

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.dhatuker.zwallet.databinding.LoadingDialogBinding

class LoadingDialog(activity : Activity) {
    private val alertDialog: AlertDialog
    private val binding: LoadingDialogBinding

    init {
        val builder = AlertDialog.Builder(activity)
        binding = LoadingDialogBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)
        alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun start(title: String){
        binding.textLoadingDesc.text = title
        alertDialog.show()
    }

    fun stop(){
        alertDialog.cancel()
    }
}