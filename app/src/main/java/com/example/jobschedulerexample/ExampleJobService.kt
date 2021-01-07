package com.example.jobschedulerexample

import android.app.job.JobParameters
import android.app.job.JobService
import timber.log.Timber

class ExampleJobService : JobService() {

    private var jobCancelled = false

    override fun onStartJob(p0: JobParameters?): Boolean {
        Timber.d("Job started")
        doBackgroundWork(p0)

        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Timber.d("Job cancelled before completion")
        jobCancelled = true
        return true
    }

    private fun doBackgroundWork(p0: JobParameters?) {
        Thread(object : Runnable {
            override fun run() {
                for (i in 0..10) {
                    Timber.d("run: $i")
                    if (jobCancelled) {
                        return
                    }
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                Timber.d("Job finished")
                jobFinished(p0, false)
            }
        }).start()
    }
}