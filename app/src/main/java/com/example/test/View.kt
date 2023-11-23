package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.databinding.ViewBinding

class View : AppCompatActivity() {
    lateinit var viewBinding: ViewBinding
    var isLepaskunciActive = true // Variable untuk melacak tombol yang aktif

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Transaksi fragment awal untuk menampilkan HomePage
        if (savedInstanceState == null) {
            replaceFragment(HomePage())
        }

        setupClickListeners()
    }

    //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

    private fun setupClickListeners() {
        viewBinding.Home.setOnClickListener {
            replaceFragment(HomePage())
        }
        viewBinding.Search.setOnClickListener {
            replaceFragment(SearchPage())
        }
        viewBinding.Profile.setOnClickListener {
            replaceFragment(ProfilePage())
        }
    }

    private fun replaceFragment(fragment: HomePage) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null) // Optional: menambahkan ke back stack
        fragmentTransaction.commit()
    }

    private fun replaceFragment(fragment: SearchPage) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null) // Optional: menambahkan ke back stack
        fragmentTransaction.commit()
    }

    private fun replaceFragment(fragment: ProfilePage) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null) // Optional: menambahkan ke back stack
        fragmentTransaction.commit()
    }
}


