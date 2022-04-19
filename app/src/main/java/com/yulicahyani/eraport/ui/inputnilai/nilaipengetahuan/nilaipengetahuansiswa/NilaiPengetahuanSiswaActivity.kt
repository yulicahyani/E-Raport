package com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan.nilaipengetahuansiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.response.ResultsListNilaiPengetahuan
import com.yulicahyani.eraport.databinding.ActivityNilaiPengetahuanSiswaBinding
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan.NilaiPengetahuanActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class NilaiPengetahuanSiswaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_ID_MAPEL = "extra_id_mapel"
        const val EXTRA_CATEGORY_MAPEL = "extra_category_mapel"
        const val EXTRA_NPH = "extra_nph"
        const val EXTRA_NPTS = "extra_npts"
        const val EXTRA_NPAS = "extra_npas"
    }

    private lateinit var activityNilaiPengetahuanSiswaBinding: ActivityNilaiPengetahuanSiswaBinding
    private lateinit var adapter: NilaiPengetahuanSiswaAdapter
    private lateinit var viewModel: NilaiPengetahuanSiswaViewModel
    private lateinit var idSiswa: String
    private lateinit var idMapel: String
    private lateinit var categoryMapel: String
    private lateinit var isNph: String
    private lateinit var isNpts: String
    private lateinit var isNpas: String

    lateinit var prefHelper: PrefHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiPengetahuanSiswaBinding =
            ActivityNilaiPengetahuanSiswaBinding.inflate(layoutInflater)
        setContentView(activityNilaiPengetahuanSiswaBinding.root)

        val item =
            activityNilaiPengetahuanSiswaBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)

        idSiswa = intent.getStringExtra(EXTRA_ID_SISWA).toString()
        idMapel = intent.getStringExtra(EXTRA_ID_MAPEL).toString()
        categoryMapel = intent.getStringExtra(EXTRA_CATEGORY_MAPEL).toString()
        isNph = intent.getStringExtra(EXTRA_NPH).toString()
        isNpts = intent.getStringExtra(EXTRA_NPTS).toString()
        isNpas = intent.getStringExtra(EXTRA_NPAS).toString()


        showProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            NilaiPengetahuanSiswaViewModel::class.java
        )
        viewModel.findNilaiPengetahuanSiswa(
            idSiswa.toInt(),
            idMapel.toInt(),
            categoryMapel,
            isNph.toInt(),
            isNpts.toInt(),
            isNpas.toInt()
        )

        adapter = NilaiPengetahuanSiswaAdapter()
        viewModel.getNilaiPengetahuanSiswa().observe(this) {
            if (it != null) {
                showProgressBar(false)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        }

        activityNilaiPengetahuanSiswaBinding.apply {
            rvNilaiPengetahuan.layoutManager =
                LinearLayoutManager(this@NilaiPengetahuanSiswaActivity)
            rvNilaiPengetahuan.setHasFixedSize(true)
            rvNilaiPengetahuan.adapter = adapter
        }

        adapter.setOnClickCallback(object : NilaiPengetahuanSiswaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsListNilaiPengetahuan) {
                Intent(
                    this@NilaiPengetahuanSiswaActivity,
                    NilaiPengetahuanActivity::class.java
                ).also {
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_NAME_TEMA, data.nama_tema)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_KODE_KD, data.kode_kd)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_NILAI, data.nilai_kd)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_DESKRIPSI_KD, data.deskripsi_kd)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_ID_SISWA, data.id_siswa)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_NAME_SISWA, data.nama_siswa)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_ID_MAPEL, data.id_mapel)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_ID_TEMA, data.id_tema)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_ID_KD, data.id_kd)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_NPH, data.is_nph)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_NPTS, data.is_npts)
                    it.putExtra(NilaiPengetahuanActivity.EXTRA_NPAS, data.is_npas)
                    startActivity(it)
                }

            }

        })

    }

    private fun navigationListener() {
        activityNilaiPengetahuanSiswaBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent =
                        Intent(this@NilaiPengetahuanSiswaActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent =
                        Intent(this@NilaiPengetahuanSiswaActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent =
                        Intent(this@NilaiPengetahuanSiswaActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent =
                        Intent(this@NilaiPengetahuanSiswaActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            activityNilaiPengetahuanSiswaBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityNilaiPengetahuanSiswaBinding.progressBar.visibility = View.GONE
        }
    }
}