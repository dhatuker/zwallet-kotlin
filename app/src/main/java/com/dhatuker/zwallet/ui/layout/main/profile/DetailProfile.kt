package com.dhatuker.zwallet.ui.layout.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.dhatuker.zwallet.databinding.FragmentDetailProfileBinding
import com.dhatuker.zwallet.ui.layout.main.home.HomeViewModel
import com.dhatuker.zwallet.ui.viewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class DetailProfile : Fragment() {

    private lateinit var binding: FragmentDetailProfileBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile().observe(viewLifecycleOwner) {
            if (it.data?.status == HttpsURLConnection.HTTP_OK) {
                binding.apply {
                    firstNameTxt.text = it.data?.data?.firstname
                    lastNameTxt.text = it.data?.data?.lastname
                    emailTxt.text = it.data?.data?.email
                    phoneTxt.text = it.data?.data?.phone.toString()
                }
            } else {
                Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

    }

}