package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseauth.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Signup : AppCompatActivity() {
    val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    val auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.signIn.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        binding.registerBtn.setOnClickListener {
            val userEnteredEmail = binding.emailEt.text
            val password = binding.passwordEt.text
            if (userEnteredEmail.toString().isEmpty()||password.toString().isEmpty()||binding.ConfirmPassword.text.toString().isEmpty()){
                Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(userEnteredEmail).matches()){
                binding.emailEt.error = "Invalid Email"
                binding.emailEt.requestFocus()
                return@setOnClickListener
            }
            if (password.length<6){
                binding.passwordEt.error = "Password must be at least 6 characters"
                binding.passwordEt.requestFocus()
                return@setOnClickListener
            }
            if (password.toString()!=binding.ConfirmPassword.text.toString()){
                binding.ConfirmPassword.error = "Password does not match"
                binding.ConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            else{
                auth.createUserWithEmailAndPassword(userEnteredEmail.toString(),binding.ConfirmPassword.text.toString()).addOnCompleteListener {
                    val intent = Intent(this,MainActivity::class.java)
                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}