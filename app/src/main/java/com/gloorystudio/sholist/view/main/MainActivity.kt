package com.gloorystudio.sholist.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.gloorystudio.sholist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }
}