package com.example.virtualgyna.screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualgyna.databinding.ActivityWeekBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Week : AppCompatActivity() {
    private lateinit var binding: ActivityWeekBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeekBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        binding.updateWeek.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val etWeek = binding.etWeek.text.toString().trim()
        if (etWeek.isEmpty()) {
            Toast.makeText(this, "Enter Week", Toast.LENGTH_SHORT).show()
        } else {
            uploadJobInfoToDb(etWeek)
            binding.etWeek.text?.clear()
        }
    }

    private fun uploadJobInfoToDb(etWeek: String) {
        val currentUser = firebaseAuth.currentUser
        val uid = currentUser?.uid
        if (uid.isNullOrEmpty()) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("weeks")
        val weekKey = ref.push().key ?: ""

        val weekData = hashMapOf(
            "id" to weekKey,
            "uid" to uid,
            "week" to etWeek,
        )

        ref.child(weekKey).setValue(weekData)
            .addOnSuccessListener {
                Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to upload week: ${e.message}")
                Toast.makeText(this, "Uploading Week Failed", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        private const val TAG = "WeekActivity"
    }
}
