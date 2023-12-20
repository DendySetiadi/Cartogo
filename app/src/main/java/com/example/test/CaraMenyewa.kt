package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.CaraMenyewaBinding
import com.example.test.databinding.DaftarBinding

class CaraMenyewa : AppCompatActivity() {
    private lateinit var binding: CaraMenyewaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CaraMenyewaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}