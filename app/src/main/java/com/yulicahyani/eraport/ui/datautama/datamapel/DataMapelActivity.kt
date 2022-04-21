package com.yulicahyani.eraport.ui.datautama.datamapel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityDataMapelBinding
import com.yulicahyani.eraport.databinding.ActivityDataSiswaBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.listmapel.ListMapelAdapter
import com.yulicahyani.eraport.ui.inputnilai.listmapel.ListMapelViewModel
import com.yulicahyani.eraport.ui.raport.RaportActivity

class DataMapelActivity : AppCompatActivity() {

    private lateinit var activityDataMapelBinding: ActivityDataMapelBinding
    private lateinit var viewModel: DataMapelViewModel
    private lateinit var adapter: DataMapelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDataMapelBinding = ActivityDataMapelBinding.inflate(layoutInflater)
        setContentView(activityDataMapelBinding.root)

        val item = activityDataMapelBinding.navigation.menu.findItem(R.id.nav_data_utama)
        item.isChecked = true
        navigationListener()

        showProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DataMapelViewModel::class.java)
        viewModel.findMapel()

        adapter = DataMapelAdapter()
        viewModel.getMapel().observe(this, {
            if(it!=null){
                showProgressBar(false)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        })

        activityDataMapelBinding.apply {
            rvMapel.layoutManager = LinearLayoutManager(this@DataMapelActivity)
            rvMapel.setHasFixedSize(true)
            rvMapel.adapter = adapter
        }
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            activityDataMapelBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityDataMapelBinding.progressBar.visibility = View.GONE
        }
    }

    private fun navigationListener() {
        activityDataMapelBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@DataMapelActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@DataMapelActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@DataMapelActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@DataMapelActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}