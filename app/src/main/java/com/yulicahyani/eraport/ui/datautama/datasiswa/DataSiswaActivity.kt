package com.yulicahyani.eraport.ui.datautama.datasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.response.ResultsSiswa
import com.yulicahyani.eraport.databinding.ActivityDataSiswaBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class DataSiswaActivity : AppCompatActivity() {

    private lateinit var activityDataSiswaBinding: ActivityDataSiswaBinding
    private lateinit var adapter: DataSiswaAdapter
    private lateinit var viewModel: DataSiswaViewModel
    lateinit var prefHelper : PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDataSiswaBinding = ActivityDataSiswaBinding.inflate(layoutInflater)
        setContentView(activityDataSiswaBinding.root)

        val item = activityDataSiswaBinding.navigation.menu.findItem(R.id.nav_data_utama)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)

        showProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DataSiswaViewModel::class.java)
        viewModel.findSiswaSekolah(prefHelper.getInt(Constant.PREF_ID_USER))

        adapter = DataSiswaAdapter()
        viewModel.getSiswaSekolah().observe(this, {
            if(it!=null){
                showProgressBar(false)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        })

        activityDataSiswaBinding.apply {
            rvSiswa.layoutManager = LinearLayoutManager(this@DataSiswaActivity)
            rvSiswa.setHasFixedSize(true)
            rvSiswa.adapter = adapter
        }

        adapter.setOnClickCallback(object : DataSiswaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsSiswa) {
                Intent(this@DataSiswaActivity, DetailDataSiswaActivity::class.java).also {
                    it.putExtra(DetailDataSiswaActivity.EXTRA_ID_SISWA, data.id_siswa)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_NAMA_SISWA, data.nama_siswa)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_ID_SEKOLAH, data.id_sekolah)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_PASSWORD, data.password)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_NIS, data.nis)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_NISN, data.nisn)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_TTL, data.ttl)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_JENIS_KELAMIN, data.jenis_kelamin)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_AGAMA, data.agama)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_ALAMAT, data.alamat)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_KELAS, data.kelas)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_SEMESTER, data.semester)
                    it.putExtra(DetailDataSiswaActivity.EXTRA_TAHUN_AJARAN, data.tahun_ajaran)
                    startActivity(it)
                }
            }

        })

        adapter.setOnEditClickCallback(object : DataSiswaAdapter.OnEditClickCallback {
            override fun onEditClicked(data: ResultsSiswa) {
                Intent(this@DataSiswaActivity, EditDataSiswaActivity::class.java).also {
                    it.putExtra(EditDataSiswaActivity.EXTRA_ID_SISWA, data.id_siswa)
                    it.putExtra(EditDataSiswaActivity.EXTRA_NAMA_SISWA, data.nama_siswa)
                    it.putExtra(EditDataSiswaActivity.EXTRA_ID_SEKOLAH, data.id_sekolah)
                    it.putExtra(EditDataSiswaActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(EditDataSiswaActivity.EXTRA_PASSWORD, data.password)
                    it.putExtra(EditDataSiswaActivity.EXTRA_NIS, data.nis)
                    it.putExtra(EditDataSiswaActivity.EXTRA_NISN, data.nisn)
                    it.putExtra(EditDataSiswaActivity.EXTRA_TTL, data.ttl)
                    it.putExtra(EditDataSiswaActivity.EXTRA_JENIS_KELAMIN, data.jenis_kelamin)
                    it.putExtra(EditDataSiswaActivity.EXTRA_AGAMA, data.agama)
                    it.putExtra(EditDataSiswaActivity.EXTRA_ALAMAT, data.alamat)
                    it.putExtra(EditDataSiswaActivity.EXTRA_KELAS, data.kelas)
                    it.putExtra(EditDataSiswaActivity.EXTRA_SEMESTER, data.semester)
                    it.putExtra(EditDataSiswaActivity.EXTRA_TAHUN_AJARAN, data.tahun_ajaran)
                    startActivity(it)
                }
            }

        })

    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            activityDataSiswaBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityDataSiswaBinding.progressBar.visibility = View.GONE
        }
    }

    private fun navigationListener() {
        activityDataSiswaBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@DataSiswaActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@DataSiswaActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@DataSiswaActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@DataSiswaActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}