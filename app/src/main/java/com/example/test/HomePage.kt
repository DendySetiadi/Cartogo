package com.example.test

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.test.databinding.FragmentHomePageBinding
import com.google.firebase.auth.FirebaseAuth

class HomePage : Fragment(R.layout.fragment_home_page){
    lateinit var binding: FragmentHomePageBinding
    var isLepaskunciActive = true // Variable to track the active button
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialising Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Mengecek status login saat aplikasi dimulai
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Pengguna sudah login
            val userEmail = currentUser.email
            // Lakukan tindakan yang diperlukan setelah login
        } else {
            // Pengguna belum login
            // Lakukan tindakan yang diperlukan untuk menavigasi ke halaman login atau lainnya
        }
        binding.lepaskunci.setOnClickListener {
            replaceFragment(Lepaskunci())
            toggleButtonBackground(binding.lepaskunci)
        }

        binding.dengansupir.setOnClickListener {
            replaceFragment(DenganSupir())
            toggleButtonBackground(binding.dengansupir)
        }
    }

    private fun toggleButtonBackground(button: Button) {
        if (isLepaskunciActive) {
            updateButtonBackground(binding.lepaskunci, "#808080", "#24A8DF")
        } else {
            updateButtonBackground(binding.dengansupir, "#808080", "#24A8DF")
        }
        updateButtonBackground(button, "#24A8DF", "#808080")
        isLepaskunciActive = !isLepaskunciActive
    }

    private fun updateButtonBackground(button: Button, textColor: String, backgroundColor: String) {
        val grayColor = Color.parseColor(textColor)
        button.backgroundTintList = ColorStateList.valueOf(grayColor)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
