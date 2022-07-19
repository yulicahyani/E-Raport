package com.yulicahyani.eraport.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.MapelResponse
import com.yulicahyani.eraport.data.source.remote.response.SiswaResponse
import com.yulicahyani.eraport.data.source.remote.response.UserResponse
import com.yulicahyani.eraport.databinding.ActivityDashboardBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.profil.ProfilActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var activityDashboardBinding: ActivityDashboardBinding
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

        //Count Dashboard
        ApiConfig.getApiService().getMapel().enqueue(object : Callback<MapelResponse> {
            override fun onResponse(call: Call<MapelResponse>, response: Response<MapelResponse>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        val countStringMapel = StringBuilder()
                        activityDashboardBinding.countMapel.text = countStringMapel.append(response.body()?.mapels?.size.toString()).append(" ").append("Mapel")
                    }
                }
            }
            override fun onFailure(call: Call<MapelResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }
        })

        ApiConfig.getApiService().getAllUser().enqueue(object :Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        val countStringUser = StringBuilder()
                        activityDashboardBinding.countUser.text = countStringUser.append(response.body()?.user?.size.toString()).append(" ").append("User")
                    }
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }
        })

        ApiConfig.getApiService().getSiswaSekolah(prefHelper.getInt(Constant.PREF_ID_USER)).enqueue(object :Callback<SiswaResponse>{
            override fun onResponse(call: Call<SiswaResponse>, response: Response<SiswaResponse>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        val countStringSiswa = StringBuilder()
                        activityDashboardBinding.countSiswa.text = countStringSiswa.append(response.body()?.siswa?.size.toString()).append(" ").append("Siswa")
                    }
                }
            }
            override fun onFailure(call: Call<SiswaResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }
        })

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