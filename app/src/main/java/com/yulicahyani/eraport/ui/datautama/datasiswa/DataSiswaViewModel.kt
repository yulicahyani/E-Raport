package com.yulicahyani.eraport.ui.datautama.datasiswa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.GeneralResponse
import com.yulicahyani.eraport.data.source.remote.response.ResultsSiswa
import com.yulicahyani.eraport.data.source.remote.response.SiswaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSiswaViewModel : ViewModel() {
    val listSiswa = MutableLiveData<ArrayList<ResultsSiswa>>()
    val responseCreate = MutableLiveData<GeneralResponse>()
    val responseUpdate = MutableLiveData<GeneralResponse>()
    val responseDelete = MutableLiveData<GeneralResponse>()

    fun findSiswaSekolah(id_user : Int){
        val client = ApiConfig.getApiService().getSiswaSekolah(id_user)
        client.enqueue(object : Callback<SiswaResponse> {
            override fun onResponse(call: Call<SiswaResponse>, response: Response<SiswaResponse>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        listSiswa.postValue(response.body()?.siswa as ArrayList<ResultsSiswa>?)
                    }
                }
            }

            override fun onFailure(call: Call<SiswaResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun addDataSiswa(
        id_sekolah: Int,
        username: String,
        password: String,
        nis: String,
        nisn: String,
        nama_siswa: String,
        nama_panggilan: String,
        ttl: String,
        jenis_kelamin: String,
        agama: String,
        alamat: String,
        kelas: Int,
        semester: Int,
        tahun_ajaran: String
    ) {
        val client = ApiConfig.getApiService().createUserSiswa(
            id_sekolah,
            username,
            password,
            nis,
            nisn,
            nama_siswa,
            nama_panggilan,
            ttl,
            jenis_kelamin,
            agama,
            alamat,
            kelas,
            semester,
            tahun_ajaran
        )
        client.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if (response.isSuccessful) {
                    responseCreate.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun updateDataSiswa(
        id_siswa: Int,
        id_sekolah: Int,
        username: String,
        password: String,
        nis: String,
        nisn: String,
        nama_siswa: String,
        nama_panggilan: String,
        ttl: String,
        jenis_kelamin: String,
        agama: String,
        alamat: String,
        kelas: Int,
        semester: Int,
        tahun_ajaran: String
    ) {
        val client = ApiConfig.getApiService().updateUserSiswa(
            id_siswa,
            id_sekolah,
            username,
            password,
            nis,
            nisn,
            nama_siswa,
            nama_panggilan,
            ttl,
            jenis_kelamin,
            agama,
            alamat,
            kelas,
            semester,
            tahun_ajaran
        )
        client.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if (response.isSuccessful) {
                    responseUpdate.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun deleteDataSiswa(id_siswa: Int) {
        val client = ApiConfig.getApiService().deleteUserSiswa(
            id_siswa
        )
        client.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if (response.isSuccessful) {
                    responseDelete.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getResponseDelete(): LiveData<GeneralResponse> = responseDelete
    fun getResponseUpdate(): LiveData<GeneralResponse> = responseUpdate
    fun getResponseCreate(): LiveData<GeneralResponse> = responseCreate
    fun getSiswaSekolah(): LiveData<ArrayList<ResultsSiswa>> = listSiswa
}