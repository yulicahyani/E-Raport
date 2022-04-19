package com.yulicahyani.eraport.ui.inputnilai.listsiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.response.ResultsSiswa
import com.yulicahyani.eraport.databinding.ActivityListSiswaBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.*
import com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan.NilaiKeterampilanActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan.nilaiketerampilansiswa.NilaiKeterampilanSiswaActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan.GradeTypeActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaisikapsosial.NilaiSosialActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaisikapspiritual.NilaiSpiritualActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class ListSiswaActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_FROM_ACTIVITY = "extra_from_activity"
        const val EXTRA_ID_MAPEL = "extra_id_mapel"
        const val EXTRA_CATEGORY_MAPEL = "extra_category_mapel"
    }

    private lateinit var activityListSiswaBinding: ActivityListSiswaBinding
    private lateinit var adapter: ListSiswaAdapter
    private lateinit var viewModel: ListSiswaViewModel
    lateinit var prefHelper : PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityListSiswaBinding = ActivityListSiswaBinding.inflate(layoutInflater)
        setContentView(activityListSiswaBinding.root)

        val item = activityListSiswaBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)

        val activity = intent.getStringExtra(EXTRA_FROM_ACTIVITY)

        showProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ListSiswaViewModel::class.java)
        viewModel.findSiswaSekolah(prefHelper.getInt(Constant.PREF_ID_USER))

        adapter = ListSiswaAdapter()
        viewModel.getSiswaSekolah().observe(this, {
            if(it!=null){
                showProgressBar(false)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        })

        activityListSiswaBinding.apply {
            rvSiswa.layoutManager = LinearLayoutManager(this@ListSiswaActivity)
            rvSiswa.setHasFixedSize(true)
            rvSiswa.adapter = adapter
        }

        adapter.setOnClickCallback(object : ListSiswaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsSiswa) {
                when (activity){
                    "Spiritual" -> {
                        Intent(this@ListSiswaActivity, NilaiSpiritualActivity::class.java).also {
                            it.putExtra(NilaiSpiritualActivity.EXTRA_ID_SISWA, data.id_siswa)
                            it.putExtra(NilaiSpiritualActivity.EXTRA_NAMA_SISWA, data.nama_siswa)
                            startActivity(it)
                        }
                    }

                    "Sosial" -> {
                        Intent(this@ListSiswaActivity, NilaiSosialActivity::class.java).also {
                            it.putExtra(NilaiSosialActivity.EXTRA_ID_SISWA, data.id_siswa)
                            it.putExtra(NilaiSosialActivity.EXTRA_NAMA_SISWA, data.nama_siswa)
                            startActivity(it)
                        }
                    }

                    "Pengetahuan" -> {
                        Intent(this@ListSiswaActivity, GradeTypeActivity::class.java).also {
                            it.putExtra(GradeTypeActivity.EXTRA_ID_SISWA, data.id_siswa)
                            it.putExtra(
                                GradeTypeActivity.EXTRA_ID_MAPEL, intent.getStringExtra(
                                EXTRA_ID_MAPEL
                                ))
                            it.putExtra(
                                GradeTypeActivity.EXTRA_CATEGORY_MAPEL, intent.getStringExtra(
                                EXTRA_CATEGORY_MAPEL
                                ))
                            startActivity(it)
                        }
                    }

                    "Keterampilan" -> {
                        Intent(this@ListSiswaActivity, NilaiKeterampilanSiswaActivity::class.java).also {
                            it.putExtra(NilaiKeterampilanSiswaActivity.EXTRA_ID_SISWA, data.id_siswa)
                            it.putExtra(
                                NilaiKeterampilanSiswaActivity.EXTRA_ID_MAPEL, intent.getStringExtra(
                                    EXTRA_ID_MAPEL
                                ))
                            startActivity(it)
                        }
                    }
                }
            }


        })


    }

    private fun navigationListener() {
        activityListSiswaBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@ListSiswaActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@ListSiswaActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@ListSiswaActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@ListSiswaActivity, RaportActivity::class.java)
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
            activityListSiswaBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityListSiswaBinding.progressBar.visibility = View.GONE
        }
    }




}