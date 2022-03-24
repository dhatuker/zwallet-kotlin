package com.dhatuker.zwallet.ui.layout.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentManagePhoneBinding
import com.dhatuker.zwallet.ui.layout.main.home.HomeViewModel
import com.dhatuker.zwallet.util.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ManagePhoneFragment : Fragment() {

    private lateinit var binding : FragmentManagePhoneBinding
    private val viewModel : HomeViewModel by activityViewModels()
    private lateinit var firstname : String
    private lateinit var lastname : String
    private lateinit var email : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentManagePhoneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        viewModel.getProfile().observe(viewLifecycleOwner) {
            when (it.state) {
                State.LOADING -> {
                }
                State.SUCCESS -> {
                    if (it.data?.status == HttpsURLConnection.HTTP_OK) {
                        firstname = it.data?.data?.firstname.toString()
                        lastname = it.data?.data?.lastname.toString()
                        email = it.data?.data?.email.toString()

                        binding.apply {
                            inputPhone.setText(it.data?.data?.phone.toString())
                        }

                    }
                }
                State.ERROR -> {
                }
            }
        }

        binding.confirmBtn.setOnClickListener {

//            val username = (firstname + lastname).lowercase()
            val username = lastname.lowercase()

            var phone = binding.inputPhone.text.toString()
            viewModel.changePhone(phone).observe(viewLifecycleOwner) {
                when(it.state){
                    State.LOADING->{}
                    State.SUCCESS->{
                        Toast.makeText(activity, "Your phone has been changed.", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(view).popBackStack()
                    }
                    State.ERROR->{}
                }
            }
        }

    }

}