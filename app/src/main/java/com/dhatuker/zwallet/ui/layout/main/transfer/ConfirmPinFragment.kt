package com.dhatuker.zwallet.ui.layout.main.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentConfirmPinBinding
import com.dhatuker.zwallet.model.request.TransferRequest
import com.dhatuker.zwallet.ui.widget.LoadingDialog
import com.dhatuker.zwallet.util.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ConfirmPinFragment : Fragment() {

    private lateinit var binding : FragmentConfirmPinBinding
    private val viewModel : TransferViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmPinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun transferMoney(view: View){

        var receiver : String? = null
        var amount : String? = null
        var notes : String? = null
        var request : TransferRequest ? = null

        val pinText = binding.editText1.text.toString() +
                binding.editText2.text.toString() +
                binding.editText3.text.toString() +
                binding.editText4.text.toString() +
                binding.editText5.text.toString() +
                binding.editText6.text.toString()

        viewModel.getTransferParameter().observe(viewLifecycleOwner) {
            request = it
            binding.apply {
                receiver = "${it.receiver}"
                amount = "${it.amount}"
                notes = "${it.notes}"
            }
        }

        viewModel.transfer(request!!, pinText).observe(viewLifecycleOwner) {
            when (it.state) {
                State.LOADING -> {
                    loadingDialog.start("Processing your request")
                }
                State.SUCCESS -> {
                    loadingDialog.stop()
                    //sukses
                }
                State.ERROR -> {
                    loadingDialog.stop()
                    //gagal
                }
            }
        }
    }

}