package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test.databinding.FragmentProfilePageBinding

class ProfilePage : Fragment(R.layout.fragment_profile_page) {
    lateinit var binding: FragmentProfilePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setting.setOnClickListener {
            val intent = Intent(requireContext(), UbahProfile::class.java)
            startActivity(intent)
        }
    }
}
