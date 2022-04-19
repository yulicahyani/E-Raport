package com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.GeneralResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NilaiPengetahuanViewModel : ViewModel() {
    val responseCreate = MutableLiveData<GeneralResponse>()
    val responseUpdate = MutableLiveData<GeneralResponse>()

    fun addNilaiPengetahuan(
        id_siswa: Int,
        id_mapel: Int,
        id_tema: Int,
        id_kd: Int,
        is_nph: Int,
        is_npts: Int,
        is_npas: Int,
        nilai_kd: Int
    ) {
        val client = ApiConfig.getApiService().createNilaiPengetahuan(
            id_siswa,
            id_mapel,
            id_tema,
            id_kd,
            is_nph,
            is_npts,
            is_npas,
            nilai_kd
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

    fun updateNilaiPengetahuan(
        id_siswa: Int,
        id_mapel: Int,
        id_tema: Int,
        id_kd: Int,
        is_nph: Int,
        is_npts: Int,
        is_npas: Int,
        nilai_kd: Int
    ) {
        val client = ApiConfig.getApiService().updateNilaiPengetahuan(
            id_siswa,
            id_mapel,
            id_tema,
            id_kd,
            is_nph,
            is_npts,
            is_npas,
            nilai_kd
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

    fun getResponseCreate(): LiveData<GeneralResponse> = responseCreate
    fun getResponseUpdate(): LiveData<GeneralResponse> = responseUpdate
}