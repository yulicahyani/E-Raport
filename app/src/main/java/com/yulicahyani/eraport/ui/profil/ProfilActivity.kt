package com.yulicahyani.eraport.ui.profil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityProfilBinding
import com.yulicahyani.eraport.helper.Constant
import com.yulicahyani.eraport.helper.PrefHelper
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.InputNilaiActivity
import com.yulicahyani.eraport.ui.login.LoginActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class ProfilActivity : AppCompatActivity() {

    private lateinit var activityProfilBinding: ActivityProfilBinding
    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProfilBinding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(activityProfilBinding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Profil"

        prefHelper = PrefHelper(this)
        val fullName = StringBuilder()
        activityProfilBinding.nama.text = fullName.append(prefHelper.getString(Constant.PREF_FIRSTNAME)).append(" ").append(prefHelper.getString(Constant.PREF_LASTNAME))
        activityProfilBinding.username.text = prefHelper.getString(Constant.PREF_USERNAME)
        activityProfilBinding.password.text = prefHelper.getString(Constant.PREF_PASSWORD)
        activityProfilBinding.email.text = prefHelper.getString(Constant.PREF_EMAIL)
        activityProfilBinding.role.text = prefHelper.getString(Constant.PREF_ROLE)
        activityProfilBinding.namaSekolah.text = prefHelper.getString(Constant.PREF_SEKOLAH)
        activityProfilBinding.alamatSekolah.text = prefHelper.getString(Constant.PREF_ALAMAT_SEKOLAH)

        val item = activityProfilBinding.navigation.menu.findItem(R.id.nav_dashboard)
        item.isChecked = true
        navigationListener()

        activityProfilBinding.btnEdit.setOnClickListener {
            val intent = Intent(this@ProfilActivity, EditProfilActivity::class.java)
            startActivity(intent)
        }

        activityProfilBinding.btnLogout.setOnClickListener {
            prefHelper.clear()
            Toast.makeText(this, "Success Logout", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun navigationListener() {
        activityProfilBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@ProfilActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@ProfilActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@ProfilActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@ProfilActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}