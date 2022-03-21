package com.dhatuker.zwallet.ui.layout.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentChangePinBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePinFragment : Fragment() {

    private lateinit var binding : FragmentChangePinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueBtn.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_changePinFragment_to_continueChangePinFragment)
        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }
}