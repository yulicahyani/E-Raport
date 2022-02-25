package com.yulicahyani.eraport.ui.datautama.datasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.ResultsSekolah
import com.yulicahyani.eraport.data.source.remote.response.SekolahResponse
import com.yulicahyani.eraport.databinding.ActivityDetailDataSiswaBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class DetailDataSiswaActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_NAMA_SISWA = "extra_nama_siswa"
        const val EXTRA_ID_SEKOLAH = "extra_id_sekolah"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_PASSWORD = "extra_password"
        const val EXTRA_NIS = "extra_nis"
        const val EXTRA_NISN = "extra_nisn"
        const val EXTRA_TTL = "extra_ttl"
        const val EXTRA_JENIS_KELAMIN= "extra_jenis_kelamin"
        const val EXTRA_AGAMA = "extra_agama"
        const val EXTRA_ALAMAT = "extra_alamat"
        const val EXTRA_KELAS = "extra_kelas"
        const val EXTRA_SEMESTER = "extra_semester"
        const val EXTRA_TAHUN_AJARAN= "extra_tahun_ajaran"
    }

    private lateinit var activityDetailDataSiswaBinding: ActivityDetailDataSiswaBinding
    lateinit var prefHelper : PrefHelper
    var listSekolah = mutableListOf<ResultsSekolah>()
    private var idSekolah by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailDataSiswaBinding = ActivityDetailDataSiswaBinding.inflate(layoutInflater)
        setContentView(activityDetailDataSiswaBinding.root)

        val item = activityDetailDataSiswaBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)

        getAllSekolah()

//        idSekolah = prefHelper.getInt(Constant.PREF_ID_SEKOLAH)
//
//        val nama_sekolah = listSekolah.find { it.id_sekolah.toInt() == idSekolah }?.nama_sekolah.toString()

        val nis_nisn = StringBuilder()
        activityDetailDataSiswaBinding.nama.text = intent.getStringExtra(EXTRA_NAMA_SISWA)
        activityDetailDataSiswaBinding.agama.text = intent.getStringExtra(EXTRA_AGAMA)
        activityDetailDataSiswaBinding.alamatSiswa.text = intent.getStringExtra(EXTRA_ALAMAT)
        activityDetailDataSiswaBinding.jenisKelamin.text = intent.getStringExtra(EXTRA_JENIS_KELAMIN)
        activityDetailDataSiswaBinding.kelas.text = intent.getStringExtra(EXTRA_KELAS)
        activityDetailDataSiswaBinding.nis.text = nis_nisn.append(intent.getStringExtra(EXTRA_NIS)).append("/").append(intent.getStringExtra(EXTRA_NISN))
        activityDetailDataSiswaBinding.password.text = intent.getStringExtra(EXTRA_PASSWORD)
        activityDetailDataSiswaBinding.semester.text = intent.getStringExtra(EXTRA_SEMESTER)
        activityDetailDataSiswaBinding.username.text = intent.getStringExtra(EXTRA_USERNAME)
        activityDetailDataSiswaBinding.tahunPelajaran.text = intent.getStringExtra(EXTRA_TAHUN_AJARAN)
        activityDetailDataSiswaBinding.ttl.text = intent.getStringExtra(EXTRA_TTL)
        activityDetailDataSiswaBinding.namaSekolah.text = prefHelper.getString(Constant.PREF_SEKOLAH)
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
                    Log.e("DetailDataSiswaActivity", response.body()!!.message)

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
        activityDetailDataSiswaBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@DetailDataSiswaActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@DetailDataSiswaActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@DetailDataSiswaActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@DetailDataSiswaActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}