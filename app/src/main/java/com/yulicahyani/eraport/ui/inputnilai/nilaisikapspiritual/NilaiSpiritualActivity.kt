package com.yulicahyani.eraport.ui.inputnilai.nilaisikapspiritual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityNilaiSpiritualBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class NilaiSpiritualActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_NAMA_SISWA = "extra_nama_siswa"
    }
    private lateinit var  activityNilaiSpiritualBinding: ActivityNilaiSpiritualBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiSpiritualBinding = ActivityNilaiSpiritualBinding.inflate(layoutInflater)
        setContentView(activityNilaiSpiritualBinding.root)

        val item = activityNilaiSpiritualBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()
    }

    private fun navigationListener() {
        activityNilaiSpiritualBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@NilaiSpiritualActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@NilaiSpiritualActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@NilaiSpiritualActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@NilaiSpiritualActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}