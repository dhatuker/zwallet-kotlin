package com.dhatuker.zwallet.ui.layout.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentContinueChangePinBinding
import com.dhatuker.zwallet.ui.layout.main.MainActivity
import com.dhatuker.zwallet.ui.layout.main.home.HomeViewModel
import com.dhatuker.zwallet.util.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ContinueChangePinFragment : Fragment() {

    private lateinit var binding: FragmentContinueChangePinBinding
    private lateinit var e1 : EditText
    private lateinit var e2 : EditText
    private lateinit var e3 : EditText
    private lateinit var e4 : EditText
    private lateinit var e5 : EditText
    private lateinit var e6 : EditText
    private val viewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContinueChangePinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        e1 = binding.editText1
        e2 = binding.editText2
        e3 = binding.editText3
        e4 = binding.editText4
        e5 = binding.editText5
        e6 = binding.editText6

        e1.addTextChangedListener {
            if(e1.text.length == 1){
                e2.requestFocus()
            }
        }

        e2.addTextChangedListener {
            if(e2.text.length == 1 ){
                e3.requestFocus()
            } else e1.requestFocus()
        }

        e3.addTextChangedListener {
            if(e3.text.length == 1 ){
                e4.requestFocus()
            } else e2.requestFocus()
        }

        e4.addTextChangedListener {
            if(e4.text.length == 1 ){
                e5.requestFocus()
            } else e3.requestFocus()
        }

        e5.addTextChangedListener {
            if(e5.text.length == 1 ){
                e6.requestFocus()
            } else e4.requestFocus()
        }

        e6.addTextChangedListener {
            if(e6.text.length == 1 ){
            } else e5.requestFocus()
        }

        binding.changePinBtn.setOnClickListener {
            if (e1.text.isNullOrEmpty() || e2.text.isNullOrEmpty()
                || e3.text.isNullOrEmpty() || e4.text.isNullOrEmpty()
                || e5.text.isNullOrEmpty() || e6.text.isNullOrEmpty()
            ) {
                Toast.makeText(activity, "Pin is Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pinText = binding.editText1.text.toString() +
                    binding.editText2.text.toString() +
                    binding.editText3.text.toString() +
                    binding.editText4.text.toString() +
                    binding.editText5.text.toString() +
                    binding.editText6.text.toString()

            viewModel.setPin(pinText).observe(viewLifecycleOwner) {
                when(it.state){
                    State.LOADING -> {
                    }
                    State.SUCCESS -> {
                        if (it.data?.status == HttpsURLConnection.HTTP_OK) {
                            Toast.makeText(activity, "Your PIN is Changed", Toast.LENGTH_SHORT).show()
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        } else {
                            Toast.makeText(activity, "Error Happen!", Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(view).popBackStack()
                        }
                    }
                    State.ERROR -> {
                    }
                }
            }

        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }

}