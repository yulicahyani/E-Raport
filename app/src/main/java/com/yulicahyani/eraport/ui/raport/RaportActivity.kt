package com.yulicahyani.eraport.ui.raport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.response.ResultsSiswa
import com.yulicahyani.eraport.databinding.ActivityRaportBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.datautama.datasiswa.DataSiswaAdapter
import com.yulicahyani.eraport.ui.datautama.datasiswa.DataSiswaViewModel
import com.yulicahyani.eraport.ui.datautama.datasiswa.DetailDataSiswaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity

class RaportActivity : AppCompatActivity() {

    private  lateinit var activityRaportBinding: ActivityRaportBinding
    private lateinit var adapter: DataSiswaRaportAdapter
    private lateinit var viewModel: DataSiswaRaportViewModel
    lateinit var prefHelper : PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRaportBinding = ActivityRaportBinding.inflate(layoutInflater)
        setContentView(activityRaportBinding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Raport"

        val item = activityRaportBinding.navigation.menu.findItem(R.id.nav_raport)
        item.setChecked(true)
        navigationListener()

        prefHelper = PrefHelper(this)

        showProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DataSiswaRaportViewModel::class.java)
        viewModel.findSiswaSekolah(prefHelper.getInt(Constant.PREF_ID_USER))

        adapter = DataSiswaRaportAdapter()
        viewModel.getSiswaSekolah().observe(this, {
            if(it!=null){
                showProgressBar(false)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        })

        activityRaportBinding.apply {
            rvSiswa.layoutManager = LinearLayoutManager(this@RaportActivity)
            rvSiswa.setHasFixedSize(true)
            rvSiswa.adapter = adapter
        }


        adapter.setOnClickCallback(object : DataSiswaRaportAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsSiswa) {
                Intent(this@RaportActivity, ListRaportSemesterActivity::class.java).also {
                    it.putExtra(ListRaportSemesterActivity.EXTRA_ID_SISWA, data.id_siswa)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_NAMA_SISWA, data.nama_siswa)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_ID_SEKOLAH, data.id_sekolah)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_PASSWORD, data.password)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_NIS, data.nis)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_NISN, data.nisn)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_TTL, data.ttl)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_JENIS_KELAMIN, data.jenis_kelamin)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_AGAMA, data.agama)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_ALAMAT, data.alamat)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_KELAS, data.kelas)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_SEMESTER, data.semester)
                    it.putExtra(ListRaportSemesterActivity.EXTRA_TAHUN_AJARAN, data.tahun_ajaran)
                    startActivity(it)
                }
            }

        })
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            activityRaportBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityRaportBinding.progressBar.visibility = View.GONE
        }
    }


    private fun navigationListener() {
        activityRaportBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@RaportActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@RaportActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@RaportActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@RaportActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}