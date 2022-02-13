package com.yulicahyani.eraport.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityLoginBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        activityLoginBinding.btnLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}