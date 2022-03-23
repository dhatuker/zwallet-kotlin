package com.dhatuker.zwallet.ui.layout.auth.register

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentRegisterBinding
import com.dhatuker.zwallet.model.ApiResponse
import com.dhatuker.zwallet.model.request.RegisterRequest
import com.dhatuker.zwallet.network.NetworkConfig
import com.dhatuker.zwallet.ui.layout.SplashScreenActivity
import com.dhatuker.zwallet.ui.layout.auth.login.LoginFragment
import com.dhatuker.zwallet.ui.layout.auth.login.LoginViewModel
import com.dhatuker.zwallet.util.Helper.formatPrice
import com.dhatuker.zwallet.util.KEY_LOGGED_IN
import com.dhatuker.zwallet.util.State
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener{

            var username = binding.inputNameRegister.text.toString()
            var email = binding.inputEmailRegister.text.toString()
            var password = binding.inputPasswordRegister.text.toString()

            viewModel.setEmail(binding.inputEmailRegister.text.toString())

            viewModel.signup(username, email, password).observe(viewLifecycleOwner) {
                when (it.state) {
                    State.LOADING -> {
                    }
                    State.SUCCESS -> {
                        if (it.data?.status == HttpsURLConnection.HTTP_OK) {
                            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_otpFragment)
                        }
                    }
                    State.ERROR -> {
                    }
                }
            }
        }
    }
}