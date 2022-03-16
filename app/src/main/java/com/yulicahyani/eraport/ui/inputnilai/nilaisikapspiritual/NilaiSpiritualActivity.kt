package com.yulicahyani.eraport.ui.inputnilai.nilaisikapspiritual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityNilaiSpiritualBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class NilaiSpiritualActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_NAMA_SISWA = "extra_nama_siswa"
    }
    private lateinit var  activityNilaiSpiritualBinding: ActivityNilaiSpiritualBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiSpiritualBinding = ActivityNilaiSpiritualBinding.inflate(layoutInflater)
        setContentView(activityNilaiSpiritualBinding.root)
        setupSpinner()
        val item = activityNilaiSpiritualBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()
    }

    private fun navigationListener() {
        activityNilaiSpiritualBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@NilaiSpiritualActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@NilaiSpiritualActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@NilaiSpiritualActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@NilaiSpiritualActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setupSpinner() {
        val arrayNilaiSikap = arrayOf("Pilih nilai", "SB", "B", "PB")
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayNilaiSikap)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Ketaatan Beribadah
        val spinnerBeribadah = activityNilaiSpiritualBinding.spinnerBeribadah
        spinnerBeribadah.adapter = arrayAdapter
        val nilaiSikapBeribadah = "Pilih nilai"
        arrayNilaiSikap.forEachIndexed { index, nilai ->
            if (nilaiSikapBeribadah == nilai) {
                spinnerBeribadah.setSelection(index)
            }
        }
        spinnerBeribadah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val penilaian = arrayNilaiSikap[position]
                Toast.makeText(
                    this@NilaiSpiritualActivity,
                    " $penilaian ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


        //Berprilaku Bersyukur
        val spinnerBersyukur = activityNilaiSpiritualBinding.spinnerBersyukur
        spinnerBersyukur.adapter = arrayAdapter
        val nilaiSikapBersyukur = "Pilih nilai"
        arrayNilaiSikap.forEachIndexed { index, nilai ->
            if (nilaiSikapBersyukur == nilai) {
                spinnerBersyukur.setSelection(index)
            }
        }
        spinnerBersyukur.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val penilaian = arrayNilaiSikap[position]
                Toast.makeText(
                    this@NilaiSpiritualActivity,
                    " $penilaian ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Berdoa
        val spinnerBerdoa = activityNilaiSpiritualBinding.spinnerBerdoa
        spinnerBerdoa.adapter = arrayAdapter
        val nilaiSikapBerdoa = "Pilih nilai"
        arrayNilaiSikap.forEachIndexed { index, nilai ->
            if (nilaiSikapBerdoa == nilai) {
                spinnerBerdoa.setSelection(index)
            }
        }
        spinnerBerdoa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val penilaian = arrayNilaiSikap[position]
                Toast.makeText(
                    this@NilaiSpiritualActivity,
                    " $penilaian ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Toleransi
        val spinnerToleransi = activityNilaiSpiritualBinding.spinnerToleransi
        spinnerToleransi.adapter = arrayAdapter
        val nilaiSikapToleransi = "Pilih nilai"
        arrayNilaiSikap.forEachIndexed { index, nilai ->
            if (nilaiSikapToleransi == nilai) {
                spinnerToleransi.setSelection(index)
            }
        }
        spinnerToleransi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val penilaian = arrayNilaiSikap[position]
                Toast.makeText(
                    this@NilaiSpiritualActivity,
                    " $penilaian ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


    }

}