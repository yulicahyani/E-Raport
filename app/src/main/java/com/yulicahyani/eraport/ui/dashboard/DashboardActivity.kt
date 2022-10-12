package com.yulicahyani.eraport.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.MapelResponse
import com.yulicahyani.eraport.data.source.remote.response.SiswaResponse
import com.yulicahyani.eraport.data.source.remote.response.UserResponse
import com.yulicahyani.eraport.databinding.ActivityDashboardBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.datautama.datasiswa.DataSiswaViewModel
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.profil.ProfilActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var activityDashboardBinding: ActivityDashboardBinding
    private lateinit var viewModel: DashboardViewModel
    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(activityDashboardBinding.root)

        supportActionBar?.hide()
        val item = activityDashboardBinding.navigation.menu.findItem(R.id.nav_dashboard)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)
        val fullName = StringBuilder()
        activityDashboardBinding.tvNama.text = fullName.append("Hai, ").append(prefHelper.getString(Constant.PREF_FIRSTNAME)).append("!")
        activityDashboardBinding.smCount.startShimmer()
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DashboardViewModel::class.java)
        viewModel.calculateCountMapel()
        viewModel.calculateCountUser()
        viewModel.calculateCountUser()
        viewModel.calculateCountSiswa(prefHelper.getInt(Constant.PREF_ID_USER))

        //Count Dashboard
        viewModel.getCountMapel().observe(this) { countMapel ->
            if (countMapel.isNotBlank() && countMapel.isEmpty()) {
                activityDashboardBinding.smCount.apply {
                    stopShimmer()
                    visibility = View.GONE
                }
                activityDashboardBinding.layoutCount.visibility = View.VISIBLE
                val countStringMapel = StringBuilder()
                activityDashboardBinding.countMapel.text = countStringMapel.append(countMapel).append(" ").append("Mapel")
            }
        }

        viewModel.getCountSiswa().observe(this) { countSiswa ->
            if (countSiswa.isNotBlank() && countSiswa.isNotEmpty()) {
                val countStringSiswa = StringBuilder()
                activityDashboardBinding.countSiswa.text = countStringSiswa.append(countSiswa)
            }
        }

        viewModel.getCountUser().observe(this) { countUser ->
            if (countUser.isNotBlank() && countUser.isNotEmpty()) {
                val countStringUser = StringBuilder()
                activityDashboardBinding.countUser.text = countStringUser.append(countUser).append(" ").append("User")
            }
        }

        activityDashboardBinding.user.setOnClickListener {
            val intent = Intent(this@DashboardActivity, ProfilActivity::class.java)
            startActivity(intent)
        }

        activityDashboardBinding.tvNama.setOnClickListener {
            val intent = Intent(this@DashboardActivity, ProfilActivity::class.java)
            startActivity(intent)
        }
    }


    private fun navigationListener() {
        activityDashboardBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@DashboardActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@DashboardActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@DashboardActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@DashboardActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}