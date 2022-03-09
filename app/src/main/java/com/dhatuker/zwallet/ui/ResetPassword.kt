package com.dhatuker.zwallet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentForgotPassBinding
import com.dhatuker.zwallet.databinding.FragmentResetPasswordBinding

class ResetPassword : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentResetPasswordBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }
}