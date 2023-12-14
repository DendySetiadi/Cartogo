package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test.databinding.FragmentSearchPageBinding
import com.google.firebase.FirebaseApp

class SearchPage : Fragment() {

    private lateinit var binding: FragmentSearchPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase using the application context
        FirebaseApp.initializeApp(requireContext())

        binding.lokasi.setOnClickListener {
            val intent = Intent(requireContext(), Transaksi::class.java)
            startActivity(intent)
        }
    }
}
