package com.dhatuker.zwallet.ui.layout.main.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentInputAmountBinding
import com.dhatuker.zwallet.model.request.TransferRequest
import com.dhatuker.zwallet.util.BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputAmountFragment : Fragment() {

    private lateinit var binding : FragmentInputAmountBinding
    private val viewModel: TransferViewModel by activityViewModels()
    var id :String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputAmountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSelectedContact().observe(viewLifecycleOwner) {
            id = it?.id.toString()
            binding.apply {
                nameContact.text = "${it?.name}"
                numberContact.text = "${it?.phone}"
                Glide.with(imageContact)
                    .load(BASE_URL + it?.image)
                    .apply(
                        RequestOptions.circleCropTransform()
                            .placeholder(R.drawable.ic_baseline_person_24)
                    )
                    .into(imageContact)
            }
        }

        binding.continueBtn.setOnClickListener{
            viewModel.setTransferParameter(TransferRequest(
                id!!,
                binding.inputAmount.text.toString().toInt(),
                binding.noteInput.text.toString()
            ))
            Navigation.findNavController(view).navigate(R.id.action_inputAmountFragment_to_confirmationFragment)
        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }
}