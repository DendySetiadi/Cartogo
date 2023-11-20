package com.example.test

// grac3_Psp JGN DIHAPUS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import com.example.test.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        binding.lupakatasandi.setOnClickListener{
            val intent = Intent(this, LupaSandi::class.java)
            startActivity(intent)
        }
        binding.belumpunyaakun.setOnClickListener {
            val intent = Intent(this, Daftar::class.java)
            startActivity(intent)
        }

        binding.tombolmasuk.setOnClickListener {
            val email = binding.masukemail2.text.toString()
            val pass = binding.masukpassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        textInputLayout = binding.TextInputLayout
        textInputEditText = binding.masukpassword

        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val passwordInput = charSequence.toString()
                if (passwordInput.length < 8) {
                    textInputLayout.helperText = "Password must be 8 characters long"
                    textInputLayout.error = null
            }}

            override fun afterTextChanged(editable: Editable) {}
        })
    }
}
