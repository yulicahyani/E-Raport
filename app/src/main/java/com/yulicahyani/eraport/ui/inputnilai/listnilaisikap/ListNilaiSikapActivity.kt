package com.yulicahyani.eraport.ui.inputnilai.listnilaisikap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.response.ResultsListNilaiSikap
import com.yulicahyani.eraport.databinding.ActivityListNilaiSikapBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaisikapsosial.NilaiSosialActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaisikapspiritual.NilaiSpiritualActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class ListNilaiSikapActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_FROM_ACTIVITY = "extra_from_activity"
    }

    private lateinit var activityListNilaiSikapBinding: ActivityListNilaiSikapBinding
    private lateinit var adapter: ListNilaiSikapAdapter
    private lateinit var viewModel: ListNilaiSikapViewModel
    lateinit var prefHelper : PrefHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityListNilaiSikapBinding = ActivityListNilaiSikapBinding.inflate(layoutInflater)
        setContentView(activityListNilaiSikapBinding.root)

        val item = activityListNilaiSikapBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()
        showProgressBar(true)

        val activity = intent.getStringExtra(EXTRA_FROM_ACTIVITY)
        prefHelper = PrefHelper(this)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ListNilaiSikapViewModel::class.java)
        adapter = ListNilaiSikapAdapter()

        when (activity){
            "SikapSpiritual" -> {
                viewModel.findListNilaiSpritual(prefHelper.getInt(Constant.PREF_ID_USER))
                viewModel.getSikapSpiritualSiswa().observe(this, {
                    if(it!=null){
                        showProgressBar(false)
                        adapter.setData(it)
                        adapter.notifyDataSetChanged()
                    }
                })
            }

            "SikapSosial" -> {
                viewModel.findListNilaiSosial(prefHelper.getInt(Constant.PREF_ID_USER))
                viewModel.getSikapSosialSiswa().observe(this, {
                    if(it!=null){
                        showProgressBar(false)
                        adapter.setData(it)
                        adapter.notifyDataSetChanged()
                    }
                })
            }
        }

        activityListNilaiSikapBinding.apply {
            rvNilaiSikap.layoutManager = LinearLayoutManager(this@ListNilaiSikapActivity)
            rvNilaiSikap.setHasFixedSize(true)
            rvNilaiSikap.adapter = adapter
        }

        adapter.setOnClickCallback(object : ListNilaiSikapAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsListNilaiSikap) {
                when (activity){
                    "SikapSpiritual" -> {
                        Intent(this@ListNilaiSikapActivity, NilaiSpiritualActivity::class.java).also {
                            it.putExtra(NilaiSpiritualActivity.EXTRA_ID_SISWA, data.id_siswa)
                            it.putExtra(NilaiSpiritualActivity.EXTRA_NAMA_SISWA, data.nama_siswa)
                            it.putExtra(NilaiSpiritualActivity.EXTRA_DESCRIPTION, data.deskripsi)
                            startActivity(it)
                        }
                    }
                    "SikapSosial" -> {
                        Intent(this@ListNilaiSikapActivity, NilaiSosialActivity::class.java).also {
                            it.putExtra(NilaiSosialActivity.EXTRA_ID_SISWA, data.id_siswa)
                            it.putExtra(NilaiSosialActivity.EXTRA_NAMA_SISWA, data.nama_siswa)
                            it.putExtra(NilaiSosialActivity.EXTRA_DESCRIPTION, data.deskripsi)
                            startActivity(it)
                        }
                    }
                }
            }
        })

    }

    private fun navigationListener() {
        activityListNilaiSikapBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@ListNilaiSikapActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@ListNilaiSikapActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@ListNilaiSikapActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@ListNilaiSikapActivity, RaportActivity::class.java)
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
            activityListNilaiSikapBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityListNilaiSikapBinding.progressBar.visibility = View.GONE
        }
    }
}