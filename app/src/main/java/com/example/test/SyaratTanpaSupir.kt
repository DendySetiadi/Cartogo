package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.SyaratTanpaSupirBinding

class SyaratTanpaSupir : AppCompatActivity() {
    private lateinit var binding: SyaratTanpaSupirBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SyaratTanpaSupirBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Now you can access the views using `binding.viewId`
    }
}