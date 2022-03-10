package com.dhatuker.zwallet.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dhatuker.zwallet.databinding.ActivitySplashScreenBinding
import com.dhatuker.zwallet.ui.auth.AuthActivity
import com.dhatuker.zwallet.ui.main.MainActivity
import com.dhatuker.zwallet.util.KEY_LOGGED_IN
import com.dhatuker.zwallet.util.PREFS_NAME

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE)

        if(prefs.getBoolean(KEY_LOGGED_IN, false)) {
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        } else {
            Handler().postDelayed({
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }
}