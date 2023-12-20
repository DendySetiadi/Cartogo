package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.SyaratDenganSupirBinding

class SyaratDenganSupir : AppCompatActivity() {
    private lateinit var binding: SyaratDenganSupirBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = SyaratDenganSupirBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Now you can access the views using `binding.viewId`
    }
}