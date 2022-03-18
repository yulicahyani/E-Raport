package com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan.nilaipengetahuansiswa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulicahyani.eraport.databinding.ActivityNilaiPengetahuanSiswaBinding

class NilaiPengetahuanSiswaActivity : AppCompatActivity() {
    private lateinit var activityNilaiPengetahuanSiswaBinding: ActivityNilaiPengetahuanSiswaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiPengetahuanSiswaBinding = ActivityNilaiPengetahuanSiswaBinding.inflate(layoutInflater)
        setContentView(activityNilaiPengetahuanSiswaBinding.root)
    }
}