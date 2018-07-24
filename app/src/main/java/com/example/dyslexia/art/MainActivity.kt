package com.example.dyslexia.art

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ncku_btn= findViewById<Button>(R.id.button1)
        ncku_btn.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("http://web.ncku.edu.tw/"))
            startActivity(url)
        }

        val intro_btn= findViewById<Button>(R.id.button2)
        intro_btn.setOnClickListener {
            val intent = Intent(this, Introduction::class.java)
            startActivity(intent)
        }

        val art_btn= findViewById<Button>(R.id.button3)
        art_btn.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.art.ncku.edu.tw/bin/home.php"))
            startActivity(url)
        }

        val calendar_btn = findViewById<Button>(R.id.button4)
        calendar_btn.setOnClickListener {
            val intent = Intent(this, MyCalendar::class.java)
            startActivity(intent)
        }

        val phone_btn = findViewById<Button>(R.id.button5)
        phone_btn.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("tel:062757575+p+52500")))
        }

        val table_btn= findViewById<Button>(R.id.button8)
        table_btn.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://iartncku.github.io/page/gotogoogletable"))
            startActivity(url)
        }

        val link_ncku = findViewById<ImageButton>(R.id.imageButton)
        link_ncku.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("http://web.ncku.edu.tw/"))
            startActivity(url)
        }
        val link_art = findViewById<ImageButton>(R.id.imageButton2)
        link_art.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.art.ncku.edu.tw/bin/home.php"))
            startActivity(url)
        }
    }
}
