package com.dhatuker.zwallet.ui.layout.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.dhatuker.zwallet.databinding.FragmentChangePasswordBinding
import com.dhatuker.zwallet.ui.layout.main.MainActivity
import com.dhatuker.zwallet.ui.layout.main.home.HomeViewModel
import com.dhatuker.zwallet.ui.widget.LoadingDialog
import com.dhatuker.zwallet.util.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    private lateinit var binding : FragmentChangePasswordBinding
    private lateinit var loadingDialog : LoadingDialog
    private val viewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.confirmBtn.setOnClickListener {
            var old = binding.oldPassword.text.toString()
            var new = binding.newPassword.text.toString()
            var confirm = binding.confimPassword.text.toString()

            if(old != new){
                if(new == confirm){
                    viewModel.changePassword(old, new).observe(viewLifecycleOwner){
                        when (it.state) {
                            State.LOADING -> {
                                loadingDialog.start("Processing your request")
                            }
                            State.SUCCESS -> {
                                loadingDialog.stop()
                                Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG).show()

                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }
                            State.ERROR -> {
                                loadingDialog.stop()
                                Toast.makeText(activity, "Failed to change your passowrd", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(activity, "Your new Password id not same with Confirm Password", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(activity, "Same Password as Current Password", Toast.LENGTH_LONG).show()
            }
        }
    }

}