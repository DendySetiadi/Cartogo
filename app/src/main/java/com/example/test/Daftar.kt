package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.test.databinding.DaftarBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class Daftar : AppCompatActivity() {
    private lateinit var  binding: DaftarBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.masuk.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.tomboldaftar.setOnClickListener{
            val email = binding.masukemail2.text.toString()
            val pass  = binding.masukpassword.text.toString()
            val confirmPass = binding.konfirmasipassword.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
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
                if (passwordInput.length >= 8) {
                    val pattern = Pattern.compile("[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?]*")
                    val matcher = pattern.matcher(passwordInput)
                    val passwordsMatch = matcher.find()
                    if (passwordsMatch) {
                        textInputLayout.helperText = "Your password is strong"
                        textInputLayout.error = null
                    } else {
                        textInputLayout.error =
                            "Mix of letters (upper and lower case), numbers, and symbols"
                    }
                } else {
                    textInputLayout.helperText = "Password must be 8 characters long"
                    textInputLayout.error = null
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }
}