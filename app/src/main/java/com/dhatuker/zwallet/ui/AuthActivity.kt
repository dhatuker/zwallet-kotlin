package com.dhatuker.zwallet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.ActivityAuthBinding
import com.dhatuker.zwallet.databinding.ActivityMainBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}