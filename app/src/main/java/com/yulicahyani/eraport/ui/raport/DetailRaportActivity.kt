package com.yulicahyani.eraport.ui.raport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityDetailRaportBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity

class DetailRaportActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID_SISWA = "extra_id_siswa"
        const val EXTRA_NAMA_SISWA = "extra_nama_siswa"
        const val EXTRA_ID_SEKOLAH = "extra_id_sekolah"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_PASSWORD = "extra_password"
        const val EXTRA_NIS = "extra_nis"
        const val EXTRA_TTL = "extra_ttl"
        const val EXTRA_JENIS_KELAMIN= "extra_jenis_kelamin"
        const val EXTRA_AGAMA = "extra_agama"
        const val EXTRA_ALAMAT = "extra_alamat"
        const val EXTRA_KELAS = "extra_kelas"
        const val EXTRA_SEMESTER = "extra_semester"
        const val EXTRA_TAHUN_AJARAN= "extra_tahun_ajaran"
    }

    private  lateinit var detailRaportBinding: ActivityDetailRaportBinding
    lateinit var prefHelper : PrefHelper
    private lateinit var viewModel: NilaiFinalViewModel
    private lateinit var adapterPengetahuan: NilaiFinalPengetahuanAdapter
    private lateinit var adapterKeterampilan: NilaiFinalKeterampilanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailRaportBinding = ActivityDetailRaportBinding.inflate(layoutInflater)
        setContentView(detailRaportBinding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Raport"

        val item = detailRaportBinding.navigation.menu.findItem(R.id.nav_raport)
        item.isChecked = true
        navigationListener()

        prefHelper = PrefHelper(this)

        val id_siswa = intent.getStringExtra(EXTRA_ID_SISWA)
        val nama = intent.getStringExtra(EXTRA_NAMA_SISWA)
        val agama = intent.getStringExtra(EXTRA_AGAMA)
        val alamatSiswa = intent.getStringExtra(EXTRA_ALAMAT)
        val jenisKelamin = intent.getStringExtra(EXTRA_JENIS_KELAMIN)
        val kelas = intent.getStringExtra(EXTRA_KELAS)
        val nis_nisn = StringBuilder()
        val nis = intent.getStringExtra(EXTRA_NIS)
        val password = intent.getStringExtra(EXTRA_PASSWORD)
        val semester = intent.getStringExtra(EXTRA_SEMESTER)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val tahunPelajaran = intent.getStringExtra(EXTRA_TAHUN_AJARAN)
        val ttl = intent.getStringExtra(EXTRA_TTL)
        val namaSekolah = prefHelper.getString(Constant.PREF_SEKOLAH)

        detailRaportBinding.nama.text = nama
        detailRaportBinding.nis.text = nis
        detailRaportBinding.sekolah.text = namaSekolah
        detailRaportBinding.semester.text = semester
        detailRaportBinding.kelas.text = kelas
        detailRaportBinding.tapel.text = tahunPelajaran

        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            NilaiFinalViewModel::class.java
        )

        if (id_siswa != null) {
            viewModel.findNilaiFinalSpiritual(id_siswa.toInt())
        }
        viewModel.getNilaiAkhirSikapSpiritual().observe(this, {
            if (it != null && it.size != 0 && it[0].semester == semester) {
                detailRaportBinding.apply {
                    spiritual.text = it[0].deskripsi
                }
            }
            else{
                detailRaportBinding.apply {
                    spiritual.text = ' '.toString()
                }
            }
        })

        if (id_siswa != null) {
            viewModel.findNilaiFinalSosial(id_siswa.toInt())
        }
        viewModel.getNilaiAkhirSikapSosial().observe(this, {
            if (it != null && it.size != 0 && it[0].semester == semester) {
                detailRaportBinding.apply {
                    sosial.text = it[0].deskripsi
                }
            }
            else{
                detailRaportBinding.apply {
                    sosial.text = ' '.toString()
                }
            }
        })

        adapterPengetahuan = NilaiFinalPengetahuanAdapter()

        if (id_siswa != null) {
            viewModel.findNilaiFinalPengetahuan(id_siswa.toInt())
        }
        viewModel.getNilaiAkhirPengetahuan().observe(this) {
            if (it != null && it.size != 0 && it[0].semester == semester) {
                adapterPengetahuan.setData(it)
                adapterPengetahuan.notifyDataSetChanged()
            }
        }

        detailRaportBinding.apply {
            rvPengetahuan.layoutManager =
                LinearLayoutManager(this@DetailRaportActivity)
            rvPengetahuan.setHasFixedSize(true)
            rvPengetahuan.adapter = adapterPengetahuan
        }

        adapterKeterampilan = NilaiFinalKeterampilanAdapter()

        if (id_siswa != null) {
            viewModel.findNilaiFinalKeterampilan(id_siswa.toInt())
        }
        viewModel.getNilaiAkhirKeterampilan().observe(this) {
            if (it != null && it.size != 0 && it[0].semester == semester) {
                adapterKeterampilan.setData(it)
                adapterKeterampilan.notifyDataSetChanged()
            }
        }

        detailRaportBinding.apply {
            rvKeterampilan.layoutManager =
                LinearLayoutManager(this@DetailRaportActivity)
            rvKeterampilan.setHasFixedSize(true)
            rvKeterampilan.adapter = adapterKeterampilan
        }

    }

    private fun navigationListener() {
        detailRaportBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@DetailRaportActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@DetailRaportActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@DetailRaportActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@DetailRaportActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}