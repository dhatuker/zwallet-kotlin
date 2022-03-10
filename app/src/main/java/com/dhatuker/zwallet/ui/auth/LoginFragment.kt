package com.dhatuker.zwallet.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.Navigation
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentLoginBinding
import com.dhatuker.zwallet.model.ApiResponse
import com.dhatuker.zwallet.model.LoginRequest
import com.dhatuker.zwallet.model.User
import com.dhatuker.zwallet.network.NetworkConfig
import com.dhatuker.zwallet.ui.main.MainActivity
import com.dhatuker.zwallet.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams(SOFT))

        prefs = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

        binding.btnLogin.setOnClickListener{
            if (binding.inputEmail.text.isNullOrEmpty() || binding.inputPassword.text.isNullOrEmpty()){
                Toast.makeText(activity, "Email/Password is Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var loginRequest = LoginRequest(
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )

            NetworkConfig(context).getService().login(loginRequest)
                .enqueue(object : Callback<ApiResponse<User>>{
                    override fun onResponse(
                        call: Call<ApiResponse<User>>,
                        response: Response<ApiResponse<User>>
                    ) {
                        val res = response.body()?.data
                        if(response.body()?.status != HttpsURLConnection.HTTP_OK){
                            Toast.makeText(context, "Authentication Failed : Wrong Email/Password", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            with (prefs.edit()){
                                putBoolean(KEY_LOGGED_IN, true)
                                putString(KEY_USER_EMAIL, res?.email)
                                putString(KEY_USER_TOKEN, res?.token)
                                putString(KEY_USER_REFRESH_TOKEN, res?.refreshToken)
                                apply()
                            }
                            Handler().postDelayed({
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }, 2000)
                        }
                    }
                    override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                        Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT)
                    }
                })
        }

        binding.textSignUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.textForgotPassword.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPassFragment)
        }
    }


}
