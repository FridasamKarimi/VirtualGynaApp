package com.example.virtualgyna.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualgyna.adapters.RecommendationsAdapter
import com.example.virtualgyna.adapters.UpdatesAdapter
import com.example.virtualgyna.adapters.WeeksAdapter
import com.example.virtualgyna.ai.screens.Ai
import com.example.virtualgyna.databinding.FragmentHomeBinding
import com.example.virtualgyna.models.UpdatesData
import com.example.virtualgyna.models.UserData
import com.example.virtualgyna.models.WeekData
import com.example.virtualgyna.screens.Week
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    //adapters
    private lateinit var updatesAdapter: UpdatesAdapter
    private lateinit var recommendationsAdapter: RecommendationsAdapter
    private lateinit var weeksAdapter: WeeksAdapter

    //models
    private var updatesArrayList = mutableListOf<UpdatesData>()
    private var recommendArrayList = mutableListOf<UpdatesData>()
    private var weekArrayList = mutableListOf<WeekData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Fetch and display user name
        fetchAndDisplayUserName()

        binding.updateWeek.setOnClickListener {
            val intent = Intent(requireActivity(), Week::class.java)
            startActivity(intent)
        }
        binding.ai.setOnClickListener {
            val intent = Intent(requireActivity(), Ai::class.java)
            startActivity(intent)
        }

        getMyTrack()

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recycler1 = binding.updatesRecyclerView
        recycler1.layoutManager = layoutManager
        recycler1.setHasFixedSize(true)
        updatesAdapter = UpdatesAdapter(updatesArrayList)
        recycler1.adapter = updatesAdapter
        updatesAdapter.notifyDataSetChanged()

        getRecommendation()

        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recycler2 = binding.recommendationRecyclerView
        recycler2.layoutManager = layoutManager2
        recycler2.setHasFixedSize(true)
        recommendationsAdapter = RecommendationsAdapter(recommendArrayList)
        recycler2.adapter = recommendationsAdapter
        recommendationsAdapter.notifyDataSetChanged()


        getWeek()

        val layoutManager3 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val recycler3 = binding.weekRecyclerView
        recycler3.layoutManager = layoutManager3
        recycler3.setHasFixedSize(true)
        weeksAdapter = WeeksAdapter(weekArrayList)
        recycler3.adapter = weeksAdapter
        weeksAdapter.notifyDataSetChanged()

    }

    private fun fetchAndDisplayUserName() {
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            val userRef =
                FirebaseDatabase.getInstance().getReference("registeredUser").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.e("Tag", "exists")

                        val userData = snapshot.getValue(UserData::class.java)
                        Log.e("UserData", userData.toString())
                        if (userData != null) {
                            val userName = userData.name ?: "Unknown"
                            Log.e("HomeFragment", "User name retrieved: $userName")
                            binding.userName.text = userName
                        } else {
                            Log.e(TAG, "Userdata Doesn't Exist")
                        }
                    } else {
                        Log.e(TAG, "User data snapshot does not exist")
//                        Timber.tag("HomeFragment").e("User data snapshot does not exist")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                    Log.e("HomeFragment", "Failed to fetch user data: ${error.message}")
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch user data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Log.e("HomeFragment", "Current user ID is null")
        }
    }


    private fun getMyTrack() {
        database = Firebase.database.reference
        database.child("myTrack").get()
            .addOnSuccessListener { dataSnapshot ->
                for (trackSnapshot in dataSnapshot.children) {
                    val id = trackSnapshot.child("id").getValue(String::class.java)
                    val milestones = trackSnapshot.child("milestones").getValue(String::class.java)
                    val visits = trackSnapshot.child("visits").getValue(String::class.java)
                    val metrics = trackSnapshot.child("metrics").getValue(String::class.java)
                    val weightRecommendation = trackSnapshot.child("weightRecommendation").getValue(String::class.java)
                    val uid = trackSnapshot.child("uid").getValue(String::class.java)

                    if (id != null && milestones != null && visits != null && metrics != null && uid != null && weightRecommendation != null) {
                        val track =
                            UpdatesData(id, milestones, visits, metrics, weightRecommendation)
                        updatesArrayList.add(track)
                    }
                }
                recommendationsAdapter.notifyDataSetChanged()
                Log.d("data", updatesArrayList.toString())

            }
    }

    private fun getRecommendation() {
        database = Firebase.database.reference
        database.child("myTrack").get()
            .addOnSuccessListener { dataSnapshot ->
                for (trackSnapshot in dataSnapshot.children) {
                    val id = trackSnapshot.child("id").getValue(String::class.java)
                    val milestones = trackSnapshot.child("milestones").getValue(String::class.java)
                    val visits = trackSnapshot.child("visits").getValue(String::class.java)
                    val metrics = trackSnapshot.child("metrics").getValue(String::class.java)
                    val weightRecommendation =
                        trackSnapshot.child("weightRecommendation").getValue(String::class.java)
                    val uid = trackSnapshot.child("uid").getValue(String::class.java)

                    Log.d("tracks", "Id: $id, Milestones:  $milestones, Visits: $visits, Metrics: $metrics, WeightReccoms: $weightRecommendation")

                    if ( milestones != null && visits != null && metrics != null && uid != null && weightRecommendation != null) {
                        val track =
                            UpdatesData(uid, milestones, visits, metrics, weightRecommendation)
                        recommendArrayList.add(track)
                    }
                }
                updatesAdapter.notifyDataSetChanged()

            }
    }

    private fun getWeek() {
        database = Firebase.database.reference
        database.child("weeks").get()
            .addOnSuccessListener { dataSnapshot ->
                for (weekSnapshot in dataSnapshot.children) {
                    val week = weekSnapshot.child("week").getValue(String::class.java)
                    val uid = weekSnapshot.child("uid").getValue(String::class.java)

                    if (week != null && uid != null) {
                        val week = WeekData(uid, week)
                        weekArrayList.add(week)
                    }
                }
                weeksAdapter.notifyDataSetChanged()

            }
    }


}