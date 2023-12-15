package com.example.test

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.test.databinding.FragmentUbahProfileBinding

class UbahProfile : Fragment(R.layout.fragment_ubah_profile) {
    private var _binding: FragmentUbahProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profil: ImageView
    private lateinit var gantiProfil: Button
    private lateinit var profileManager: ProfileManager
    private lateinit var viewModel: ProfileView

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUbahProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProfileView::class.java)
        viewModel.profileManager = ProfileManager(requireContext())
        profileManager = ProfileManager(requireContext())
        binding.profil.setImageURI(profileManager.getProfileImageUri())
        binding.gantiProfil.setOnClickListener {
            openGallery()
        }
        binding.editFname.setText(viewModel.profileManager?.userFname)
        binding.editLname.setText(viewModel.profileManager?.userLname)
        binding.editUname.setText(viewModel.profileManager?.userName)
        binding.editEmail.setText(viewModel.profileManager?.userEmail)
        binding.telpon.setText(viewModel.profileManager?.userTelepon)

        binding.simpan.setOnClickListener {
            val newFname = binding.editFname.text.toString()
            val newLname = binding.editLname.text.toString()
            val newName = binding.editUname.text.toString()
            viewModel.updateUserName(newName)
            val newEmail = binding.editEmail.text.toString()
            val newTelepon = binding.telpon.text.toString()

            viewModel.profileManager?.apply {
                userFname = newFname
                userLname = newLname
                userName = newName
                userEmail = newEmail
                userTelepon = newTelepon
            }

        }

    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri = data.data!!
            binding.profil.setImageURI(selectedImageUri)

            profileManager.setProfileImageUri(selectedImageUri)

            viewModel.updateProfileImageUri(selectedImageUri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}