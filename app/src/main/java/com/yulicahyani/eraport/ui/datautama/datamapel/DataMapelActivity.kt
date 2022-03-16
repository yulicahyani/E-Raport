package com.yulicahyani.eraport.ui.datautama.datamapel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityDataMapelBinding
import com.yulicahyani.eraport.databinding.ActivityDataSiswaBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class DataMapelActivity : AppCompatActivity() {

    private lateinit var activityDataMapelBinding: ActivityDataMapelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDataMapelBinding = ActivityDataMapelBinding.inflate(layoutInflater)
        setContentView(activityDataMapelBinding.root)

        val item = activityDataMapelBinding.navigation.menu.findItem(R.id.nav_data_utama)
        item.isChecked = true
        navigationListener()
    }

    private fun navigationListener() {
        activityDataMapelBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@DataMapelActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@DataMapelActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@DataMapelActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@DataMapelActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}