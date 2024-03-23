package com.example.virtualgyna.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.virtualgyna.databinding.FragmentTrackBinding
import com.example.virtualgyna.models.MonitoringMetric
import com.example.virtualgyna.models.PregnancyMilestone
import com.example.virtualgyna.models.PrenatalVisit
import com.example.virtualgyna.models.UpdatesData
import com.example.virtualgyna.utils.ReportGenerator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class TrackFragment : Fragment() {

    private lateinit var binding: FragmentTrackBinding
    private lateinit var auth: FirebaseAuth

    private val milestonesList = mutableListOf<PregnancyMilestone>()
    private val visitsList = mutableListOf<PrenatalVisit>()
    private val metricsList = mutableListOf<MonitoringMetric>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.calculateButton.setOnClickListener {
            calculatePregnancyInfo()
        }

        binding.addMilestoneButton.setOnClickListener {
            addPregnancyMilestone()
        }

        binding.addVisitButton.setOnClickListener {
            addPrenatalVisit()
        }

        binding.addMetricButton.setOnClickListener {
            addMonitoringMetric()
        }

        binding.generateReportButton.setOnClickListener {
            generateReport()
        }
    }

    private fun calculatePregnancyInfo() {
        val lastPeriodDateStr = binding.lastPeriodDateEditText.text.toString()
        if (lastPeriodDateStr.isNotEmpty()) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val lastPeriodDate = dateFormat.parse(lastPeriodDateStr)

            // Calculate Pregnancy Due Date
            val dueDateCalendar = Calendar.getInstance()
            dueDateCalendar.time = lastPeriodDate
            dueDateCalendar.add(Calendar.MONTH, 9) // Add 9 months for typical pregnancy period
            val dueDate = dateFormat.format(dueDateCalendar.time)

            // Calculate Pregnancy Week
            val todayCalendar = Calendar.getInstance()
            val differenceInMillis = todayCalendar.timeInMillis - lastPeriodDate.time
            val weeksElapsed = differenceInMillis / (1000 * 60 * 60 * 24 * 7)

            // Calculate Fertility Window
            val fertilityWindowStartCalendar = Calendar.getInstance()
            fertilityWindowStartCalendar.time = lastPeriodDate
            fertilityWindowStartCalendar.add(Calendar.DAY_OF_YEAR, 10)
            val fertilityWindowEndCalendar = Calendar.getInstance()
            fertilityWindowEndCalendar.time = lastPeriodDate
            fertilityWindowEndCalendar.add(Calendar.DAY_OF_YEAR, 14)
            val fertilityWindowStart = dateFormat.format(fertilityWindowStartCalendar.time)
            val fertilityWindowEnd = dateFormat.format(fertilityWindowEndCalendar.time)

            // Calculate Pregnancy Weight Recommendation (This is just a placeholder)
            val weightRecommendation =
                "Consult your healthcare provider for personalized recommendations."

            // Generate Pregnancy Milestones
            milestonesList.clear()
            milestonesList.add(PregnancyMilestone("First Trimester", lastPeriodDate))
            milestonesList.add(PregnancyMilestone("Second Trimester", dueDateCalendar.time))
            milestonesList.add(PregnancyMilestone("Third Trimester", dueDateCalendar.time))

            // Generate Prenatal Visits
            visitsList.clear()
            val prenatalVisitDates = listOf(
                lastPeriodDate, // First prenatal visit usually occurs around 8 weeks
                dueDateCalendar.time, // More frequent visits in the second and third trimesters
                dueDateCalendar.time
            )
            for (visitDate in prenatalVisitDates) {
                visitsList.add(PrenatalVisit(visitDate, "Scheduled prenatal visit"))
            }

            // Generate Monitoring Metrics
            metricsList.clear()
            // Example metrics - you should replace with actual monitoring data
            metricsList.add(MonitoringMetric("Weight", 60.0f, Date()))
            metricsList.add(MonitoringMetric("Blood Pressure", 120.0f, Date()))

            // Update UI with results
            binding.dueDateTextView.text = dueDate
            binding.weeksElapsedTextView.text = weeksElapsed.toString()
            binding.fertilityWindowTextView.text = "$fertilityWindowStart - $fertilityWindowEnd"
            binding.weightRecommendationTextView.text = weightRecommendation
        }
    }


    private fun addPregnancyMilestone() {
        val milestoneName = binding.milestoneNameEditText.text.toString()
        val milestoneDateStr = binding.milestoneDateEditText.text.toString()
        if (milestoneName.isNotEmpty() && milestoneDateStr.isNotEmpty()) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val milestoneDate = dateFormat.parse(milestoneDateStr)
            milestonesList.add(PregnancyMilestone(milestoneName, milestoneDate))
        }
    }

    private fun addPrenatalVisit() {
        val visitDateStr = binding.visitDateEditText.text.toString()
        val visitNotes = binding.visitNotesEditText.text.toString()
        if (visitDateStr.isNotEmpty() && visitNotes.isNotEmpty()) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val visitDate = dateFormat.parse(visitDateStr)
            visitsList.add(PrenatalVisit(visitDate, visitNotes))
        }
    }

    private fun addMonitoringMetric() {
        val metricType = binding.metricTypeEditText.text.toString()
        val metricValueStr = binding.metricValueEditText.text.toString()
        if (metricType.isNotEmpty() && metricValueStr.isNotEmpty()) {
            val metricValue = metricValueStr.toFloat()
            metricsList.add(MonitoringMetric(metricType, metricValue, Date()))
        }
    }

    private fun generateReport() {
        val reportGenerator = ReportGenerator()
        val report = reportGenerator.generateReport(milestonesList, visitsList, metricsList)
        binding.reportTextView.text = report

        // Get the current user's UID
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

        // Prepare the UpdatesData object
        val updatesData = currentUserUid?.let {
            UpdatesData(
                uid = it, // Use the UID of the current user
                milestones = milestonesList.joinToString(separator = "\n") { it.toString() },
                visits = visitsList.joinToString(separator = "\n") { it.toString() },
                metrics = metricsList.joinToString(separator = "\n") { it.toString() },
                weightRecommendation = "Consult your healthcare provider for personalized recommendations."
            )
        }

        // Get a reference to the Firebase Realtime Database
        val database = FirebaseDatabase.getInstance().getReference("myTrack")

        // Generate a unique key for the new data entry
        val trackEntryKey = database.push().key ?: ""

        // Write the data to the database under the unique key
        val trackEntryRef = database.child(trackEntryKey)
        trackEntryRef.setValue(updatesData)
            .addOnSuccessListener {
                // Data successfully saved
                // You can add any success message or action here
                Toast.makeText(requireContext(), "Data saved successfully!", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener { exception ->
                // Failed to save data
                // You can handle the error or display an error message here
                Toast.makeText(
                    requireContext(),
                    "Failed to save data: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}