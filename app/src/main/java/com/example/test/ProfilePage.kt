package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test.databinding.FragmentProfilePageBinding

class ProfilePage : Fragment(R.layout.fragment_profile_page) {
    private var _binding: FragmentProfilePageBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ProfileView::class.java)

        viewModel.profileImageUri.observe(viewLifecycleOwner, Observer { uri -> binding.profill.setImageURI(uri) })

        binding.pengaturan2.setOnClickListener {
            // Pindah ke fragment lain (ganti dengan fragment yang sesuai)
            val fragment = UbahProfile()
            replaceFragment(fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
        fragmentTransaction.addToBackStack(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
