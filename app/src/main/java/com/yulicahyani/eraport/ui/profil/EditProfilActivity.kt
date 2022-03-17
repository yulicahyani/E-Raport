package com.yulicahyani.eraport.ui.profil

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.ResultsSekolah
import com.yulicahyani.eraport.data.source.remote.response.SekolahResponse
import com.yulicahyani.eraport.data.source.remote.response.GeneralResponse
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


class EditProfilActivity : AppCompatActivity() {

    private lateinit var activityEditProfilBinding: ActivityEditProfilBinding
    private lateinit var idNamaSekolah: String
    private var idSekolah by Delegates.notNull<Int>()
    lateinit var prefHelper: PrefHelper
    var listSekolah = mutableListOf<ResultsSekolah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEditProfilBinding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(activityEditProfilBinding.root)

        prefHelper = PrefHelper(this)
        getAllSekolah()
        activityEditProfilBinding.usernameEt.setText(prefHelper.getString(Constant.PREF_USERNAME))
        activityEditProfilBinding.passwordEt.setText(prefHelper.getString(Constant.PREF_PASSWORD))
        activityEditProfilBinding.emailEt.setText(prefHelper.getString(Constant.PREF_EMAIL))
        activityEditProfilBinding.firstNameEt.setText(prefHelper.getString(Constant.PREF_FIRSTNAME))
        activityEditProfilBinding.lastNameEt.setText(prefHelper.getString(Constant.PREF_LASTNAME))
        activityEditProfilBinding.roleEt.setText(prefHelper.getString(Constant.PREF_ROLE))

        val item = activityEditProfilBinding.navigation.menu.findItem(R.id.nav_dashboard)
        item.isChecked = true
        navigationListener()

        activityEditProfilBinding.btnSimpan.setOnClickListener {

            if (TextUtils.isEmpty(activityEditProfilBinding.usernameEt.text.toString())) {
                activityEditProfilBinding.usernameEt.error = "Please enter username"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(activityEditProfilBinding.passwordEt.text.toString())) {
                activityEditProfilBinding.passwordEt.error = "Please enter password"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(activityEditProfilBinding.emailEt.text.toString())) {
                activityEditProfilBinding.emailEt.error = "Please enter email"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(activityEditProfilBinding.firstNameEt.text.toString())) {
                activityEditProfilBinding.firstNameEt.error = "Please enter firstname"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(activityEditProfilBinding.lastNameEt.text.toString())) {
                activityEditProfilBinding.lastNameEt.error = "Please enter lastname"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(activityEditProfilBinding.roleEt.text.toString())) {
                activityEditProfilBinding.roleEt.error = "Please enter role"
                return@setOnClickListener
            }

            updateUser()

            val intent = Intent(this@EditProfilActivity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        activityEditProfilBinding.btnBatal.setOnClickListener {
            val intent = Intent(this@EditProfilActivity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    private fun setupSpinner() {
        val arrayIdSekolah = listSekolah.map { it.id_sekolah }.toTypedArray()
        val arrayNamaSekolah = listSekolah.map {
            StringBuilder().append(it.id_sekolah).append("-").append(it.nama_sekolah).toString()
        }.toTypedArray()

        val spinner = activityEditProfilBinding.spinnerIdSekolah
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
                    this@EditProfilActivity,
                    " $idSekolah",
                    Toast.LENGTH_SHORT
                ).show()


            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun navigationListener() {
        activityEditProfilBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@EditProfilActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@EditProfilActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@EditProfilActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@EditProfilActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
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
                    Log.e("EditProfilActivity", response.body()!!.message)
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

    private fun updateUser() {
        val client = ApiConfig.getApiService().updateUser(
            id_user = prefHelper.getInt(Constant.PREF_ID_USER),
            id_sekolah = idSekolah,
            email = activityEditProfilBinding.emailEt.text.toString(),
            username = activityEditProfilBinding.usernameEt.text.toString(),
            password = activityEditProfilBinding.passwordEt.text.toString(),
            firsname = activityEditProfilBinding.firstNameEt.text.toString(),
            lastname = activityEditProfilBinding.lastNameEt.text.toString(),
            role = activityEditProfilBinding.roleEt.text.toString()
        )
        client.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 1) {
                        updateUserSharePref()
                    }

                    Toast.makeText(
                        this@EditProfilActivity,
                        response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun updateUserSharePref() {
        prefHelper.put(Constant.PREF_ID_SEKOLAH, idSekolah)
        prefHelper.put(Constant.PREF_EMAIL, activityEditProfilBinding.emailEt.text.toString())
        prefHelper.put(Constant.PREF_USERNAME, activityEditProfilBinding.usernameEt.text.toString())
        prefHelper.put(Constant.PREF_PASSWORD, activityEditProfilBinding.passwordEt.text.toString())
        prefHelper.put(
            Constant.PREF_FIRSTNAME,
            activityEditProfilBinding.firstNameEt.text.toString()
        )
        prefHelper.put(Constant.PREF_LASTNAME, activityEditProfilBinding.lastNameEt.text.toString())
        prefHelper.put(Constant.PREF_ROLE, activityEditProfilBinding.roleEt.text.toString())
        prefHelper.put(
            Constant.PREF_SEKOLAH,
            listSekolah.find { it.id_sekolah.toInt() == idSekolah }?.nama_sekolah.toString()
        )
        prefHelper.put(
            Constant.PREF_ALAMAT_SEKOLAH,
            listSekolah.find { it.id_sekolah.toInt() == idSekolah }?.alamat.toString()
        )
    }

}