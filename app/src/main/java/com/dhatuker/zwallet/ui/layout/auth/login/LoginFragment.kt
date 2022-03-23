package com.dhatuker.zwallet.ui.layout.auth.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dhatuker.zwallet.ui.viewModelFactory
import androidx.navigation.Navigation
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentLoginBinding
import com.dhatuker.zwallet.ui.layout.main.MainActivity
import com.dhatuker.zwallet.util.*
import com.dhatuker.zwallet.ui.widget.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var preferences: SharedPreferences
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        preferences = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        loadingDialog = LoadingDialog(requireActivity())
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnLogin.setOnClickListener{
            if (binding.inputEmail.text.isNullOrEmpty() || binding.inputPassword.text.isNullOrEmpty()){
                Toast.makeText(activity, "Email/Password is Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val response = viewModel.login(
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )

            response.observe(viewLifecycleOwner) {
                when (it.state) {
                    State.LOADING -> {
                        loadingDialog.start("Processing your request")
                    }
                    State.SUCCESS -> {
                        loadingDialog.stop()
                        if (it.data?.status == HttpsURLConnection.HTTP_OK){
                            if(it.data.data?.hasPin!!){
                                Handler().postDelayed({
                                    with(preferences.edit()) {
                                        putBoolean(KEY_LOGGED_IN, true)
                                        putString(KEY_USER_EMAIL, it.data.data?.email)
                                        putString(KEY_USER_TOKEN, it.data.data?.token)
                                        putString(KEY_USER_REFRESH_TOKEN, it.data.data?.refreshToken)
                                        apply()
                                    }
                                    val intent = Intent(activity, MainActivity::class.java)
                                    startActivity(intent)
                                    activity?.finish()
                                }, 2000)
                            } else {
                                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_createPinFragment)
                            }
                        } else {
                            Toast.makeText(context, "${it.data?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    State.ERROR -> {
                        loadingDialog.stop()
                        Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.textSignUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.textForgotPassword.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPassFragment)
        }
    }


}
