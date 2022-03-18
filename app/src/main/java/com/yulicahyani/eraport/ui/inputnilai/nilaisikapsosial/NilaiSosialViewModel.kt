package com.yulicahyani.eraport.ui.inputnilai.nilaisikapsosial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NilaiSosialViewModel : ViewModel() {
    val detailNilaiSosial = MutableLiveData<ResultsDetailNilaiSikapSosial>()
    val responseCreate = MutableLiveData<GeneralResponse>()
    val responseUpdate = MutableLiveData<GeneralResponse>()

    fun findDetailNilaiSosial(id_siswa: Int) {
        val client = ApiConfig.getApiService().getDetailSikapSosialSiswa(id_siswa)
        client.enqueue(object : Callback<DetailNilaiSikapSosialResponse> {
            override fun onResponse(
                call: Call<DetailNilaiSikapSosialResponse>,
                response: Response<DetailNilaiSikapSosialResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 1) {
                        detailNilaiSosial.postValue(response.body()?.nilai?.get(0))
                    }
                }
            }

            override fun onFailure(call: Call<DetailNilaiSikapSosialResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun addNilaiSosial(
        id_siswa: Int,
        jujur: String,
        disiplin: String,
        tanggung_jawab: String,
        santun: String,
        peduli: String,
        percaya_diri: String
    ) {
        val client = ApiConfig.getApiService()
            .createNilaiSosial(
                id_siswa,
                jujur,
                disiplin,
                tanggung_jawab,
                santun,
                peduli,
                percaya_diri
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

    fun updateNilaiSosial(
        id_siswa: Int,
        jujur: String,
        disiplin: String,
        tanggung_jawab: String,
        santun: String,
        peduli: String,
        percaya_diri: String
    ) {
        val client = ApiConfig.getApiService()
            .updateNilaiSosial(
                id_siswa,
                jujur,
                disiplin,
                tanggung_jawab,
                santun,
                peduli,
                percaya_diri
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

    fun getDetailSikapSpiritualSiswa(): LiveData<ResultsDetailNilaiSikapSosial> = detailNilaiSosial
    fun getResponseCreate(): LiveData<GeneralResponse> = responseCreate
    fun getResponseUpdate(): LiveData<GeneralResponse> = responseUpdate
}