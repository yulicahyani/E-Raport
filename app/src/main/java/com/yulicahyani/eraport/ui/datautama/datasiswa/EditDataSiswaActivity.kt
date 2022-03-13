package com.yulicahyani.eraport.ui.datautama.datasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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

        activityEditDataSiswaBinding.btnBatal.setOnClickListener {
            val intent = Intent(this@EditDataSiswaActivity, DataUtamaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
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