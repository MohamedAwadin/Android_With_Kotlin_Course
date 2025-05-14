package com.example.aklec4

import android.net.Network
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)




        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val data1 = workDataOf("Number" to 20, Pair("AnotherArg", 30))
        val data2 = Data.Builder().putInt("MyNumber", 15).putInt("MyNumber2", 10).build()
        val request = OneTimeWorkRequestBuilder<DemoWorker>()
            .setInputData(data1)
            .setInputData(data2)
            .setBackoffCriteria(BackoffPolicy.LINEAR , 1 , TimeUnit.DAYS)
            .setConstraints(constraints)

            .build()

        val request2 = OneTimeWorkRequestBuilder<DemoWorker>()
            .setInputData(data1)
            .setInputData(data2)
            .setConstraints(constraints)

            .build()

        //val prequest = PeriodicWorkRequestBuilder<DemoWorker>(1 , TimeUnit.DAYS, 15 , TimeUnit.MINUTES ).build()


        val manager = WorkManager.getInstance(this)


        // to link between the req and manager
        //manager.enqueue(request)

        manager.beginWith(request).then(request2).enqueue()



    }
}