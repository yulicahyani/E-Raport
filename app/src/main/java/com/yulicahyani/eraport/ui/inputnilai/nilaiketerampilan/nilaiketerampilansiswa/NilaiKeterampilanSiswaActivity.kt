package com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan.nilaiketerampilansiswa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulicahyani.eraport.databinding.ActivityNilaiKeterampilanSiswaBinding

class NilaiKeterampilanSiswaActivity : AppCompatActivity() {
    private lateinit var activityNilaiKeterampilanSiswaBinding: ActivityNilaiKeterampilanSiswaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiKeterampilanSiswaBinding = ActivityNilaiKeterampilanSiswaBinding.inflate(layoutInflater)
        setContentView(activityNilaiKeterampilanSiswaBinding.root)
    }
}