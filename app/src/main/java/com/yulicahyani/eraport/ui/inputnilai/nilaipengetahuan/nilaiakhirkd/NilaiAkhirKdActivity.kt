package com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan.nilaiakhirkd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulicahyani.eraport.databinding.ActivityNilaiAkhirKdBinding

class NilaiAkhirKdActivity : AppCompatActivity() {
    private lateinit var activityNilaiAkhirKdBinding: ActivityNilaiAkhirKdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiAkhirKdBinding = ActivityNilaiAkhirKdBinding.inflate(layoutInflater)
        setContentView(activityNilaiAkhirKdBinding.root)
    }
}