package com.example.jobschedulerexample

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    fun scheduleJob(view: View) {

        val componentName = ComponentName(this, ExampleJobService::class.java)
        val info = JobInfo.Builder(123, componentName)
            .setRequiresCharging(true)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setPersisted(true)
            .setPeriodic(15 * 60 * 10000)
            .build()

        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Timber.d("Job scheduled")
        } else Timber.d("Job scheduling failed")
    }

    fun cancelJob(view: View) {
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(123)
        Timber.d("Job cancelled")
    }
}