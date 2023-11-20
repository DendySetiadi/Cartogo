package com.example.test

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.DaftarBinding
import com.example.test.databinding.LupaSandiBinding
import com.google.firebase.auth.FirebaseAuth

class LupaSandi: AppCompatActivity() {
    private lateinit var  binding: LupaSandiBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LupaSandiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tombollanjut.setOnClickListener {
            val email = binding.masukemail2.text.toString()
            val edtEmail = binding.masukemail2

            if (email.isEmpty()) {
                edtEmail.error = "Email Tidak Boleh Kosong"
                edtEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edtEmail.error = "Email Tidak Valid"
                edtEmail.requestFocus()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {

                if (it.isSuccessful){
                    Toast.makeText(this, "Email Reset Password Telah Dikirim", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }

            }


        }

    }


}