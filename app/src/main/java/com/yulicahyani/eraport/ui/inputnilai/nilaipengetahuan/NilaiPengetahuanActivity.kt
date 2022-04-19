package com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityNilaiPengetahuanBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.listnilaisikap.ListNilaiSikapActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan.nilaipengetahuansiswa.NilaiPengetahuanSiswaActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaisikapsosial.NilaiSosialViewModel
import com.yulicahyani.eraport.ui.raport.RaportActivity

class NilaiPengetahuanActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_NAME_TEMA = "extra_name_tema"
        const val EXTRA_KODE_KD = "extra_kode_kd"
        const val EXTRA_DESKRIPSI_KD = "extra_deskripsi_kd"
        const val EXTRA_NILAI = "extra_nilai"
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_NAME_SISWA = "extra_name_siswa"
        const val EXTRA_ID_MAPEL = "extra_id_mapel"
        const val EXTRA_ID_KD = "extra_id_kd"
        const val EXTRA_ID_TEMA = "extra_id_tema"
        const val EXTRA_NPH = "extra_nph"
        const val EXTRA_NPTS = "extra_npts"
        const val EXTRA_NPAS = "extra_npas"
    }

    private lateinit var activityNilaiPengetahuanBinding: ActivityNilaiPengetahuanBinding
    private lateinit var viewModel: NilaiPengetahuanViewModel
    private lateinit var id_siswa: String
    private lateinit var id_mapel: String
    private lateinit var id_tema: String
    private lateinit var id_kd: String
    private lateinit var is_nph: String
    private lateinit var is_npts: String
    private lateinit var is_npas: String
    private lateinit var nilai_kd: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiPengetahuanBinding = ActivityNilaiPengetahuanBinding.inflate(layoutInflater)
        setContentView(activityNilaiPengetahuanBinding.root)

        val item = activityNilaiPengetahuanBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()
        showProgressBar(true)

        id_siswa = intent.getStringExtra(EXTRA_ID_SISWA).toString()
        id_mapel = intent.getStringExtra(EXTRA_ID_MAPEL).toString()
        id_tema = intent.getStringExtra(EXTRA_ID_TEMA).toString()
        id_kd = intent.getStringExtra(EXTRA_ID_KD).toString()
        is_nph = intent.getStringExtra(EXTRA_NPH).toString()
        is_npts = intent.getStringExtra(EXTRA_NPTS).toString()
        is_npas = intent.getStringExtra(EXTRA_NPAS).toString()
        nilai_kd = intent.getStringExtra(EXTRA_NILAI).toString()

        activityNilaiPengetahuanBinding.btnCancel.setOnClickListener(this)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            NilaiPengetahuanViewModel::class.java
        )

        if (intent.getStringExtra(EXTRA_NILAI).isNullOrEmpty()) {
            showProgressBar(false)
            activityNilaiPengetahuanBinding.btnUpdate.visibility = View.INVISIBLE
            activityNilaiPengetahuanBinding.btnTambah.visibility = View.VISIBLE
            activityNilaiPengetahuanBinding.btnTambah.setOnClickListener(this)
        } else {
            showProgressBar(false)
            activityNilaiPengetahuanBinding.btnTambah.visibility = View.INVISIBLE
            activityNilaiPengetahuanBinding.btnUpdate.visibility = View.VISIBLE
            activityNilaiPengetahuanBinding.btnUpdate.setOnClickListener(this)
        }
        val kodeKd = StringBuilder()
        activityNilaiPengetahuanBinding.apply {
            tvNameSiswa.text = intent.getStringExtra(EXTRA_NAME_SISWA).toString()
            tvNameTema.text = intent.getStringExtra(EXTRA_NAME_TEMA).toString()
            tvNameKd.text =
                kodeKd.append("KD ").append(intent.getStringExtra(EXTRA_KODE_KD).toString())
            tvDeskripsiKd.text = intent.getStringExtra(EXTRA_DESKRIPSI_KD).toString()
            nilaiEt.setText(intent.getStringExtra(EXTRA_NILAI).toString())
        }


    }

    private fun navigationListener() {
        activityNilaiPengetahuanBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent =
                        Intent(this@NilaiPengetahuanActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent =
                        Intent(this@NilaiPengetahuanActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent =
                        Intent(this@NilaiPengetahuanActivity, InputNilaiActivity::class.java)
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

    private fun showProgressBar(state: Boolean) = if (state) {
        activityNilaiPengetahuanBinding.progressBar.visibility = View.VISIBLE
    } else {
        activityNilaiPengetahuanBinding.progressBar.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when (v) {
            activityNilaiPengetahuanBinding.btnCancel -> {
                intentNilaiPengetahuanSiswa()
            }
            activityNilaiPengetahuanBinding.btnTambah -> {
                addNilaiPengetahuan()
            }

            activityNilaiPengetahuanBinding.btnUpdate -> {
                updateNilaiPengetahuan()
            }
        }
    }

    fun addNilaiPengetahuan() {
        if (TextUtils.isEmpty(activityNilaiPengetahuanBinding.nilaiEt.text.toString())) {
            activityNilaiPengetahuanBinding.nilaiEt.error = "Mohon masukan nilai"
            return
        }

        showProgressBar(true)
        val nilai = activityNilaiPengetahuanBinding.nilaiEt.text.toString()
        if (id_siswa != null && id_mapel != null && id_tema != null && id_kd != null && is_nph != null && is_npts != null && is_npas != null) {
            viewModel.addNilaiPengetahuan(
                id_siswa.toInt(),
                id_mapel.toInt(),
                id_tema.toInt(),
                id_kd.toInt(),
                is_nph.toInt(),
                is_npts.toInt(),
                is_npas.toInt(),
                nilai.toInt()
            )
        }

        viewModel.getResponseCreate().observe(this, {
            if (it != null) {
                showProgressBar(false)
                if (it.status == 1) {
                    Toast.makeText(this, "Success Input Nilai", Toast.LENGTH_SHORT).show()
                    intentNilaiPengetahuanSiswa()
                } else {
                    Toast.makeText(this, "Failed Input Nilai", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    fun intentNilaiPengetahuanSiswa() {
        val intent =
            Intent(
                this@NilaiPengetahuanActivity,
                NilaiPengetahuanSiswaActivity::class.java
            )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NilaiPengetahuanSiswaActivity.EXTRA_ID_SISWA, id_siswa)
        intent.putExtra(NilaiPengetahuanSiswaActivity.EXTRA_ID_MAPEL, id_mapel)
        intent.putExtra(NilaiPengetahuanSiswaActivity.EXTRA_NPH, is_nph)
        intent.putExtra(NilaiPengetahuanSiswaActivity.EXTRA_NPTS, is_npts)
        intent.putExtra(NilaiPengetahuanSiswaActivity.EXTRA_NPAS, is_npas)
        intent.putExtra(
            NilaiPengetahuanSiswaActivity.EXTRA_CATEGORY_MAPEL,
            "pengetahuan"
        )

        startActivity(intent)
    }

    fun updateNilaiPengetahuan() {
        if (TextUtils.isEmpty(activityNilaiPengetahuanBinding.nilaiEt.text.toString())) {
            activityNilaiPengetahuanBinding.nilaiEt.error = "Mohon masukan nilai"
            return
        }

        showProgressBar(true)
        val nilai = activityNilaiPengetahuanBinding.nilaiEt.text.toString()
        if (id_siswa != null && id_mapel != null && id_tema != null && id_kd != null && is_nph != null && is_npts != null && is_npas != null) {
            viewModel.updateNilaiPengetahuan(
                id_siswa.toInt(),
                id_mapel.toInt(),
                id_tema.toInt(),
                id_kd.toInt(),
                is_nph.toInt(),
                is_npts.toInt(),
                is_npas.toInt(),
                nilai.toInt()
            )
        }

        viewModel.getResponseUpdate().observe(this, {
            if (it != null) {
                showProgressBar(false)
                if (it.status == 1) {
                    Toast.makeText(this, "Success Update Nilai", Toast.LENGTH_SHORT).show()
                    intentNilaiPengetahuanSiswa()
                } else {
                    Toast.makeText(this, "Failed Update Nilai", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}