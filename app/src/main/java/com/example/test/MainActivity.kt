package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private var textInputLayout: TextInputLayout? = null
    private lateinit var textInputEditText: TextInputEditText
    private lateinit var button: ImageView
    private lateinit var database: DatabaseReference
    private lateinit var masuk_email: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = FirebaseDatabase.getInstance().getReference("users")
        textInputLayout = findViewById(R.id.TextInputLayout)
        textInputEditText = findViewById(R.id.InputPassword)
        button = findViewById(R.id.tombolmasuk)
        masuk_email = findViewById(R.id.masuk_email)

        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val passwordInput = charSequence.toString()
                if (passwordInput.length >= 8) {
                    val pattern = Pattern.compile("[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?]*")
                    val matcher = pattern.matcher(passwordInput)
                    val passwordsMatch = matcher.find()
                    if (passwordsMatch) {
                        textInputLayout?.helperText = "Your password is strong"
                        textInputLayout?.error = null
                    } else {
                        textInputLayout?.error =
                            "Mix of letters (upper and lower case), numbers, and symbols"
                    }
                } else {
                    textInputLayout?.helperText = "Password must be 8 characters long"
                    textInputLayout?.error = null
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        button.setOnClickListener {
            val email = masuk_email.text.toString()
            val password = textInputEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Email atau password harus diisi cuy", Toast.LENGTH_SHORT).show()
            } else {
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(email).exists()) {
                            if (snapshot.child(email).child("password").getValue(String::class.java) == password) {
                                Toast.makeText(applicationContext, "Login berhasil yes", Toast.LENGTH_SHORT).show()
                                setContentView(R.layout.activity_home_page)
                                val register = Intent(applicationContext, HomePage::class.java)
                                startActivity(register)

                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Passwordmu salah lur",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Datamu belum terdaftar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle onCancelled event if needed
                    }
                })
            }
        }
    }
}
