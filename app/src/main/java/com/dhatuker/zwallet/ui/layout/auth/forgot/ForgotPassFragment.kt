package com.dhatuker.zwallet.ui.layout.auth.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentForgotPassBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPassFragment : Fragment() {

    private lateinit var binding: FragmentForgotPassBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPassBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_forgot_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnReset.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_forgotPassFragment_to_resetPassword)
        }
    }
}