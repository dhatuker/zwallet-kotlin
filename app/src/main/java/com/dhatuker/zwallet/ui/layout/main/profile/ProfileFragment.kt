package com.dhatuker.zwallet.ui.layout.main.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.FragmentProfileBinding
import com.dhatuker.zwallet.ui.layout.SplashScreenActivity
import com.dhatuker.zwallet.util.KEY_LOGGED_IN
import com.dhatuker.zwallet.util.PREFS_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

        binding.personInfoBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_detailProfile)
        }

        binding.changePassBtn.setOnClickListener{
            Navigation.findNavController(view).navigate((R.id.action_profileFragment_to_changePasswordFragment))
        }

        binding.changePinBtn.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_changePinFragment)
        }

        binding.logoutBtn.setOnClickListener {

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


}