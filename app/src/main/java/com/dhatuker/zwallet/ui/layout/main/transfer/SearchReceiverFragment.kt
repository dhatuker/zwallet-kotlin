package com.dhatuker.zwallet.ui.layout.main.transfer

import android.content.Context
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
import com.dhatuker.zwallet.ui.adapter.ContactAdapter
import com.dhatuker.zwallet.databinding.FragmentSearchReceiverBinding
import com.dhatuker.zwallet.ui.viewModelFactory
import com.dhatuker.zwallet.util.PREFS_NAME
import com.dhatuker.zwallet.util.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class SearchReceiverFragment : Fragment() {

    private lateinit var binding : FragmentSearchReceiverBinding
    lateinit var contactAdapter : ContactAdapter
    private lateinit var prefs: SharedPreferences
    private var item : Int = 0
    private val viewModel: TransferViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchReceiverBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

        this.contactAdapter = ContactAdapter(listOf()) { value, _ ->
            viewModel.setSelectedContact(value)
            Navigation.findNavController(view). navigate(R.id.action_searchReceiverFragment_to_inputAmountFragment)
        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        prepareData()
    }

    fun prepareData() {

        binding.recycleSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactAdapter
        }

            viewModel.getContact().observe(viewLifecycleOwner) {
                when (it.state) {
                    State.LOADING -> {
                        binding.apply {
                            loadingIndicator.visibility = View.VISIBLE
                            recycleSearch.visibility = View.GONE
                        }
                    }
                    State.SUCCESS -> {
                        if (it.data?.status == HttpsURLConnection.HTTP_OK) {
                            this.contactAdapter.apply {
                                addData(it.data?.data!!)
                                item = itemCount
                                notifyDataSetChanged()
                            }
                            binding.apply {
                                loadingIndicator.visibility = View.GONE
                                recycleSearch.visibility = View.VISIBLE
                                contactAvaiable.text = item.toString() + " Contact Found"
                            }
                        } else {
                            Toast.makeText(context, it.data?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> {
                        binding.apply {
                            loadingIndicator.visibility = View.GONE
                            recycleSearch.visibility = View.VISIBLE
                        }
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
}