package com.example.test
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.Daftar
import com.example.test.HomePage
import com.example.test.LupaSandi
import com.example.test.R
import com.example.test.databinding.MasukBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class Masuk : AppCompatActivity() {

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText
    private lateinit var binding: MasukBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MasukBinding.inflate(layoutInflater)
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
                signInWithEmailAndPassword(email, pass)
            } else {
                showToast(getString(R.string.empty_fields_not_allowed))
            }
        }

        textInputLayout = binding.TextInputLayout
        textInputEditText = binding.masukpassword

        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                val passwordInput = charSequence.toString()
                if (passwordInput.length < 8) {
                    textInputLayout.helperText = getString(R.string.password_length_error)
                    textInputLayout.error = null
                } else {
                    textInputLayout.helperText = null
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                } else {
                    showToast(getFirebaseAuthErrorMessage(task.exception))
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getFirebaseAuthErrorMessage(exception: Exception?): String {
        // Handle different Firebase authentication errors and return user-friendly messages
        // You can customize this method based on your specific needs
        return when (exception) {
            is FirebaseAuthInvalidUserException -> getString(R.string.invalid_user)
            is FirebaseAuthInvalidCredentialsException -> getString(R.string.invalid_credentials)
            else -> getString(R.string.auth_failed)
        }
    }
}
