package com.example.test

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.test.databinding.ActivityHomePageBinding
class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomePageBinding
    var isLepaskunciActive = true // Variable to track the active button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lepaskunci.setOnClickListener {
            replacefragment(Lepaskunci())
            toggleButtonBackground(binding.lepaskunci)
        }

        binding.dengansupir.setOnClickListener {
            replacefragment(DenganSupir())
            toggleButtonBackground(binding.dengansupir)
        }
    }

    private fun toggleButtonBackground(button: Button) {
        if (isLepaskunciActive) {
            val grayColor = Color.parseColor("#808080")
            binding.lepaskunci.backgroundTintList = ColorStateList.valueOf(grayColor)
            val backgroundColor = Color.parseColor("#24A8DF")
            button.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        } else {
            val grayColor = Color.parseColor("#808080")
            binding.dengansupir.backgroundTintList = ColorStateList.valueOf(grayColor)
            val backgroundColor = Color.parseColor("#24A8DF")
            button.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        }
        isLepaskunciActive = !isLepaskunciActive
    }

    private fun replacefragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
        }
}



