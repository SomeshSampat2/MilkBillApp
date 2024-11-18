package com.example.milkbillcalculator

import android.app.Application
import androidx.work.Configuration
import androidx.work.Configuration.Builder
import androidx.work.WorkManager

class MilkBillApp : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        // Initialize WorkManager
        WorkManager.initialize(
            this,
            workManagerConfiguration
        )
    }

    override val workManagerConfiguration: Configuration
        get() = Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}
