package com.yulicahyani.eraport.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityDashboardBinding
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.profil.ProfilActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var activityDashboardBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(activityDashboardBinding.root)

        val item = activityDashboardBinding.navigation.menu.findItem(R.id.nav_dashboard)
        item.setChecked(true)
        navigationListener()

        activityDashboardBinding.user.setOnClickListener {
            val intent = Intent(this@DashboardActivity, ProfilActivity::class.java)
            startActivity(intent)
        }

        activityDashboardBinding.tvNama.setOnClickListener {
            val intent = Intent(this@DashboardActivity, ProfilActivity::class.java)
            startActivity(intent)
        }
    }


    private fun navigationListener() {
        activityDashboardBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@DashboardActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@DashboardActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@DashboardActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@DashboardActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}