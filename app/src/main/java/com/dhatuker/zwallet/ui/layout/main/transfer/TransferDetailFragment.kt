package com.dhatuker.zwallet.ui.layout.main.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentTrasnferDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferDetailFragment : Fragment() {

    private lateinit var binding : FragmentTrasnferDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrasnferDetailBinding.inflate(layoutInflater)
        return binding.root
    }

}