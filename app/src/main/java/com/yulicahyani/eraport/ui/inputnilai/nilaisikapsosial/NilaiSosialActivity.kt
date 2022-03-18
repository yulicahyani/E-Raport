package com.yulicahyani.eraport.ui.inputnilai.nilaisikapsosial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityNilaiSosialBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.inputnilai.listnilaisikap.ListNilaiSikapActivity
import com.yulicahyani.eraport.ui.inputnilai.nilaisikapspiritual.NilaiSpiritualActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class NilaiSosialActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_NAMA_SISWA = "extra_nama_siswa"
        const val EXTRA_DESCRIPTION = "extra_description"
    }

    private lateinit var activityNilaiSosialBinding: ActivityNilaiSosialBinding
    private lateinit var viewModel: NilaiSosialViewModel
    private lateinit var jujur: String
    private lateinit var disiplin: String
    private lateinit var tanggung_jawab: String
    private lateinit var santun: String
    private lateinit var peduli: String
    private lateinit var percaya_diri: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNilaiSosialBinding = ActivityNilaiSosialBinding.inflate(layoutInflater)
        setContentView(activityNilaiSosialBinding.root)

        val item = activityNilaiSosialBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.isChecked = true
        navigationListener()
        showProgressBar(true)

        jujur = ""
        disiplin = ""
        tanggung_jawab = ""
        santun = ""
        peduli = ""
        percaya_diri = ""

        activityNilaiSosialBinding.btnCancel.setOnClickListener(this)

        val id_siswa = intent.getStringExtra(EXTRA_ID_SISWA)
        activityNilaiSosialBinding.tvNameSiswa.text = intent.getStringExtra(
            NilaiSpiritualActivity.EXTRA_NAMA_SISWA
        )
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            NilaiSosialViewModel::class.java
        )

        if (intent.getStringExtra(NilaiSpiritualActivity.EXTRA_DESCRIPTION).isNullOrEmpty()) {
            showProgressBar(false)
            activityNilaiSosialBinding.btnUpdate.visibility = View.INVISIBLE
            activityNilaiSosialBinding.btnTambah.visibility = View.VISIBLE
            activityNilaiSosialBinding.btnTambah.setOnClickListener(this)
            setupSpinner()
        } else {
            activityNilaiSosialBinding.btnTambah.visibility = View.INVISIBLE
            activityNilaiSosialBinding.btnUpdate.visibility = View.VISIBLE
            activityNilaiSosialBinding.btnUpdate.setOnClickListener(this)
            if (id_siswa != null) {
                viewModel.findDetailNilaiSosial(id_siswa.toInt())
            }
            viewModel.getDetailSikapSpiritualSiswa().observe(this, {
                if (it != null) {
                    showProgressBar(false)
                    activityNilaiSosialBinding.apply {
                        tvDescSpiritual.text = it.deskripsi
                    }
                    jujur = it.jujur
                    disiplin = it.disiplin
                    tanggung_jawab = it.tanggung_jawab
                    santun = it.santun
                    peduli = it.peduli
                    percaya_diri = it.percaya_diri
                    setupSpinner()
                }
            })
        }


    }

    override fun onClick(v: View?) {
        when (v) {
            activityNilaiSosialBinding.btnTambah -> {
                addNilaiSosial()
            }
            activityNilaiSosialBinding.btnUpdate -> {
                updateNilaiSosial()
            }
            activityNilaiSosialBinding.btnCancel -> {
                val intent = Intent(this@NilaiSosialActivity, ListNilaiSikapActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra(ListNilaiSikapActivity.EXTRA_FROM_ACTIVITY, "SikapSosial")
                startActivity(intent)
            }
        }
    }

    private fun setupSpinner() {
        val arrayNilaiSikap = arrayOf("Pilih nilai", "SB", "B", "PB")
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayNilaiSikap)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Jujur
        val spinnerJujur = activityNilaiSosialBinding.spinnerJujur
        spinnerJujur.adapter = arrayAdapter

        if (jujur.isEmpty()) {
            spinnerJujur.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (jujur == nilai) {
                    spinnerJujur.setSelection(index)
                }
            }
        }

        spinnerJujur.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    jujur = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


        //Disiplin
        val spinnerDisiplin = activityNilaiSosialBinding.spinnerDisiplin
        spinnerDisiplin.adapter = arrayAdapter

        if (disiplin.isEmpty()) {
            spinnerDisiplin.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (disiplin == nilai) {
                    spinnerDisiplin.setSelection(index)
                }
            }
        }

        spinnerDisiplin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    disiplin = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Tanggung Jawab
        val spinnerTanggungJawab = activityNilaiSosialBinding.spinnerTanggungJawab
        spinnerTanggungJawab.adapter = arrayAdapter

        if (tanggung_jawab.isEmpty()) {
            spinnerTanggungJawab.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (tanggung_jawab == nilai) {
                    spinnerTanggungJawab.setSelection(index)
                }
            }
        }
        spinnerTanggungJawab.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    tanggung_jawab = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Santun
        val spinnerSantun = activityNilaiSosialBinding.spinnerSantun
        spinnerSantun.adapter = arrayAdapter
        if (santun.isEmpty()) {
            spinnerSantun.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (santun == nilai) {
                    spinnerSantun.setSelection(index)
                }
            }
        }
        spinnerSantun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    santun = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Peduli
        val spinnerPeduli = activityNilaiSosialBinding.spinnerPeduli
        spinnerPeduli.adapter = arrayAdapter
        if (peduli.isEmpty()) {
            spinnerPeduli.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (peduli == nilai) {
                    spinnerPeduli.setSelection(index)
                }
            }
        }
        spinnerPeduli.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    peduli = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Percaya Diri
        val spinnerPercayaDiri = activityNilaiSosialBinding.spinnerPercayaDiri
        spinnerPercayaDiri.adapter = arrayAdapter
        if (percaya_diri.isEmpty()) {
            spinnerPercayaDiri.setSelection(0)
        } else {
            arrayNilaiSikap.forEachIndexed { index, nilai ->
                if (percaya_diri == nilai) {
                    spinnerPercayaDiri.setSelection(index)
                }
            }
        }
        spinnerPercayaDiri.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    percaya_diri = arrayNilaiSikap[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


    }

    private fun addNilaiSosial() {
        if (jujur.isEmpty() || disiplin.isEmpty() || tanggung_jawab.isEmpty() || santun.isEmpty() || peduli.isEmpty() || percaya_diri.isEmpty()) {
            Toast.makeText(this, "Input Data Tidak Lengkap", Toast.LENGTH_SHORT).show()
        } else {
            val id_siswa = intent.getStringExtra(NilaiSpiritualActivity.EXTRA_ID_SISWA)
            if (id_siswa != null) {
                showProgressBar(true)
                viewModel.addNilaiSosial(
                    id_siswa.toInt(),
                    jujur,
                    disiplin,
                    tanggung_jawab,
                    santun,
                    peduli,
                    percaya_diri
                )
            }
            viewModel.getResponseCreate().observe(this, {
                if (it != null) {
                    showProgressBar(false)
                    if (it.status == 1) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        val intent =
                            Intent(this@NilaiSosialActivity, ListNilaiSikapActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(
                            ListNilaiSikapActivity.EXTRA_FROM_ACTIVITY,
                            "SikapSosial"
                        )
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    }

    private fun updateNilaiSosial() {
        if (jujur.isEmpty() || disiplin.isEmpty() || tanggung_jawab.isEmpty() || santun.isEmpty() || peduli.isEmpty() || percaya_diri.isEmpty()) {
            Toast.makeText(this, "Penilaian Tidak Lengkap", Toast.LENGTH_SHORT).show()
        } else {
            val id_siswa = intent.getStringExtra(NilaiSpiritualActivity.EXTRA_ID_SISWA)
            if (id_siswa != null) {
                showProgressBar(true)
                viewModel.updateNilaiSosial(
                    id_siswa.toInt(),
                    jujur,
                    disiplin,
                    tanggung_jawab,
                    santun,
                    peduli,
                    percaya_diri
                )
            }
            viewModel.getResponseUpdate().observe(this, {
                if (it != null) {
                    showProgressBar(false)
                    if (it.status == 1) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        val intent =
                            Intent(this@NilaiSosialActivity, ListNilaiSikapActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(
                            ListNilaiSikapActivity.EXTRA_FROM_ACTIVITY,
                            "SikapSosial"
                        )
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun showProgressBar(state: Boolean) = if (state) {
        activityNilaiSosialBinding.progressBar.visibility = View.VISIBLE
    } else {
        activityNilaiSosialBinding.progressBar.visibility = View.GONE
    }


    private fun navigationListener() {
        activityNilaiSosialBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this@NilaiSosialActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@NilaiSosialActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@NilaiSosialActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@NilaiSosialActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}