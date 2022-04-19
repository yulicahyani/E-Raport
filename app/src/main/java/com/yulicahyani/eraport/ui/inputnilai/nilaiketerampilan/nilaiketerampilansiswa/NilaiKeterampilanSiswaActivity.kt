package com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan.nilaiketerampilansiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.response.ResultsListNilaiKeterampilan
import com.yulicahyani.eraport.databinding.ActivityNilaiKeterampilanSiswaBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan.NilaiKeterampilanActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class NilaiKeterampilanSiswaActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_ID_MAPEL = "extra_id_mapel"
    }

    private lateinit var activityNilaiKeterampilanSiswaBinding: ActivityNilaiKeterampilanSiswaBinding
    private lateinit var viewModel: NilaiKeterampilanSiswaViewModel
    private lateinit var adapter: NilaiKeterampilanSiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiKeterampilanSiswaBinding = ActivityNilaiKeterampilanSiswaBinding.inflate(layoutInflater)
        setContentView(activityNilaiKeterampilanSiswaBinding.root)

        val item =
            activityNilaiKeterampilanSiswaBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()

        val id_siswa = intent.getStringExtra(EXTRA_ID_SISWA).toString()
        val id_mapel = intent.getStringExtra(EXTRA_ID_MAPEL).toString()

        showProgressBar(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NilaiKeterampilanSiswaViewModel::class.java)
        viewModel.findNilaiPengetahuanSiswa(id_siswa.toInt(), id_mapel.toInt())

        adapter = NilaiKeterampilanSiswaAdapter()
        viewModel.getNilaiKeterampilanSiswa().observe(this ) {
            if (it!=null){
                showProgressBar(false)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        }

        activityNilaiKeterampilanSiswaBinding.apply {
            rvNilaiKeterampilan.layoutManager = LinearLayoutManager(this@NilaiKeterampilanSiswaActivity)
            rvNilaiKeterampilan.setHasFixedSize(true)
            rvNilaiKeterampilan.adapter = adapter
        }

        adapter.setOnClickCallback(object : NilaiKeterampilanSiswaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsListNilaiKeterampilan) {
                Intent(
                    this@NilaiKeterampilanSiswaActivity,
                    NilaiKeterampilanActivity::class.java
                ).also {
                    it.putExtra(NilaiKeterampilanActivity.EXTRA_ID_SISWA, data.id_siswa)
                    it.putExtra(NilaiKeterampilanActivity.EXTRA_NAME_SISWA, data.nama_siswa)
                    it.putExtra(NilaiKeterampilanActivity.EXTRA_NAME_KETERAMPILAN, data.nama_keterampilan)
                    it.putExtra(NilaiKeterampilanActivity.EXTRA_ID_MAPEL, data.id_mapel)
                    it.putExtra(NilaiKeterampilanActivity.EXTRA_ID_KD, data.id_kd)
                    it.putExtra(NilaiKeterampilanActivity.EXTRA_KODE_KD, data.kode_kd)
                    it.putExtra(NilaiKeterampilanActivity.EXTRA_DESKRIPSI_KD, data.deskripsi_kd)
                    it.putExtra(NilaiKeterampilanActivity.EXTRA_ID_KT, data.id_kt)
                    it.putExtra(NilaiKeterampilanActivity.EXTRA_NILAI_KT, data.nilai_kt)
                    startActivity(it)
                }
            }

        })


    }

    private fun navigationListener() {
        activityNilaiKeterampilanSiswaBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent =
                        Intent(this@NilaiKeterampilanSiswaActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent =
                        Intent(this@NilaiKeterampilanSiswaActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent =
                        Intent(this@NilaiKeterampilanSiswaActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent =
                        Intent(this@NilaiKeterampilanSiswaActivity, RaportActivity::class.java)
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
            activityNilaiKeterampilanSiswaBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityNilaiKeterampilanSiswaBinding.progressBar.visibility = View.GONE
        }
    }
}