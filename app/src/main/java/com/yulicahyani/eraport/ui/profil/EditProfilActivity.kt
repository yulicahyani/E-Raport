package com.yulicahyani.eraport.ui.profil

import android.R.attr.country
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityEditProfilBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity


class EditProfilActivity : AppCompatActivity() {

    private lateinit var activityEditProfilBinding: ActivityEditProfilBinding
    private lateinit var idSekolah: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEditProfilBinding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(activityEditProfilBinding.root)

        setupSpinner()

        val item = activityEditProfilBinding.navigation.menu.findItem(R.id.nav_dashboard)
        item.setChecked(true)
        navigationListener()

        activityEditProfilBinding.btnBatal.setOnClickListener {
            val intent = Intent(this@EditProfilActivity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    private fun setupSpinner() {
        val arrayIdSekolah = arrayOf("SDC1", "SDC2")
        val spinner = activityEditProfilBinding.spinnerIdSekolah
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayIdSekolah)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        idSekolah = "SDC2"
        arrayIdSekolah.forEachIndexed({index, id ->
            if (idSekolah.equals(id)){
                spinner.setSelection(index)
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@EditProfilActivity,
                    " " + arrayIdSekolah[position],
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun navigationListener() {
        activityEditProfilBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
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

}