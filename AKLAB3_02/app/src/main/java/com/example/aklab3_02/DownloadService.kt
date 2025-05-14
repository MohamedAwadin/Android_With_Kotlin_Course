package com.example.aklab3_02

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class DownloadService : IntentService("DownloadService") {

    override fun onHandleIntent(intent: Intent?) {
        intent?.getStringExtra("URL")?.let { url ->
            try {
                // 1. Download the image
                val bitmap = BitmapFactory.decodeStream(URL(url).openStream())

                // 2. Save the image
                val fileName = "downloaded_image.jpg"
                val file = File(filesDir, fileName)
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }

                // 3. Send broadcast
                val broadcastIntent = Intent("com.example.DOWNLOAD_COMPLETE")
                broadcastIntent.putExtra("FILE_NAME", fileName)
                sendBroadcast(broadcastIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


}