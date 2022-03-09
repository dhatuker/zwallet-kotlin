package com.dhatuker.zwallet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.data.Transaction
import com.dhatuker.zwallet.adapter.TransactionAdapter
import com.dhatuker.zwallet.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val transactionData = mutableListOf<Transaction>()
    lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.transactionAdapter = TransactionAdapter(transactionData)
        val layoutManager = LinearLayoutManager(context)
        binding.recycleTransaction.layoutManager = layoutManager
        binding.recycleTransaction.adapter = transactionAdapter
        prepareData()

        binding.imageUser.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_homeFragment2_to_profileFragment)
        }
    }

    fun prepareData(){
        this.transactionData.add(Transaction(
            transactionImage = activity?.getDrawable(R.drawable.img)!!,
            transactionName = "Dhatu Kertayuga",
            transactionType = "Transfer",
            transactionNominal = 129399.00
        ))
    }
}