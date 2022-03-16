package com.yulicahyani.eraport.ui.datautama.datauser

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
import com.yulicahyani.eraport.databinding.ActivityEditDataUserBinding
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

class EditDataUserActivity : AppCompatActivity() {

    private lateinit var activityEditDataUserBinding: ActivityEditDataUserBinding
    private lateinit var idNamaSekolah: String
    private var idSekolah by Delegates.notNull<Int>()
    lateinit var prefHelper: PrefHelper
    var listSekolah = mutableListOf<ResultsSekolah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEditDataUserBinding = ActivityEditDataUserBinding.inflate(layoutInflater)
        setContentView(activityEditDataUserBinding.root)

        val item = activityEditDataUserBinding.navigation.menu.findItem(R.id.nav_data_utama)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)
        getAllSekolah()

        activityEditDataUserBinding.btnBatal.setOnClickListener {
            val intent = Intent(this@EditDataUserActivity, DataUtamaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun setupSpinner() {
        val arrayIdSekolah = listSekolah.map { it.id_sekolah }.toTypedArray()
        val arrayNamaSekolah = listSekolah.map {
            StringBuilder().append(it.id_sekolah).append("-").append(it.nama_sekolah).toString()
        }.toTypedArray()

        val spinner = activityEditDataUserBinding.spinnerIdSekolah
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
                    this@EditDataUserActivity,
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
                    Log.e("EditDataUserActivity", response.body()!!.message)
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
        activityEditDataUserBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@EditDataUserActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@EditDataUserActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@EditDataUserActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@EditDataUserActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}