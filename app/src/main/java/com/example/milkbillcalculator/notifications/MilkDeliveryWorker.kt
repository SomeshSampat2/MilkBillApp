package com.example.milkbillcalculator.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.milkbillcalculator.R
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Duration
import java.util.concurrent.TimeUnit

class MilkDeliveryWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        createNotificationChannel()
        showNotification()
        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Milk Delivery",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Milk delivery reminder notifications"
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Milk Delivery Check")
            .setContentText("Did you receive today's milk delivery?")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "milk_delivery_channel"
        private const val NOTIFICATION_ID = 1

        fun schedule(context: Context) {
            val currentTime = LocalDateTime.now()
            val scheduledTime = LocalDateTime.now().with(LocalTime.of(20, 30))

            // If current time is past 8:30 PM, schedule for next day
            val adjustedScheduledTime = if (currentTime.isAfter(scheduledTime)) {
                scheduledTime.plusDays(1)
            } else {
                scheduledTime
            }

            val initialDelay = Duration.between(currentTime, adjustedScheduledTime)

            val dailyWorkRequest = PeriodicWorkRequestBuilder<MilkDeliveryWorker>(
                24, TimeUnit.HOURS
            )
                .setInitialDelay(initialDelay.toMinutes(), TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    "milk_delivery_notification",
                    ExistingPeriodicWorkPolicy.REPLACE,
                    dailyWorkRequest
                )
        }
    }
}
