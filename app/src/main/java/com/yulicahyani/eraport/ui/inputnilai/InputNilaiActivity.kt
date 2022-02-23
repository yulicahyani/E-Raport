package com.yulicahyani.eraport.ui.inputnilai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.yulicahyani.eraport.R
import com.yulicahyani.eraport.databinding.ActivityInputNilaiBinding
import com.yulicahyani.eraport.ui.dashboard.DashboardActivity
import com.yulicahyani.eraport.ui.datautama.DataUtamaActivity
import com.yulicahyani.eraport.ui.inputnilai.listmapel.ListMapelActivity
import com.yulicahyani.eraport.ui.inputnilai.listsiswa.ListSiswaActivity
import com.yulicahyani.eraport.ui.raport.RaportActivity

class InputNilaiActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityInputNilaiBinding: ActivityInputNilaiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityInputNilaiBinding = ActivityInputNilaiBinding.inflate(layoutInflater)
        setContentView(activityInputNilaiBinding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Input Nilai"

        val item = activityInputNilaiBinding.navigation.menu.findItem(R.id.nav_input_nilai)
        item.setChecked(true)
        navigationListener()

        activityInputNilaiBinding.cdSpiritual.setOnClickListener(this)
        activityInputNilaiBinding.cdSikapsosial.setOnClickListener(this)
        activityInputNilaiBinding.cdPengetahuan.setOnClickListener(this)
        activityInputNilaiBinding.cdKeterampilan.setOnClickListener(this)

    }

    private fun navigationListener() {
        activityInputNilaiBinding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_dashboard -> {
                    val intent = Intent(this@InputNilaiActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_data_utama -> {
                    val intent = Intent(this@InputNilaiActivity, DataUtamaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_input_nilai -> {
                    val intent = Intent(this@InputNilaiActivity, InputNilaiActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_raport -> {
                    val intent = Intent(this@InputNilaiActivity, RaportActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onClick(v: View?) {
        when (v){
            activityInputNilaiBinding.cdSpiritual ->{
                intentListUser("Spiritual")
            }
            activityInputNilaiBinding.cdSikapsosial -> {
                intentListUser("Sosial")
            }
            activityInputNilaiBinding.cdPengetahuan -> {
                intentListMapel("Pengetahuan")
            }
            activityInputNilaiBinding.cdKeterampilan -> {
                intentListMapel("Keterampilan")
            }
        }
    }

    private  fun intentListUser(nameActivity : String){
        Intent(this@InputNilaiActivity, ListSiswaActivity::class.java).also {
            it.putExtra(ListSiswaActivity.EXTRA_FROM_ACTIVITY, nameActivity)
            startActivity(it)
        }
    }

    private  fun intentListMapel(nameActivity : String){
        Intent(this@InputNilaiActivity, ListMapelActivity::class.java).also {
            it.putExtra(ListMapelActivity.EXTRA_FROM_ACTIVITY, nameActivity)
            startActivity(it)
        }
    }
}