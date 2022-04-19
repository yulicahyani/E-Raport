package com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityNilaiKeterampilanBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan.nilaiketerampilansiswa.NilaiKeterampilanSiswaActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class NilaiKeterampilanActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_NAME_SISWA = "extra_name_siswa"
        const val EXTRA_NAME_KETERAMPILAN = "extra_name_keterampilan"
        const val EXTRA_ID_MAPEL = "extra_id_mapel"
        const val EXTRA_ID_KD = "extra_id_kd"
        const val EXTRA_KODE_KD = "extra_kode_kd"
        const val EXTRA_DESKRIPSI_KD = "extra_deskripsi_kd"
        const val EXTRA_ID_KT = "extra_id_kt"
        const val EXTRA_NILAI_KT = "extra_nilai_kt"
    }

    private lateinit var activityNilaiKeterampilanBinding: ActivityNilaiKeterampilanBinding
    private lateinit var viewModel: NilaiKeterampilanViewModel
    private lateinit var id_siswa: String
    private lateinit var id_mapel: String
    private lateinit var id_kd: String
    private lateinit var id_kt: String
    private lateinit var nilai_kt: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiKeterampilanBinding = ActivityNilaiKeterampilanBinding.inflate(layoutInflater)
        setContentView(activityNilaiKeterampilanBinding.root)

        val item = activityNilaiKeterampilanBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()

        showProgressBar(true)

        id_siswa = intent.getStringExtra(EXTRA_ID_SISWA).toString()
        id_mapel = intent.getStringExtra(EXTRA_ID_MAPEL).toString()
        id_kd = intent.getStringExtra(EXTRA_ID_KD).toString()
        id_kt = intent.getStringExtra(EXTRA_ID_KT).toString()
        nilai_kt = intent.getStringExtra(EXTRA_NILAI_KT).toString()

        activityNilaiKeterampilanBinding.btnCancel.setOnClickListener(this)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            NilaiKeterampilanViewModel::class.java
        )

        if (intent.getStringExtra(EXTRA_NILAI_KT).isNullOrEmpty()) {
            showProgressBar(false)
            activityNilaiKeterampilanBinding.btnUpdate.visibility = View.INVISIBLE
            activityNilaiKeterampilanBinding.btnTambah.visibility = View.VISIBLE
            activityNilaiKeterampilanBinding.btnTambah.setOnClickListener(this)
        } else {
            showProgressBar(false)
            activityNilaiKeterampilanBinding.btnTambah.visibility = View.INVISIBLE
            activityNilaiKeterampilanBinding.btnUpdate.visibility = View.VISIBLE
            activityNilaiKeterampilanBinding.btnUpdate.setOnClickListener(this)
        }
        val kodeKd = StringBuilder()
        activityNilaiKeterampilanBinding.apply {
            tvNameSiswa.text = intent.getStringExtra(EXTRA_NAME_SISWA).toString()
            tvNameKeterampilan.text = intent.getStringExtra(EXTRA_NAME_KETERAMPILAN).toString()
            tvDeskripsiKd.text = intent.getStringExtra(EXTRA_DESKRIPSI_KD).toString()
            tvNameKd.text =
                kodeKd.append("KD ").append(
                    intent.getStringExtra(EXTRA_KODE_KD).toString()
                )
            nilaiEt.setText(intent.getStringExtra(EXTRA_NILAI_KT).toString())
        }

    }

    private fun navigationListener() {
        activityNilaiKeterampilanBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent =
                        Intent(this@NilaiKeterampilanActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent =
                        Intent(this@NilaiKeterampilanActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent =
                        Intent(this@NilaiKeterampilanActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@NilaiKeterampilanActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun showProgressBar(state: Boolean) = if (state) {
        activityNilaiKeterampilanBinding.progressBar.visibility = View.VISIBLE
    } else {
        activityNilaiKeterampilanBinding.progressBar.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when (v) {
            activityNilaiKeterampilanBinding.btnCancel -> {
                intentKeterampilanSiswa()
            }
            activityNilaiKeterampilanBinding.btnTambah -> {
                addNilaiKeterampilan()
            }
            activityNilaiKeterampilanBinding.btnUpdate -> {
                updateNilaiKeterampilan()
            }
        }
    }

    fun intentKeterampilanSiswa() {
        val intent =
            Intent(this@NilaiKeterampilanActivity, NilaiKeterampilanSiswaActivity::class.java)

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NilaiKeterampilanSiswaActivity.EXTRA_ID_SISWA, id_siswa)
        intent.putExtra(NilaiKeterampilanSiswaActivity.EXTRA_ID_MAPEL, id_mapel)
        startActivity(intent)
    }

    fun addNilaiKeterampilan() {
        if (TextUtils.isEmpty(activityNilaiKeterampilanBinding.nilaiEt.text.toString())) {
            activityNilaiKeterampilanBinding.nilaiEt.error = "Mohon masukan nilai"
            return
        }

        showProgressBar(true)
        val nilai = activityNilaiKeterampilanBinding.nilaiEt.text.toString()
        viewModel.addNilaiKeterampilan(
            id_siswa.toInt(),
            id_mapel.toInt(),
            id_kd.toInt(),
            id_kt.toInt(),
            nilai.toInt()
        )

        viewModel.getResponseCreate().observe(this, {
            if (it != null) {
                showProgressBar(false)
                if (it.status == 1) {
                    Toast.makeText(this, "Success Input Nilai", Toast.LENGTH_SHORT).show()
                    intentKeterampilanSiswa()
                } else {
                    Toast.makeText(this, "Failed Input Nilai", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    fun updateNilaiKeterampilan() {
        if (TextUtils.isEmpty(activityNilaiKeterampilanBinding.nilaiEt.text.toString())) {
            activityNilaiKeterampilanBinding.nilaiEt.error = "Mohon masukan nilai"
            return
        }

        showProgressBar(true)
        val nilai = activityNilaiKeterampilanBinding.nilaiEt.text.toString()
        viewModel.updateNilaiKeterampilan(
            id_siswa.toInt(),
            id_mapel.toInt(),
            id_kd.toInt(),
            id_kt.toInt(),
            nilai.toInt()
        )

        viewModel.getResponseUpdate().observe(this, {
            if (it != null) {
                showProgressBar(false)
                if (it.status == 1) {
                    Toast.makeText(this, "Success Update Nilai", Toast.LENGTH_SHORT).show()
                    intentKeterampilanSiswa()
                } else {
                    Toast.makeText(this, "Failed Update Nilai", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}