package com.example.chatapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.chatapp.R
import com.example.chatapp.ui.register.RegisterActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper())
            .postDelayed({
                startQuranActivity()
            }, 2000)
    }

    private fun startQuranActivity() {
        val intent = Intent(
            this@SplashActivity,
            RegisterActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}