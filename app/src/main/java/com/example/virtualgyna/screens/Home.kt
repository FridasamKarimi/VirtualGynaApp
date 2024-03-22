package com.example.virtualgyna.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.virtualgyna.R
import com.example.virtualgyna.databinding.ActivityHomeBinding
import com.example.virtualgyna.fragment.HomeFragment
import com.example.virtualgyna.fragment.ProfileFragment
import com.example.virtualgyna.fragment.TrackFragment

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.icHome -> replaceFragment(HomeFragment())
                R.id.icMonitor -> replaceFragment(TrackFragment())
                R.id.icProfile -> replaceFragment(ProfileFragment())

                else -> {
                }
            }
            true
        }
        if (resources.getColor(R.color.background_tint_dark) == resources.getColor(R.color.background_tint_dark)) {
            binding.bottomNavigationView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.background_tint_dark
                )
            )
        } else {
            binding.bottomNavigationView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.background_tint_light
                )
            )


        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}