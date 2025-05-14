package com.example.aklab_022

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val urlEditText = findViewById<EditText>(R.id.urlEditText)
        val downloadButton = findViewById<Button>(R.id.downloadButton)

        downloadButton.setOnClickListener {
            val url = urlEditText.text.toString()
            if (url.isNotEmpty()) {
                val intent = Intent(this, DownloadService::class.java)
                intent.putExtra("URL", url)
                startService(intent)
            }
        }

    }
}