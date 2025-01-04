package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseauth.databinding.ActivitySplashScreenBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SplashScreen : AppCompatActivity() {
    val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    val isUserLogin = Firebase.auth.currentUser
    lateinit var splashIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if (isUserLogin==null){
                splashIntent = Intent(this,Login::class.java)
            }
            else {
                splashIntent =Intent(this, MainActivity::class.java)
            }
            startActivity(splashIntent)
            finish()
        },2000)
    }
}