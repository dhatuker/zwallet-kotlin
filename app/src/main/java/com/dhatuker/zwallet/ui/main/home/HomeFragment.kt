package com.dhatuker.zwallet.ui.main.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.data.Transaction
import com.dhatuker.zwallet.adapter.TransactionAdapter
import com.dhatuker.zwallet.databinding.FragmentHomeBinding
import com.dhatuker.zwallet.model.ApiResponse
import com.dhatuker.zwallet.model.BalanceRequest
import com.dhatuker.zwallet.model.RegisterRequest
import com.dhatuker.zwallet.model.User
import com.dhatuker.zwallet.network.NetworkConfig
import com.dhatuker.zwallet.ui.SplashScreenActivity
import com.dhatuker.zwallet.ui.main.MainActivity
import com.dhatuker.zwallet.ui.viewModelFactory
import com.dhatuker.zwallet.util.*
import com.dhatuker.zwallet.util.Helper.formatPrice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var transactionAdapter: TransactionAdapter
    private lateinit var prefs: SharedPreferences
    private val viewModel: HomeViewModel by viewModelFactory { HomeViewModel(requireActivity().application) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

        prepareData()

        binding.imageUser.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment2_to_profileFragment)
        }
    }

    fun prepareData() {
        this.transactionAdapter = TransactionAdapter(listOf())

        binding.recycleTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        viewModel.getBalance().observe(viewLifecycleOwner) {
            if (it.status == HttpsURLConnection.HTTP_OK) {
                binding.apply {
                    balanceTotal.formatPrice(it.data?.get(0)?.balance.toString())
                    phoneUser.text = it.data?.get(0)?.phone
                    userName.text = it.data?.get(0)?.name
                }
            } else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }

            viewModel.getInvoice().observe(viewLifecycleOwner) {
                if (it.status == HttpsURLConnection.HTTP_OK) {
                    this.transactionAdapter.apply {
                        addData(it.data!!)
                        notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}
