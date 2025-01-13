package com.example.imagegridapp;

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import com.example.imagegridapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout

    // URL-urile imaginilor
    private val imageUrls = listOf(
        "http://cti.ubm.ro/cmo/digits/img0.jpg",
        "http://cti.ubm.ro/cmo/digits/img1.jpg",
        "http://cti.ubm.ro/cmo/digits/img2.jpg",
        "http://cti.ubm.ro/cmo/digits/img3.jpg",
        "http://cti.ubm.ro/cmo/digits/img4.jpg",
        "http://cti.ubm.ro/cmo/digits/img5.jpg",
        "http://cti.ubm.ro/cmo/digits/img6.jpg",
        "http://cti.ubm.ro/cmo/digits/img7.jpg",
        "http://cti.ubm.ro/cmo/digits/img8.jpg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)

        // Descărcăm și afișăm imaginile folosind corutine
        loadImages()
    }

    private fun loadImages() {
        CoroutineScope(Dispatchers.Main).launch {
            for (url in imageUrls) {
                val bitmap = downloadImage(url)
                addImageToGrid(bitmap)
            }
        }
    }

    // Funcție pentru a descărca imaginea de la un URL
    private suspend fun downloadImage(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    // Funcție pentru a adăuga imaginea în GridLayout
    private fun addImageToGrid(bitmap: Bitmap?) {
        if (bitmap != null) {
            val imageView = ImageView(this)
            imageView.setImageBitmap(bitmap)
            imageView.layoutParams = GridLayout.LayoutParams().apply {
                width = 250
                height = 250
                marginStart = 8
                marginEnd = 8
                bottomMargin = 8
                topMargin = 8
            }
            gridLayout.addView(imageView)
        }
    }
}