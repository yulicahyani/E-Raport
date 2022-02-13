package com.yulicahyani.eraport.ui.profil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.yulicahyani.eraport.R

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Profil"
    }
}