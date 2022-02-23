package com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityNilaiPengetahuanBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class NilaiPengetahuanActivity : AppCompatActivity() {
    private lateinit var activityNilaiPengetahuanBinding: ActivityNilaiPengetahuanBinding
    companion object{
        const val EXTRA_GRADE_TYPE = "extra_grade_type"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiPengetahuanBinding = ActivityNilaiPengetahuanBinding.inflate(layoutInflater)
        setContentView(activityNilaiPengetahuanBinding.root)

        val item = activityNilaiPengetahuanBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()
    }

    private fun navigationListener() {
        activityNilaiPengetahuanBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@NilaiPengetahuanActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@NilaiPengetahuanActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@NilaiPengetahuanActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@NilaiPengetahuanActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}