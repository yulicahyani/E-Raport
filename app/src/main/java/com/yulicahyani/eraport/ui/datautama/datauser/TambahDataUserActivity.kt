package com.yulicahyani.eraport.ui.datautama.datauser

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
import com.yulicahyani.eraport.databinding.ActivityTambahDataSiswaBinding
import com.yulicahyani.eraport.databinding.ActivityTambahDataUserBinding
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

class TambahDataUserActivity : AppCompatActivity() {

    private lateinit var activityTambahDataUserBinding: ActivityTambahDataUserBinding
    private lateinit var viewModel: DataUserViewModel
    private lateinit var idNamaSekolah: String
    private var idSekolah by Delegates.notNull<Int>()
    lateinit var prefHelper: PrefHelper
    var listSekolah = mutableListOf<ResultsSekolah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTambahDataUserBinding = ActivityTambahDataUserBinding.inflate(layoutInflater)
        setContentView(activityTambahDataUserBinding.root)

        val item = activityTambahDataUserBinding.navigation.menu.findItem(R.id.nav_data_utama)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)
        getAllSekolah()

        activityTambahDataUserBinding.btnBatal.setOnClickListener {
            val intent = Intent(this@TambahDataUserActivity, DataUtamaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DataUserViewModel::class.java)

        activityTambahDataUserBinding.btnTambah.setOnClickListener {
            addDataUser()
        }
    }

    fun addDataUser() {
        if (TextUtils.isEmpty(activityTambahDataUserBinding.usernameEt.text.toString())) {
            activityTambahDataUserBinding.usernameEt.error = "Please enter username"
            return
        } else if (TextUtils.isEmpty(activityTambahDataUserBinding.passwordEt.text.toString())) {
            activityTambahDataUserBinding.passwordEt.error = "Please enter password"
            return
        } else if (TextUtils.isEmpty(activityTambahDataUserBinding.emailEt.text.toString())) {
            activityTambahDataUserBinding.emailEt.error = "Please enter email"
            return
        } else if (TextUtils.isEmpty(activityTambahDataUserBinding.firstNameEt.text.toString())) {
            activityTambahDataUserBinding.firstNameEt.error = "Please enter firstname"
            return
        } else if (TextUtils.isEmpty(activityTambahDataUserBinding.lastNameEt.text.toString())) {
            activityTambahDataUserBinding.lastNameEt.error = "Please enter lastname"
            return
        } else if (TextUtils.isEmpty(activityTambahDataUserBinding.roleEt.text.toString())) {
            activityTambahDataUserBinding.roleEt.error = "Please enter role"
            return
        }

        val id_sekolah = idSekolah
        val email = activityTambahDataUserBinding.emailEt.text.toString()
        val username = activityTambahDataUserBinding.usernameEt.text.toString()
        val password = activityTambahDataUserBinding.passwordEt.text.toString()
        val firstname = activityTambahDataUserBinding.firstNameEt.text.toString()
        val lastname = activityTambahDataUserBinding.lastNameEt.text.toString()
        val role = activityTambahDataUserBinding.roleEt.text.toString()

        if (id_sekolah != null && email != null && username != null && firstname != null && lastname != null && role != null) {
            viewModel.addDataUser(
                id_sekolah,
                email,
                username,
                password,
                firstname,
                lastname,
                role
            )
        }

        viewModel.getResponseCreate().observe(this, {
            if (it != null) {
                if (it.status == 1) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@TambahDataUserActivity, DataUtamaActivity::class.java)
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

        val spinner = activityTambahDataUserBinding.spinnerIdSekolah
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
                    this@TambahDataUserActivity,
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
                    Log.e("TambahDataUserActivity", response.body()!!.message)
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
        activityTambahDataUserBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@TambahDataUserActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@TambahDataUserActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@TambahDataUserActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@TambahDataUserActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}