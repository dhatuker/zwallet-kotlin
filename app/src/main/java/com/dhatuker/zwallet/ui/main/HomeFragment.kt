package com.dhatuker.zwallet.ui.main

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
import com.dhatuker.zwallet.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val transactionData = mutableListOf<Transaction>()
    lateinit var transactionAdapter: TransactionAdapter
    private lateinit var prefs: SharedPreferences

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
        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

        NetworkConfig(context).getService().getBalance()
            .enqueue(object : Callback<ApiResponse<List<BalanceRequest>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<BalanceRequest>>>,
                    response: Response<ApiResponse<List<BalanceRequest>>>
                ) {
                    val res = response.body()?.data
                    if(response.body()?.status != HttpsURLConnection.HTTP_OK){
                        Toast.makeText(context, "Fail to Get Data", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        binding.userName.text = response.body()!!.data[0].name
                        binding.balanceTotal.text = response.body()!!.data[0].balance.toString()
                        binding.phoneUser.text = response.body()!!.data[0].phone
                    }
                }
                override fun onFailure(
                    call: Call<ApiResponse<List<BalanceRequest>>>,
                    t: Throwable
                ) {
                    Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT)
                }
            })

        binding.recycleTransaction.layoutManager = layoutManager
        binding.recycleTransaction.adapter = transactionAdapter
        prepareData()

        binding.imageUser.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_homeFragment2_to_profileFragment)
            AlertDialog.Builder(context)
                .setTitle("Logout Confirmmation")
                .setMessage("Are u sure?")
                .setPositiveButton("Yes") { _, _ ->
                    with(prefs.edit()) {
                        putBoolean(KEY_LOGGED_IN, false)
                        apply()
                        val intent = Intent(activity, SplashScreenActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
                .setNegativeButton("No") { _, _ ->
                    return@setNegativeButton
                }
                .show()
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
