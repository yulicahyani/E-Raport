package com.yulicahyani.eraport.ui.raport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityListRaportSemesterBinding
import com.yulicahyani.eraport.databinding.ActivityRaportBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.datautama.datasiswa.DetailDataSiswaActivity
import com.yulicahyani.eraport.ui.datautama.datasiswa.TambahDataSiswaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity

class ListRaportSemesterActivity : AppCompatActivity() {

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

    private  lateinit var listRaportSemesterBinding: ActivityListRaportSemesterBinding
    lateinit var prefHelper : PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listRaportSemesterBinding = ActivityListRaportSemesterBinding.inflate(layoutInflater)
        setContentView(listRaportSemesterBinding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Raport"

        val item = listRaportSemesterBinding.navigation.menu.findItem(R.id.nav_raport)
        item.setChecked(true)
        navigationListener()

        prefHelper = PrefHelper(this)

        val id_siswa = intent.getStringExtra(EXTRA_ID_SISWA)
        val nama = intent.getStringExtra(EXTRA_NAMA_SISWA)
        val agama = intent.getStringExtra(EXTRA_AGAMA)
        val alamatSiswa = intent.getStringExtra(EXTRA_ALAMAT)
        val jenisKelamin = intent.getStringExtra(
            EXTRA_JENIS_KELAMIN
        )
        val kelas = intent.getStringExtra(EXTRA_KELAS)
        val nis_nisn = StringBuilder()
        val nis = nis_nisn.append(intent.getStringExtra(
            EXTRA_NIS
        )).append("/").append(intent.getStringExtra(EXTRA_NISN))
        val password = intent.getStringExtra(EXTRA_PASSWORD)
        val semester = intent.getStringExtra(EXTRA_SEMESTER)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val tahunPelajaran = intent.getStringExtra(
            EXTRA_TAHUN_AJARAN
        )
        val ttl = intent.getStringExtra(EXTRA_TTL)
        val namaSekolah = prefHelper.getString(Constant.PREF_SEKOLAH)

        listRaportSemesterBinding.lihatRaport1.setOnClickListener {
            Intent(this@ListRaportSemesterActivity, DetailRaportActivity::class.java).also {
                it.putExtra(DetailRaportActivity.EXTRA_ID_SISWA, id_siswa)
                it.putExtra(DetailRaportActivity.EXTRA_NAMA_SISWA, nama)
                it.putExtra(DetailRaportActivity.EXTRA_ID_SEKOLAH, namaSekolah)
                it.putExtra(DetailRaportActivity.EXTRA_USERNAME, username)
                it.putExtra(DetailRaportActivity.EXTRA_PASSWORD, password)
                it.putExtra(DetailRaportActivity.EXTRA_NIS, nis.toString())
                it.putExtra(DetailRaportActivity.EXTRA_TTL, ttl)
                it.putExtra(DetailRaportActivity.EXTRA_JENIS_KELAMIN, jenisKelamin)
                it.putExtra(DetailRaportActivity.EXTRA_AGAMA, agama)
                it.putExtra(DetailRaportActivity.EXTRA_ALAMAT, alamatSiswa)
                it.putExtra(DetailRaportActivity.EXTRA_KELAS, kelas)
                it.putExtra(DetailRaportActivity.EXTRA_SEMESTER, '1')
                it.putExtra(DetailRaportActivity.EXTRA_TAHUN_AJARAN, tahunPelajaran)
                startActivity(it)
            }
        }

        listRaportSemesterBinding.lihatRaport2.setOnClickListener {
            Intent(this@ListRaportSemesterActivity, DetailRaportActivity::class.java).also {
                it.putExtra(DetailRaportActivity.EXTRA_ID_SISWA, id_siswa)
                it.putExtra(DetailRaportActivity.EXTRA_NAMA_SISWA, nama)
                it.putExtra(DetailRaportActivity.EXTRA_ID_SEKOLAH, namaSekolah)
                it.putExtra(DetailRaportActivity.EXTRA_USERNAME, username)
                it.putExtra(DetailRaportActivity.EXTRA_PASSWORD, password)
                it.putExtra(DetailRaportActivity.EXTRA_NIS, nis.toString())
                it.putExtra(DetailRaportActivity.EXTRA_TTL, ttl)
                it.putExtra(DetailRaportActivity.EXTRA_JENIS_KELAMIN, jenisKelamin)
                it.putExtra(DetailRaportActivity.EXTRA_AGAMA, agama)
                it.putExtra(DetailRaportActivity.EXTRA_ALAMAT, alamatSiswa)
                it.putExtra(DetailRaportActivity.EXTRA_KELAS, kelas)
                it.putExtra(DetailRaportActivity.EXTRA_SEMESTER, '2')
                it.putExtra(DetailRaportActivity.EXTRA_TAHUN_AJARAN, tahunPelajaran)
                startActivity(it)
            }
        }
    }
    private fun navigationListener() {
        listRaportSemesterBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@ListRaportSemesterActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@ListRaportSemesterActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@ListRaportSemesterActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@ListRaportSemesterActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}