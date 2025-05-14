package com.example.aklab3_02

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val fileName = intent.getStringExtra("FILE_NAME")
        if (fileName != null) {
            val activityIntent = Intent(context, ImageViewActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("FILE_NAME", fileName)
            }
            context.startActivity(activityIntent)
        }
    }
}