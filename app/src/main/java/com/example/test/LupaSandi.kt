package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.DaftarBinding
import com.example.test.databinding.LupaSandiBinding

class LupaSandi: AppCompatActivity() {
    private lateinit var  binding: LupaSandiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LupaSandiBinding.inflate(layoutInflater)
        setContentView(binding.root)}

}