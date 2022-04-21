package com.yulicahyani.eraport.ui.datautama.datauser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.response.ResultsAllUser
import com.yulicahyani.eraport.data.source.remote.response.ResultsSiswa
import com.yulicahyani.eraport.databinding.ActivityDataSiswaBinding
import com.yulicahyani.eraport.databinding.ActivityDataUserBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.datautama.datasiswa.*
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.listmapel.ListMapelAdapter
import com.yulicahyani.eraport.ui.inputnilai.listmapel.ListMapelViewModel
import com.yulicahyani.eraport.ui.raport.RaportActivity

class DataUserActivity : AppCompatActivity() {

    private lateinit var activityDataUserBinding: ActivityDataUserBinding
    private lateinit var viewModel: DataUserViewModel
    private lateinit var adapter: DataUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDataUserBinding = ActivityDataUserBinding.inflate(layoutInflater)
        setContentView(activityDataUserBinding.root)

        val item = activityDataUserBinding.navigation.menu.findItem(R.id.nav_data_utama)
        item.isChecked = true
        navigationListener()

        activityDataUserBinding.btnTambahUser.setOnClickListener {
            val intent = Intent(this@DataUserActivity, TambahDataUserActivity::class.java)
            startActivity(intent)
        }

        showProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DataUserViewModel::class.java)
        viewModel.findUser()

        adapter = DataUserAdapter()
        viewModel.getAllUser().observe(this, {
            if(it!=null){
                showProgressBar(false)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        })

        activityDataUserBinding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@DataUserActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }


        adapter.setOnClickCallback(object : DataUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsAllUser) {
                Intent(this@DataUserActivity, DetailDataUserActivity::class.java).also {
                    it.putExtra(DetailDataUserActivity.EXTRA_ID_USER, data.id_user)
                    it.putExtra(DetailDataUserActivity.EXTRA_EMAIL, data.email)
                    it.putExtra(DetailDataUserActivity.EXTRA_ID_SEKOLAH, data.id_sekolah)
                    it.putExtra(DetailDataUserActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailDataUserActivity.EXTRA_PASSWORD, data.password)
                    it.putExtra(DetailDataUserActivity.EXTRA_FIRSTNAME, data.firstname)
                    it.putExtra(DetailDataUserActivity.EXTRA_LASTNAME, data.lastname)
                    it.putExtra(DetailDataUserActivity.EXTRA_ROLE, data.role)
                    startActivity(it)
                }
            }

        })

        adapter.setOnEditClickCallback(object : DataUserAdapter.OnEditClickCallback {
            override fun onEditClicked(data: ResultsAllUser) {
                Intent(this@DataUserActivity, EditDataUserActivity::class.java).also {
                    it.putExtra(EditDataUserActivity.EXTRA_ID_USER, data.id_user)
                    it.putExtra(EditDataUserActivity.EXTRA_EMAIL, data.email)
                    it.putExtra(EditDataUserActivity.EXTRA_ID_SEKOLAH, data.id_sekolah)
                    it.putExtra(EditDataUserActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(EditDataUserActivity.EXTRA_PASSWORD, data.password)
                    it.putExtra(EditDataUserActivity.EXTRA_FIRSTNAME, data.firstname)
                    it.putExtra(EditDataUserActivity.EXTRA_LASTNAME, data.lastname)
                    it.putExtra(EditDataUserActivity.EXTRA_ROLE, data.role)
                    startActivity(it)
                }
            }

        })
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            activityDataUserBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityDataUserBinding.progressBar.visibility = View.GONE
        }
    }

    private fun navigationListener() {
        activityDataUserBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@DataUserActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@DataUserActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@DataUserActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@DataUserActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}