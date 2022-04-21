package com.yulicahyani.eraport.ui.datautama.datasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.ResultsSekolah
import com.yulicahyani.eraport.data.source.remote.response.SekolahResponse
import com.yulicahyani.eraport.databinding.ActivityEditDataSiswaBinding
import com.yulicahyani.eraport.databinding.ActivityTambahDataSiswaBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan.NilaiPengetahuanViewModel
import com.yulicahyani.eraport.ui.raport.RaportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import kotlin.properties.Delegates

class TambahDataSiswaActivity : AppCompatActivity() {

    private lateinit var activityTambahDataSiswaBinding: ActivityTambahDataSiswaBinding
    private lateinit var viewModel: DataSiswaViewModel
    private lateinit var idNamaSekolah: String
    private var idSekolah by Delegates.notNull<Int>()
    lateinit var prefHelper: PrefHelper
    var listSekolah = mutableListOf<ResultsSekolah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTambahDataSiswaBinding = ActivityTambahDataSiswaBinding.inflate(layoutInflater)
        setContentView(activityTambahDataSiswaBinding.root)

        val item = activityTambahDataSiswaBinding.navigation.menu.findItem(R.id.nav_data_utama)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)
        getAllSekolah()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DataSiswaViewModel::class.java
        )

        activityTambahDataSiswaBinding.btnBatal.setOnClickListener {
            val intent = Intent(this@TambahDataSiswaActivity, DataUtamaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        activityTambahDataSiswaBinding.btnTambah.setOnClickListener {
            addDataSiswa()
        }
    }

    fun addDataSiswa() {
        if (TextUtils.isEmpty(activityTambahDataSiswaBinding.usernameEt.text.toString())) {
            activityTambahDataSiswaBinding.usernameEt.error = "Please enter username"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.passwordEt.text.toString())) {
            activityTambahDataSiswaBinding.passwordEt.error = "Please enter password"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.nisEt.text.toString())) {
            activityTambahDataSiswaBinding.nisEt.error = "Please enter nis"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.nisnEt.text.toString())) {
            activityTambahDataSiswaBinding.nisnEt.error = "Please enter nisn"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.namaEt.text.toString())) {
            activityTambahDataSiswaBinding.namaEt.error = "Please enter nama"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.namaPanggilEt.text.toString())) {
            activityTambahDataSiswaBinding.namaPanggilEt.error = "Please enter nama panggilan"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.ttlEt.text.toString())) {
            activityTambahDataSiswaBinding.ttlEt.error = "Please enter ttl"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.jenisKelaminEt.text.toString())) {
            activityTambahDataSiswaBinding.jenisKelaminEt.error = "Please enter jenis kelamin"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.agamaEt.text.toString())) {
            activityTambahDataSiswaBinding.agamaEt.error = "Please enter agama"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.alamatEt.text.toString())) {
            activityTambahDataSiswaBinding.alamatEt.error = "Please enter alamat"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.kelasEt.text.toString())) {
            activityTambahDataSiswaBinding.kelasEt.error = "Please enter kelas"
            return
        }else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.semesterEt.text.toString())) {
            activityTambahDataSiswaBinding.semesterEt.error = "Please enter semester"
            return
        } else if (TextUtils.isEmpty(activityTambahDataSiswaBinding.tahunPelajaranEt.text.toString())) {
            activityTambahDataSiswaBinding.tahunPelajaranEt.error = "Please enter tahun pelajaran"
            return
        }

        val id_sekolah = idSekolah
        val username = activityTambahDataSiswaBinding.usernameEt.text.toString()
        val password = activityTambahDataSiswaBinding.passwordEt.text.toString()
        val nis = activityTambahDataSiswaBinding.nisEt.text.toString()
        val nisn = activityTambahDataSiswaBinding.nisnEt.text.toString()
        val nama_siswa = activityTambahDataSiswaBinding.namaEt.text.toString()
        val nama_panggilan = activityTambahDataSiswaBinding.namaPanggilEt.text.toString()
        val ttl = activityTambahDataSiswaBinding.ttlEt.text.toString()
        val jenis_kelamin = activityTambahDataSiswaBinding.jenisKelaminEt.text.toString()
        val agama = activityTambahDataSiswaBinding.agamaEt.text.toString()
        val alamat = activityTambahDataSiswaBinding.alamatEt.text.toString()
        val kelas = activityTambahDataSiswaBinding.kelasEt.text.toString().toInt()
        val semester = activityTambahDataSiswaBinding.semesterEt.text.toString().toInt()
        val tahun_ajaran = activityTambahDataSiswaBinding.tahunPelajaranEt.text.toString()

        if (id_sekolah != null && username != null && password != null && nis != null && nisn != null && nama_siswa != null && nama_panggilan != null
            && ttl != null && jenis_kelamin != null && agama != null && alamat != null && kelas != null && semester != null && tahun_ajaran != null) {
            viewModel.addDataSiswa(
                id_sekolah,
                username,
                password,
                nis,
                nisn,
                nama_siswa,
                nama_panggilan,
                ttl,
                jenis_kelamin,
                agama,
                alamat,
                kelas,
                semester,
                tahun_ajaran
            )
        }

        viewModel.getResponseCreate().observe(this, {
            if (it != null) {
                if (it.status == 1) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@TambahDataSiswaActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    private fun setupSpinner() {
        val arrayIdSekolah = listSekolah.map { it.id_sekolah }.toTypedArray()
        val arrayNamaSekolah = listSekolah.map {
            StringBuilder().append(it.id_sekolah).append("-").append(it.nama_sekolah).toString()
        }.toTypedArray()

        val spinner = activityTambahDataSiswaBinding.spinnerIdSekolah
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayNamaSekolah)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        idNamaSekolah =
            StringBuilder().append(prefHelper.getInt(Constant.PREF_ID_SEKOLAH).toString())
                .append("-").append(prefHelper.getString(Constant.PREF_SEKOLAH)).toString()
        idSekolah = prefHelper.getInt(Constant.PREF_ID_SEKOLAH)
        arrayNamaSekolah.forEachIndexed { index, id ->
            if (idNamaSekolah == id) {
                spinner.setSelection(index)
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                idSekolah = arrayIdSekolah[position].toInt()
                Toast.makeText(
                    this@TambahDataSiswaActivity,
                    " $idSekolah",
                    Toast.LENGTH_SHORT
                ).show()


            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
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
                    Log.e("TambahDataSiswaActivity", response.body()!!.message)
                    setupSpinner()
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
        activityTambahDataSiswaBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@TambahDataSiswaActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@TambahDataSiswaActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@TambahDataSiswaActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@TambahDataSiswaActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}