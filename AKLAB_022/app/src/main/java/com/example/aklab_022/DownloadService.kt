package com.example.aklab_022

import android.app.IntentService
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import android.content.Context
import android.content.BroadcastReceiver

class DownloadService : IntentService("DownloadService") {
    private val downloadReceiver = DownloadReceiver()

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter("com.example.DOWNLOAD_COMPLETE")
        registerReceiver(downloadReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.getStringExtra("URL")?.let { url ->
            try {

                val bitmap = BitmapFactory.decodeStream(URL(url).openStream())


                val fileName = "downloaded_image.jpg"
                val file = File(filesDir, fileName)
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }


                val broadcastIntent = Intent("com.example.DOWNLOAD_COMPLETE")
                broadcastIntent.putExtra("FILE_NAME", fileName)
                sendBroadcast(broadcastIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}