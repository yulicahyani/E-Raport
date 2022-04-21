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
import com.yulicahyani.eraport.databinding.ActivityEditProfilBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import kotlin.properties.Delegates

class EditDataSiswaActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_NAMA_SISWA = "extra_nama_siswa"
        const val EXTRA_NAMA_PANGGIL = "extra_nama_panggil"
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

    private lateinit var activityEditDataSiswaBinding: ActivityEditDataSiswaBinding
    private lateinit var viewModel: DataSiswaViewModel
    private lateinit var idNamaSekolah: String
    private var idSekolah by Delegates.notNull<Int>()
    lateinit var prefHelper: PrefHelper
    var listSekolah = mutableListOf<ResultsSekolah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEditDataSiswaBinding = ActivityEditDataSiswaBinding.inflate(layoutInflater)
        setContentView(activityEditDataSiswaBinding.root)

        val item = activityEditDataSiswaBinding.navigation.menu.findItem(R.id.nav_data_utama)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)
        getAllSekolah()

        idSekolah = intent.getStringExtra(EXTRA_ID_SEKOLAH).toString().toInt()
        activityEditDataSiswaBinding.usernameEt.setText(intent.getStringExtra(EXTRA_USERNAME))
        activityEditDataSiswaBinding.passwordEt.setText(intent.getStringExtra(EXTRA_PASSWORD))
        activityEditDataSiswaBinding.nisEt.setText(intent.getStringExtra(EXTRA_NIS))
        activityEditDataSiswaBinding.nisnEt.setText(intent.getStringExtra(EXTRA_NISN))
        activityEditDataSiswaBinding.namaEt.setText(intent.getStringExtra(EXTRA_NAMA_SISWA))
        activityEditDataSiswaBinding.namaPanggilEt.setText(intent.getStringExtra(EXTRA_NAMA_PANGGIL))
        activityEditDataSiswaBinding.ttlEt.setText(intent.getStringExtra(EXTRA_TTL))
        activityEditDataSiswaBinding.jenisKelaminEt.setText(intent.getStringExtra(EXTRA_JENIS_KELAMIN))
        activityEditDataSiswaBinding.agamaEt.setText(intent.getStringExtra(EXTRA_AGAMA))
        activityEditDataSiswaBinding.alamatEt.setText(intent.getStringExtra(EXTRA_ALAMAT))
        activityEditDataSiswaBinding.kelasEt.setText(intent.getStringExtra(EXTRA_KELAS))
        activityEditDataSiswaBinding.semesterEt.setText(intent.getStringExtra(EXTRA_SEMESTER))
        activityEditDataSiswaBinding.tahunPelajaranEt.setText(intent.getStringExtra(EXTRA_TAHUN_AJARAN))

        activityEditDataSiswaBinding.btnBatal.setOnClickListener {
            val intent = Intent(this@EditDataSiswaActivity, DataUtamaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DataSiswaViewModel::class.java
        )
        activityEditDataSiswaBinding.btnSimpan.setOnClickListener {
            updateDataSiswa()
        }
    }

    fun updateDataSiswa() {
        if (TextUtils.isEmpty(activityEditDataSiswaBinding.usernameEt.text.toString())) {
            activityEditDataSiswaBinding.usernameEt.error = "Please enter username"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.passwordEt.text.toString())) {
            activityEditDataSiswaBinding.passwordEt.error = "Please enter password"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.nisEt.text.toString())) {
            activityEditDataSiswaBinding.nisEt.error = "Please enter nis"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.nisnEt.text.toString())) {
            activityEditDataSiswaBinding.nisnEt.error = "Please enter nisn"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.namaEt.text.toString())) {
            activityEditDataSiswaBinding.namaEt.error = "Please enter nama"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.namaPanggilEt.text.toString())) {
            activityEditDataSiswaBinding.namaPanggilEt.error = "Please enter nama panggilan"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.ttlEt.text.toString())) {
            activityEditDataSiswaBinding.ttlEt.error = "Please enter ttl"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.jenisKelaminEt.text.toString())) {
            activityEditDataSiswaBinding.jenisKelaminEt.error = "Please enter jenis kelamin"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.agamaEt.text.toString())) {
            activityEditDataSiswaBinding.agamaEt.error = "Please enter agama"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.alamatEt.text.toString())) {
            activityEditDataSiswaBinding.alamatEt.error = "Please enter alamat"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.kelasEt.text.toString())) {
            activityEditDataSiswaBinding.kelasEt.error = "Please enter kelas"
            return
        }else if (TextUtils.isEmpty(activityEditDataSiswaBinding.semesterEt.text.toString())) {
            activityEditDataSiswaBinding.semesterEt.error = "Please enter semester"
            return
        } else if (TextUtils.isEmpty(activityEditDataSiswaBinding.tahunPelajaranEt.text.toString())) {
            activityEditDataSiswaBinding.tahunPelajaranEt.error = "Please enter tahun pelajaran"
            return
        }

        val id_siswa = intent.getStringExtra(EXTRA_ID_SISWA).toString().toInt()
        val id_sekolah = idSekolah
        val username = activityEditDataSiswaBinding.usernameEt.text.toString()
        val password = activityEditDataSiswaBinding.passwordEt.text.toString()
        val nis = activityEditDataSiswaBinding.nisEt.text.toString()
        val nisn = activityEditDataSiswaBinding.nisnEt.text.toString()
        val nama_siswa = activityEditDataSiswaBinding.namaEt.text.toString()
        val nama_panggilan = activityEditDataSiswaBinding.namaPanggilEt.text.toString()
        val ttl = activityEditDataSiswaBinding.ttlEt.text.toString()
        val jenis_kelamin = activityEditDataSiswaBinding.jenisKelaminEt.text.toString()
        val agama = activityEditDataSiswaBinding.agamaEt.text.toString()
        val alamat = activityEditDataSiswaBinding.alamatEt.text.toString()
        val kelas = activityEditDataSiswaBinding.kelasEt.text.toString().toInt()
        val semester = activityEditDataSiswaBinding.semesterEt.text.toString().toInt()
        val tahun_ajaran = activityEditDataSiswaBinding.tahunPelajaranEt.text.toString()

        if (id_siswa != null && id_sekolah != null && username != null && password != null && nis != null && nisn != null && nama_siswa != null && nama_panggilan != null
            && ttl != null && jenis_kelamin != null && agama != null && alamat != null && kelas != null && semester != null && tahun_ajaran != null) {
            viewModel.updateDataSiswa(
                id_siswa,
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

        viewModel.getResponseUpdate().observe(this, {
            if (it != null) {
                if (it.status == 1) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditDataSiswaActivity, DataUtamaActivity::class.java)
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

        val spinner = activityEditDataSiswaBinding.spinnerIdSekolah
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
                    this@EditDataSiswaActivity,
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
                    Log.e("EditDataSiswaActivity", response.body()!!.message)
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
        activityEditDataSiswaBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@EditDataSiswaActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@EditDataSiswaActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@EditDataSiswaActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@EditDataSiswaActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}