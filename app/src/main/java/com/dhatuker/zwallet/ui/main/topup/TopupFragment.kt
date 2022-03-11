package com.dhatuker.zwallet.ui.main.topup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentTopupBinding

class TopupFragment : Fragment() {

    private lateinit var binding: FragmentTopupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTopupBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

}