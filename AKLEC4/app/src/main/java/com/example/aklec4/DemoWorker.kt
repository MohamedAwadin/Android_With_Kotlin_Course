package com.example.aklec4

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class DemoWorker(context: Context , workerParameters: WorkerParameters) : Worker(context , workerParameters) {
    override fun doWork(): Result {

        val count= runAttemptCount
        val rec1 = inputData.getInt("Number" , -1)
        val rec2 = inputData.getInt("MyNumber", -1)
        if ( rec2 == -1){
            return Result.failure()
        }
        else{
            for (i in 1..rec2){
                Thread.sleep(1000)
                Log.i("TAG" , "doWork : $i")
            }
            val dataobj = Data.Builder().putString("Result" , "Work Done").build()
            return Result.success(dataobj)
        }
    }
}