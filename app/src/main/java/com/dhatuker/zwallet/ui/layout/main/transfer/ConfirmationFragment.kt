package com.dhatuker.zwallet.ui.layout.main.transfer

import android.os.Build
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
import com.dhatuker.zwallet.databinding.FragmentConfirmationBinding
import com.dhatuker.zwallet.util.BASE_URL
import com.dhatuker.zwallet.util.Helper.formatPrice
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class ConfirmationFragment : Fragment() {

    private lateinit var binding :FragmentConfirmationBinding
    private val viewModel : TransferViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSelectedContact().observe(viewLifecycleOwner) {
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

        viewModel.getTransferParameter().observe(viewLifecycleOwner) {
            binding.totalAmountText.formatPrice(it.amount.toString())

            if(it.notes.isNullOrEmpty()){
                binding.notesTxt.text = "-"
            } else {
                binding.notesTxt.text = it.notes
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val current = LocalDateTime.now()
                val formatter1 = DateTimeFormatter.ofPattern("MMM DD, yyyy")
                val formatter2 = DateTimeFormatter.ofPattern("HH:mma")
                binding.dateText.text = current.format(formatter1)
                binding.timeText.text = current.format(formatter2)
            } else {
                val current = Date()
                val formatter1 = SimpleDateFormat("MMM DD, yyyy")
                val formatter2 = SimpleDateFormat("HH:mma")
                binding.dateText.text = formatter1.format(current)
                binding.timeText.text = formatter2.format(current)
            }
        }

        viewModel.getBalance().observe(viewLifecycleOwner) {
            binding.balanceLeftText.text = (it.data?.data?.get(0)?.balance.toString())
        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.continueBtn.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_confirmationFragment_to_confirmPinFragment)
        }

    }

}