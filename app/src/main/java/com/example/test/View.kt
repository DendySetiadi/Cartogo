package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.test.databinding.ViewBinding

class View : AppCompatActivity() {
    lateinit var binding: ViewBinding
    var isLepaskunciActive = true // Variable untuk melacak tombol yang aktif

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Transaksi fragment awal untuk menampilkan HomePage
        if (savedInstanceState == null) {
            replaceFragment(HomePage())
        }

        binding.Home.setOnClickListener {
            replaceFragment(HomePage())
        }
        binding.Search.setOnClickListener {
            replaceFragment(SearchPage())
        }
        binding.Profile.setOnClickListener {
            replaceFragment(ProfilePage())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
