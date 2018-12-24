package com.yly.androidallinone.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class WorkManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val photoCheckBuilder =
//                PeriodicWorkRequestBuilder<PhotoCheckWorker>(12, TimeUnit.HOURS)
//// ...if you want, you can apply constraints to the builder here...
//
//// Create the actual work object:
//        val photoCheckWork = photoCheckBuilder.build()
//// Then enqueue the recurring task:
//        WorkManager.getInstance().enqueue(photoCheckWork)


        val myConstraints = Constraints.Builder()
//                .setRequiresDeviceIdle(true)
//                .setRequiresCharging(true)
                // Many other constraints are available, see the
                // Constraints.Builder reference
                .build()


        val compressionWork = OneTimeWorkRequestBuilder<CompressWorker>()
                .setConstraints(myConstraints).build()
        WorkManager.getInstance().enqueue(compressionWork)

        WorkManager.getInstance().getWorkInfoByIdLiveData(compressionWork.id)
                .observe(this, Observer { workInfo ->
                    // Do something with the status
                    if (workInfo != null && workInfo.state.isFinished) {
                        // ...
                        println("work finish")
                    }
                })

        WorkManager.getInstance()
                // First, run all the A tasks (in parallel):
                .beginWith(compressionWork)
                // ...when all A tasks are finished, run the single B task:
                .then(compressionWork)
                // ...then run the C tasks (in any order):
                .then(compressionWork)
                .enqueue()
    }
}