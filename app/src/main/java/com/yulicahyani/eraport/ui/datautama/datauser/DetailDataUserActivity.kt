package com.yulicahyani.eraport.ui.datautama.datauser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.ResultsSekolah
import com.yulicahyani.eraport.data.source.remote.response.SekolahResponse
import com.yulicahyani.eraport.databinding.ActivityDataSiswaBinding
import com.yulicahyani.eraport.databinding.ActivityDetailDataSiswaBinding
import com.yulicahyani.eraport.databinding.ActivityDetailDataUserBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.datautama.datasiswa.DataSiswaAdapter
import com.yulicahyani.eraport.ui.datautama.datasiswa.DataSiswaViewModel
import com.yulicahyani.eraport.ui.datautama.datasiswa.DetailDataSiswaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class DetailDataUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID_USER = "extra_id_user"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_ID_SEKOLAH = "extra_id_sekolah"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_PASSWORD = "extra_password"
        const val EXTRA_FIRSTNAME = "extra_firstname"
        const val EXTRA_LASTNAME = "extra_lastname"
        const val EXTRA_ROLE = "extra_role"
    }

    private lateinit var activityDetailDataUserBinding: ActivityDetailDataUserBinding
    lateinit var prefHelper : PrefHelper
    var listSekolah = mutableListOf<ResultsSekolah>()
    var idSekolah by Delegates.notNull<Int>()
    lateinit var nama_sekolah: String
    lateinit var alamat_sekolah: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailDataUserBinding = ActivityDetailDataUserBinding.inflate(layoutInflater)
        setContentView(activityDetailDataUserBinding.root)

        val item = activityDetailDataUserBinding.navigation.menu.findItem(R.id.nav_data_utama)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)

        getAllSekolah()

        idSekolah = intent.getStringExtra(EXTRA_ID_SEKOLAH).toString().toInt()

        val nama = StringBuilder()
        activityDetailDataUserBinding.email.text = intent.getStringExtra(EXTRA_EMAIL)
        activityDetailDataUserBinding.username.text = intent.getStringExtra(
            EXTRA_USERNAME
        )
        activityDetailDataUserBinding.password.text = intent.getStringExtra(
            EXTRA_PASSWORD
        )
        activityDetailDataUserBinding.nama.text = nama.append(intent.getStringExtra(
            EXTRA_FIRSTNAME
        )).append(" ").append(intent.getStringExtra(EXTRA_LASTNAME))

    }

    private fun getAllSekolah() {
        val client = ApiConfig.getApiService().getAllSekolah()
        client.enqueue(object : Callback<SekolahResponse> {
            override fun onResponse(
                call: Call<SekolahResponse>,
                response: Response<SekolahResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()!!.sekolah.forEach {
                        listSekolah.add(it)
                    }
                    Log.e("listSekolah", listSekolah.size.toString())
                    Log.e("DetailDataUserActivity", response.body()!!.message)
                    nama_sekolah = listSekolah.find { it.id_sekolah.toInt() == idSekolah }?.nama_sekolah.toString()
                    alamat_sekolah = listSekolah.find { it.id_sekolah.toInt() == idSekolah }?.alamat.toString()
                    activityDetailDataUserBinding.namaSekolah.text = nama_sekolah
                    activityDetailDataUserBinding.alamatSekolah.text = alamat_sekolah

                } else {
                    Log.e("", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SekolahResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })

    }

    private fun navigationListener() {
        activityDetailDataUserBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@DetailDataUserActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@DetailDataUserActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@DetailDataUserActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@DetailDataUserActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}