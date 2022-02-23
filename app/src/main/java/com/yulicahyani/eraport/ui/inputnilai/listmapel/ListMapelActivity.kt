package com.yulicahyani.eraport.ui.inputnilai.listmapel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.response.ResultsMapel
import com.yulicahyani.eraport.databinding.ActivityListMapelBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.listsiswa.ListSiswaActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class ListMapelActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_FROM_ACTIVITY = "extra_from_activity"
    }
    private lateinit var activityListMapelBinding: ActivityListMapelBinding
    private lateinit var viewModel: ListMapelViewModel
    private lateinit var adapter: ListMapelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityListMapelBinding = ActivityListMapelBinding.inflate(layoutInflater)
        setContentView(activityListMapelBinding.root)

        val item = activityListMapelBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()

        val activity = intent.getStringExtra(ListSiswaActivity.EXTRA_FROM_ACTIVITY)

        showProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ListMapelViewModel::class.java)
        viewModel.findMapel()

        adapter = ListMapelAdapter()
        viewModel.getMapel().observe(this, {
            if(it!=null){
                showProgressBar(false)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        })

        activityListMapelBinding.apply {
            rvMapel.layoutManager = LinearLayoutManager(this@ListMapelActivity)
            rvMapel.setHasFixedSize(true)
            rvMapel.adapter = adapter
        }

        adapter.setOnClickCallback(object : ListMapelAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsMapel) {
                Intent(this@ListMapelActivity, ListSiswaActivity::class.java).also {
                    it.putExtra(ListSiswaActivity.EXTRA_FROM_ACTIVITY, activity)
                    it.putExtra(ListSiswaActivity.EXTRA_ID_MAPEL, data.id_mapel)
                    it.putExtra(ListSiswaActivity.EXTRA_CATEGORY_MAPEL, data.is_mulok)
                    startActivity(it)
                }
            }
        })
    }

    private fun navigationListener() {
        activityListMapelBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@ListMapelActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@ListMapelActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@ListMapelActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@ListMapelActivity, RaportActivity::class.java)
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
            activityListMapelBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityListMapelBinding.progressBar.visibility = View.GONE
        }
    }
}