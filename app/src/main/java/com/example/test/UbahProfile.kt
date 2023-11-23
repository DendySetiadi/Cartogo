package com.example.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.UbahProfileBinding

class UbahProfile : AppCompatActivity() {
    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    private lateinit var  binding: UbahProfileBinding
    private lateinit var profil: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UbahProfileBinding.inflate(layoutInflater)
        setContentView(R.layout.ubah_profile)

        profil = findViewById(R.id.profil)
        val gantiProfil: Button = findViewById(R.id.gantiProfil)

        gantiProfil.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
            profil.setImageBitmap(bitmap)
        }
    }
}