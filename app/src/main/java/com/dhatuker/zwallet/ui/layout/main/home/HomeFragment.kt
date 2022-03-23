package com.dhatuker.zwallet.ui.layout.main.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.ui.adapter.TransactionAdapter
import com.dhatuker.zwallet.databinding.FragmentHomeBinding
import com.dhatuker.zwallet.ui.layout.SplashScreenActivity
import com.dhatuker.zwallet.util.*
import com.dhatuker.zwallet.util.Helper.formatPrice
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var transactionAdapter: TransactionAdapter
    private lateinit var prefs: SharedPreferences
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        binding.buttonTopup.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment2_to_topupFragment)
        }

        binding.buttonTransfer.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment2_to_searchReceiverFragment)
        }
    }

    fun prepareData() {
        this.transactionAdapter = TransactionAdapter(listOf())

        binding.recycleTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        viewModel.getBalance().observe(viewLifecycleOwner) {
            when (it.state) {
                State.LOADING -> {
                }
                State.SUCCESS -> {
                    if (it.data?.status == HttpsURLConnection.HTTP_OK) {
                        binding.apply {
                            balanceTotal.formatPrice(it.data.data?.get(0)?.balance.toString())
                            phoneUser.text = it.data.data?.get(0)?.phone
                            userName.text = it.data.data?.get(0)?.name
                        }
                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("Your Session is Expired")
                            .setMessage("Please login again.")
                            .setPositiveButton("OK") { _, _ ->
                                with(prefs.edit()) {
                                    putBoolean(KEY_LOGGED_IN, false)
                                    apply()
                                }
                                val intent = Intent(activity, SplashScreenActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }
                            .show()
                    }
                }
                State.ERROR -> {
                }
            }


            viewModel.getInvoice().observe(viewLifecycleOwner) {
                when (it.state) {
                    State.LOADING -> {
                        binding.apply {
                            loadingIndicator.visibility = View.VISIBLE
                            recycleTransaction.visibility = View.GONE
                        }
                    }
                    State.SUCCESS -> {
                        if (it.data?.status == HttpsURLConnection.HTTP_OK) {
                            binding.apply {
                                loadingIndicator.visibility = View.GONE
                                recycleTransaction.visibility = View.VISIBLE
                            }
                            this.transactionAdapter.apply {
                                addData(it.data?.data!!)
                                notifyDataSetChanged()
                            }
                        } else {
                            with(prefs.edit()) {
                                putBoolean(KEY_LOGGED_IN, false)
                                apply()
                            }
                        }
                    }
                    else -> {
                        binding.apply {
                            loadingIndicator.visibility = View.GONE
                            recycleTransaction.visibility = View.VISIBLE
                        }
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
