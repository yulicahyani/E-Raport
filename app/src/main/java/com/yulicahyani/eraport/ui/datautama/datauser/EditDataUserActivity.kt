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
import com.yulicahyani.eraport.databinding.ActivityEditDataSiswaBinding
import com.yulicahyani.eraport.databinding.ActivityEditDataUserBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.datautama.datasiswa.DataSiswaViewModel
import com.yulicahyani.eraport.ui.datautama.datasiswa.EditDataSiswaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import kotlin.properties.Delegates

class EditDataUserActivity : AppCompatActivity() {

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
    private lateinit var activityEditDataUserBinding: ActivityEditDataUserBinding
    private lateinit var viewModel: DataUserViewModel
    private lateinit var idNamaSekolah: String
    var idSekolah by Delegates.notNull<Int>()
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

        idSekolah = intent.getStringExtra(EXTRA_ID_SEKOLAH).toString().toInt()
        activityEditDataUserBinding.emailEt.setText(intent.getStringExtra(EXTRA_EMAIL))
        activityEditDataUserBinding.usernameEt.setText(intent.getStringExtra(
            EXTRA_USERNAME
        ))
        activityEditDataUserBinding.passwordEt.setText(intent.getStringExtra(
            EXTRA_PASSWORD
        ))
        activityEditDataUserBinding.firstNameEt.setText(intent.getStringExtra(EXTRA_FIRSTNAME))
        activityEditDataUserBinding.lastNameEt.setText(intent.getStringExtra(EXTRA_LASTNAME))
        activityEditDataUserBinding.roleEt.setText(intent.getStringExtra(EXTRA_ROLE))

        activityEditDataUserBinding.btnBatal.setOnClickListener {
            val intent = Intent(this@EditDataUserActivity, DataUtamaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DataUserViewModel::class.java
        )
        activityEditDataUserBinding.btnSimpan.setOnClickListener {
            updateDataUser()
        }
    }

    fun updateDataUser() {
        if (TextUtils.isEmpty(activityEditDataUserBinding.usernameEt.text.toString())) {
            activityEditDataUserBinding.usernameEt.error = "Please enter username"
            return
        } else if (TextUtils.isEmpty(activityEditDataUserBinding.passwordEt.text.toString())) {
            activityEditDataUserBinding.passwordEt.error = "Please enter password"
            return
        } else if (TextUtils.isEmpty(activityEditDataUserBinding.emailEt.text.toString())) {
            activityEditDataUserBinding.emailEt.error = "Please enter email"
            return
        } else if (TextUtils.isEmpty(activityEditDataUserBinding.firstNameEt.text.toString())) {
            activityEditDataUserBinding.firstNameEt.error = "Please enter firstname"
            return
        } else if (TextUtils.isEmpty(activityEditDataUserBinding.lastNameEt.text.toString())) {
            activityEditDataUserBinding.lastNameEt.error = "Please enter lastname"
            return
        } else if (TextUtils.isEmpty(activityEditDataUserBinding.roleEt.text.toString())) {
            activityEditDataUserBinding.roleEt.error = "Please enter role"
            return
        }

        val id_user = intent.getStringExtra(EditDataUserActivity.EXTRA_ID_USER).toString().toInt()
        val id_sekolah = idSekolah
        val email = activityEditDataUserBinding.emailEt.text.toString()
        val username = activityEditDataUserBinding.usernameEt.text.toString()
        val password = activityEditDataUserBinding.passwordEt.text.toString()
        val firstname = activityEditDataUserBinding.firstNameEt.text.toString()
        val lastname = activityEditDataUserBinding.lastNameEt.text.toString()
        val role = activityEditDataUserBinding.roleEt.text.toString()

        if (id_user != null && id_sekolah != null && email != null && username != null && firstname != null && lastname != null && role != null) {
            viewModel.updateDataUser(
                id_user,
                id_sekolah,
                email,
                username,
                password,
                firstname,
                lastname,
                role
            )
        }

        viewModel.getResponseUpdate().observe(this, {
            if (it != null) {
                if (it.status == 1) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditDataUserActivity, DataUtamaActivity::class.java)
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

        val spinner = activityEditDataUserBinding.spinnerIdSekolah
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayNamaSekolah)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        idNamaSekolah =
            StringBuilder().append(idSekolah.toString())
                .append("-").append(listSekolah.find { it.id_sekolah.toInt() == idSekolah }?.nama_sekolah.toString())
                .toString()
//        idSekolah = prefHelper.getInt(Constant.PREF_ID_SEKOLAH)
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