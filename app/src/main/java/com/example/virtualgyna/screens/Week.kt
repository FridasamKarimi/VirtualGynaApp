package com.example.virtualgyna.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.virtualgyna.R

class Week : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_week)
    }
}