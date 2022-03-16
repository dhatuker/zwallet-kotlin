package com.dhatuker.zwallet.ui.layout.auth.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dhatuker.zwallet.databinding.FragmentRegisterBinding
import com.dhatuker.zwallet.model.ApiResponse
import com.dhatuker.zwallet.model.request.RegisterRequest
import com.dhatuker.zwallet.network.NetworkConfig
import com.dhatuker.zwallet.ui.layout.auth.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var prefs: SharedPreferences

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
            var registerRequest = RegisterRequest(
                binding.inputNameRegister.text.toString(),
                binding.inputEmailRegister.text.toString(),
                binding.inputPasswordRegister.text.toString()
            )

            NetworkConfig(context).getService().signup(registerRequest)
                .enqueue(object : Callback<ApiResponse<String>> {
                    override fun onResponse(
                        call: Call<ApiResponse<String>>,
                        response: Response<ApiResponse<String>>
                    ) {
                        val res = response.body()!!.message

                        if(response.body()?.status != HttpsURLConnection.HTTP_OK){
                            Toast.makeText(context, "Email Sudah Terdaftar", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Silahkan Cek Email Anda", Toast.LENGTH_SHORT).show()

                            Handler().postDelayed({
                                val intent = Intent(activity, LoginFragment::class.java)
                                startActivity(intent)
                            }, 2000)
//                        }
                        }
                    }
                    override fun onFailure(call: Call<ApiResponse<String>>, t: Throwable) {
                        Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT)
                    }
                })
        }
    }
}