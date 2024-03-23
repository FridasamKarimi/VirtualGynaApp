package com.example.virtualgyna.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualgyna.adapters.RecommendationsAdapter
import com.example.virtualgyna.adapters.UpdatesAdapter
import com.example.virtualgyna.adapters.WeeksAdapter
import com.example.virtualgyna.ai.screens.Ai
import com.example.virtualgyna.databinding.FragmentHomeBinding
import com.example.virtualgyna.models.UpdatesData
import com.example.virtualgyna.models.WeekData
import com.example.virtualgyna.screens.Week


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

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

        binding.updateWeek.setOnClickListener {
            val intent = Intent(requireActivity(), Week::class.java)
            startActivity(intent)
        }
        binding.ai.setOnClickListener {
            val intent = Intent(requireActivity(), Ai::class.java)
            startActivity(intent)
        }

        dataIntialize()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recycler1 = binding.updatesRecyclerView
        recycler1.layoutManager = layoutManager
        recycler1.setHasFixedSize(true)
        updatesAdapter = UpdatesAdapter(updatesArrayList)
        recycler1.adapter = updatesAdapter
        updatesAdapter.notifyDataSetChanged()


        dataIntialize2()
        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recycler2 = binding.recommendationRecyclerView
        recycler2.layoutManager = layoutManager2
        recycler2.setHasFixedSize(true)
        recommendationsAdapter = RecommendationsAdapter(recommendArrayList)
        recycler2.adapter = recommendationsAdapter
        recommendationsAdapter.notifyDataSetChanged()

        dataIntialize3()
        val layoutManager3 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val recycler3 = binding.weekRecyclerView
        recycler3.layoutManager = layoutManager3
        recycler3.setHasFixedSize(true)
        weeksAdapter = WeeksAdapter(weekArrayList)
        recycler3.adapter = weeksAdapter
        weeksAdapter.notifyDataSetChanged()

    }

    private fun dataIntialize3() {
        weekArrayList = arrayListOf(
            WeekData("", "", "2"),
            WeekData("", "", "7"),
            WeekData("", "", "9"),
            WeekData("", "", "12"),
            WeekData("", "", "2"),
            WeekData("", "", "7"),
            WeekData("", "", "9"),
            WeekData("", "", "12"),
            WeekData("", "", "2"),
            WeekData("", "", "7"),
            WeekData("", "", "9"),
            WeekData("", "", "12"),
        )
    }

    private fun dataIntialize() {
        updatesArrayList = arrayListOf(
            UpdatesData("", "", "Day 22", "100", "200", ""),
            UpdatesData("", "", "Day 18", "300", "300", ""),
            UpdatesData("", "", "Day 20", "200", "92", ""),
            UpdatesData("", "", "Day 16", "500", "20", ""),
            UpdatesData("", "", "Day 10", "50", "0", ""),
            UpdatesData("", "", "Day 33", "30", "10", ""),
            UpdatesData("", "", "Day 35", "70", "45", ""),
            UpdatesData("", "", "Day 29", "600", "w23", ""),
            UpdatesData("", "", "Day 10", "50", "0", ""),
            UpdatesData("", "", "Day 33", "30", "10", ""),
            UpdatesData("", "", "Day 35", "70", "45", ""),
            UpdatesData("", "", "Day 29", "600", "w23", ""),
            UpdatesData("", "", "Day 11", "100", "2737", ""),
        )
    }

    private fun dataIntialize2() {
        recommendArrayList = arrayListOf(
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "special features to help you find exactly what you're looking ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "Real-time meetings by Google. Using your browser, share your ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "Google sign-in has a new look. We've improved the sign-in page ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "In your Google Account, you can see and manage your info"
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "special features to help you find exactly what you're looking ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "Real-time meetings by Google. Using your browser, share your ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "Google sign-in has a new look. We've improved the sign-in page ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "In your Google Account, you can see and manage your info"
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "special features to help you find exactly what you're looking ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "Real-time meetings by Google. Using your browser, share your ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "Google sign-in has a new look. We've improved the sign-in page ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "In your Google Account, you can see and manage your info"
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "special features to help you find exactly what you're looking ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "Real-time meetings by Google. Using your browser, share your ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "Google sign-in has a new look. We've improved the sign-in page ..."
            ),
            UpdatesData(
                "",
                "",
                "",
                "",
                "",
                "In your Google Account, you can see and manage your info"
            )
        )
    }


}