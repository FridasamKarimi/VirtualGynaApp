package com.example.virtualgyna

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualgyna.auth.Login
import com.example.virtualgyna.databinding.ActivityMainBinding
import com.example.virtualgyna.screens.Home
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            if (auth.currentUser != null) {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)
    }


}

