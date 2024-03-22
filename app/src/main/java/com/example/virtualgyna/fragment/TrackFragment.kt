package com.example.virtualgyna.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.virtualgyna.databinding.FragmentTrackBinding
import com.example.virtualgyna.models.MonitoringMetric
import com.example.virtualgyna.models.PregnancyMilestone
import com.example.virtualgyna.models.PrenatalVisit
import com.example.virtualgyna.utils.ReportGenerator
import java.text.SimpleDateFormat
import java.util.*


class TrackFragment : Fragment() {

    private lateinit var binding: FragmentTrackBinding

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
    }


}