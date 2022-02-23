package com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.local.entity.GradeTypeEntity
import com.yulicahyani.eraport.databinding.ActivityGradeTypeBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.listsiswa.ListSiswaActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class GradeTypeActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_ID_MAPEL = "extra_id_mapel"
        const val EXTRA_CATEGORY_MAPEL = "extra_category_mapel"
    }
    private lateinit var activityGradeTypeBinding: ActivityGradeTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityGradeTypeBinding = ActivityGradeTypeBinding.inflate(layoutInflater)
        setContentView(activityGradeTypeBinding.root)

        val item = activityGradeTypeBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()

        activityGradeTypeBinding.cdNph.setOnClickListener(this)
        activityGradeTypeBinding.cdNpts.setOnClickListener(this)
        activityGradeTypeBinding.cdNpas.setOnClickListener(this)
        activityGradeTypeBinding.cdNilaiAkhirKd.setOnClickListener(this)

    }

    private fun navigationListener() {
        activityGradeTypeBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@GradeTypeActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@GradeTypeActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@GradeTypeActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@GradeTypeActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onClick(v: View?) {
        val gradeType = GradeTypeEntity(0,0,0)
        when (v) {
            activityGradeTypeBinding.cdNph -> {
                gradeType.is_nph = 1
                intentNilaiPengetahuan(gradeType)
            }
            activityGradeTypeBinding.cdNpts -> {
                gradeType.is_npts = 1
                intentNilaiPengetahuan(gradeType)
            }
            activityGradeTypeBinding.cdNpas -> {
                gradeType.is_npas = 1
                intentNilaiPengetahuan(gradeType)
            }
            activityGradeTypeBinding.cdNilaiAkhirKd -> {
                intentNilaiPengetahuan(gradeType)
            }
        }

    }

    private fun intentNilaiPengetahuan(gradeType : GradeTypeEntity){
        Intent(this@GradeTypeActivity, NilaiPengetahuanActivity::class.java).also {
            it.putExtra(NilaiPengetahuanActivity.EXTRA_GRADE_TYPE, gradeType)
            startActivity(it)
        }
    }
}