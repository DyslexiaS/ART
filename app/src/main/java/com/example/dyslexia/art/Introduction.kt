package com.example.dyslexia.art

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.BaseAdapter
import android.widget.Button

class Introduction : AppCompatActivity() {

    private val pageview: ViewPager by bind(R.id.page)
    private val tablayout:TabLayout by bind(R.id.tablayout)
    private val adpater=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        val home_btn = findViewById<Button>(R.id.home)
        home_btn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
