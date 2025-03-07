package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseauth.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    val auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.create.setOnClickListener {
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }
        binding.Login.setOnClickListener {
            val userEnteredEmail = binding.emailEt.text
            val password = binding.passwordEt.text
            if (userEnteredEmail.toString().isEmpty()||password.toString().isEmpty()){
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
            else{
                auth.signInWithEmailAndPassword(userEnteredEmail.toString(),password.toString()).addOnCompleteListener {
                    val intent = Intent(this,MainActivity::class.java)
                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}