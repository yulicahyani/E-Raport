package com.yulicahyani.eraport.ui.inputnilai.nilaisikapspiritual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityNilaiSpiritualBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.listnilaisikap.ListNilaiSikapActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class NilaiSpiritualActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_NAMA_SISWA = "extra_nama_siswa"
        const val EXTRA_DESCRIPTION = "extra_description"
    }

    private lateinit var activityNilaiSpiritualBinding: ActivityNilaiSpiritualBinding
    private lateinit var viewModel: NilaiSpiritualViewModel
    private lateinit var beribadah: String
    private lateinit var bersyukur: String
    private lateinit var berdoa: String
    private lateinit var toleransi: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiSpiritualBinding = ActivityNilaiSpiritualBinding.inflate(layoutInflater)
        setContentView(activityNilaiSpiritualBinding.root)

        val item = activityNilaiSpiritualBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()
        showProgressBar(true)

        beribadah = ""
        bersyukur = ""
        berdoa = ""
        toleransi = ""

        activityNilaiSpiritualBinding.btnCancel.setOnClickListener(this)

        val id_siswa = intent.getStringExtra(EXTRA_ID_SISWA)
        activityNilaiSpiritualBinding.tvNameSiswa.text = intent.getStringExtra(EXTRA_NAMA_SISWA)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            NilaiSpiritualViewModel::class.java
        )

        if (intent.getStringExtra(EXTRA_DESCRIPTION).isNullOrEmpty()) {
            showProgressBar(false)
            activityNilaiSpiritualBinding.btnUpdate.visibility = View.INVISIBLE
            activityNilaiSpiritualBinding.btnTambah.visibility = View.VISIBLE
            activityNilaiSpiritualBinding.btnTambah.setOnClickListener(this)
            setupSpinner()
        } else {
            activityNilaiSpiritualBinding.btnTambah.visibility = View.INVISIBLE
            activityNilaiSpiritualBinding.btnUpdate.visibility = View.VISIBLE
            activityNilaiSpiritualBinding.btnUpdate.setOnClickListener(this)
            if (id_siswa != null) {
                viewModel.findDetailNilaiSpiritual(id_siswa.toInt())
            }
            viewModel.getDetailSikapSpiritualSiswa().observe(this, {
                if (it != null) {
                    showProgressBar(false)
                    activityNilaiSpiritualBinding.apply {
                        tvDescSpiritual.text = it.deskripsi
                    }
                    beribadah = it.ketaatan_beribadah
                    bersyukur = it.berprilaku_bersyukur
                    berdoa = it.berdoa
                    toleransi = it.toleransi
                    setupSpinner()
                }
            })
        }


    }

    private fun navigationListener() {
        activityNilaiSpiritualBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
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

        if (beribadah.isEmpty()) {
            spinnerBeribadah.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (beribadah == nilai) {
                    spinnerBeribadah.setSelection(index)
                }
            }
        }

        spinnerBeribadah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    beribadah = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


        //Berprilaku Bersyukur
        val spinnerBersyukur = activityNilaiSpiritualBinding.spinnerBersyukur
        spinnerBersyukur.adapter = arrayAdapter

        if (bersyukur.isEmpty()) {
            spinnerBersyukur.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (bersyukur == nilai) {
                    spinnerBersyukur.setSelection(index)
                }
            }
        }

        spinnerBersyukur.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    bersyukur = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Berdoa
        val spinnerBerdoa = activityNilaiSpiritualBinding.spinnerBerdoa
        spinnerBerdoa.adapter = arrayAdapter

        if (berdoa.isEmpty()) {
            spinnerBerdoa.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (berdoa == nilai) {
                    spinnerBerdoa.setSelection(index)
                }
            }
        }
        spinnerBerdoa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    berdoa = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Toleransi
        val spinnerToleransi = activityNilaiSpiritualBinding.spinnerToleransi
        spinnerToleransi.adapter = arrayAdapter
        if (toleransi.isEmpty()) {
            spinnerToleransi.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (toleransi == nilai) {
                    spinnerToleransi.setSelection(index)
                }
            }
        }
        spinnerToleransi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    toleransi = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


    }

    private fun showProgressBar(state: Boolean) = if (state) {
        activityNilaiSpiritualBinding.progressBar.visibility = View.VISIBLE
    } else {
        activityNilaiSpiritualBinding.progressBar.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when (v) {
            activityNilaiSpiritualBinding.btnTambah -> {
                addNilaiSpiritual()
            }
            activityNilaiSpiritualBinding.btnUpdate -> {
                updateNilaiSpiritual()
            }
            activityNilaiSpiritualBinding.btnCancel -> {
                val intent = Intent(this@NilaiSpiritualActivity, ListNilaiSikapActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra(ListNilaiSikapActivity.EXTRA_FROM_ACTIVITY, "SikapSpiritual")
                startActivity(intent)
            }
        }
    }

    private fun addNilaiSpiritual() {
        if (beribadah.isEmpty() || bersyukur.isEmpty() || berdoa.isEmpty() || toleransi.isEmpty()) {
            Toast.makeText(this, "Input Data Tidak Lengkap", Toast.LENGTH_SHORT).show()
        } else {
            val id_siswa = intent.getStringExtra(EXTRA_ID_SISWA)
            if (id_siswa != null) {
                showProgressBar(true)
                viewModel.addNilaiSpiritual(
                    id_siswa.toInt(),
                    beribadah,
                    bersyukur,
                    berdoa,
                    toleransi
                )
            }
            viewModel.getResponseCreate().observe(this, {
                if (it != null) {
                    showProgressBar(false)
                    if (it.status == 1) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        val intent =
                            Intent(this@NilaiSpiritualActivity, ListNilaiSikapActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(
                            ListNilaiSikapActivity.EXTRA_FROM_ACTIVITY,
                            "SikapSpiritual"
                        )
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun updateNilaiSpiritual() {
        if (beribadah.isEmpty() || bersyukur.isEmpty() || berdoa.isEmpty() || toleransi.isEmpty()) {
            Toast.makeText(this, "Penilaian Tidak Lengkap", Toast.LENGTH_SHORT).show()
        } else {
            val id_siswa = intent.getStringExtra(EXTRA_ID_SISWA)
            if (id_siswa != null) {
                showProgressBar(true)
                viewModel.updateNilaiSpiritual(
                    id_siswa.toInt(),
                    beribadah,
                    bersyukur,
                    berdoa,
                    toleransi
                )
            }
            viewModel.getResponseUpdate().observe(this, {
                if (it != null) {
                    showProgressBar(false)
                    if (it.status == 1) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        val intent =
                            Intent(this@NilaiSpiritualActivity, ListNilaiSikapActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(
                            ListNilaiSikapActivity.EXTRA_FROM_ACTIVITY,
                            "SikapSpiritual"
                        )
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

}