package com.example.virtualgyna.utils

import com.example.virtualgyna.models.MonitoringMetric
import com.example.virtualgyna.models.PregnancyMilestone
import com.example.virtualgyna.models.PrenatalVisit
import java.text.SimpleDateFormat
import java.util.*

class ReportGenerator {

    fun generateReport(
        milestones: List<PregnancyMilestone>,
        visits: List<PrenatalVisit>,
        metrics: List<MonitoringMetric>
    ): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val reportBuilder = StringBuilder()

        // Append milestones
        reportBuilder.append("Pregnancy Milestones:\n")
        for (milestone in milestones) {
            reportBuilder.append("${milestone.milestoneName}: ${dateFormat.format(milestone.milestoneDate)}\n")
        }
        reportBuilder.append("\n")

        // Append prenatal visits
        reportBuilder.append("Prenatal Visits:\n")
        for (visit in visits) {
            reportBuilder.append("${dateFormat.format(visit.visitDate)}: ${visit.notes}\n")
        }
        reportBuilder.append("\n")

        // Append monitoring metrics
        reportBuilder.append("Monitoring Metrics:\n")
        for (metric in metrics) {
            reportBuilder.append("${metric.metricType}: ${metric.value} (${dateFormat.format(metric.date)})\n")
        }

        return reportBuilder.toString()
    }
}
