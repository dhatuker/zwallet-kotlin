package com.dhatuker.zwallet.ui.layout.main.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentInputAmountBinding

class InputAmountFragment : Fragment() {

    private lateinit var binding : FragmentInputAmountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputAmountBinding.inflate(layoutInflater)
        return binding.root
    }
}