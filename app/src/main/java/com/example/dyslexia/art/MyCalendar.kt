package com.example.dyslexia.art

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MyCalendar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val home_btn = findViewById<Button>(R.id.home)
        home_btn.setOnClickListener{
           this.finish()
        }

        val calendar_ncku = findViewById<Button>(R.id.button9)
        calendar_ncku.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("http://web.ncku.edu.tw/p/412-1000-6149.php?Lang=zh-tw"))
            startActivity(url)
        }
        val calendar_art = findViewById<Button>(R.id.button10)
        calendar_art.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.art.ncku.edu.tw/files/40-1408-11-1.php?Lang=zh-tw"))
            startActivity(url)
        }
        val calendar_personal = findViewById<Button>(R.id.button11)
        calendar_art.setOnClickListener {
            val intent = Intent(this, PersonalCalendar::class.java)
            startActivity(intent)
        }

    }
}