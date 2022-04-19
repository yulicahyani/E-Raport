package com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.GeneralResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NilaiKeterampilanViewModel: ViewModel() {
    val responseCreate = MutableLiveData<GeneralResponse>()
    val responseUpdate = MutableLiveData<GeneralResponse>()

    fun addNilaiKeterampilan(
        id_siswa: Int,
        id_mapel: Int,
        id_kd: Int,
        id_kt: Int,
        nilai_kt: Int,
    ) {
        val client = ApiConfig.getApiService().createNilaiKeterampilan(
            id_siswa,
            id_mapel,
            id_kd,
            id_kt,
            nilai_kt
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

    fun updateNilaiKeterampilan(
        id_siswa: Int,
        id_mapel: Int,
        id_kd: Int,
        id_kt: Int,
        nilai_kt: Int,
    ) {
        val client = ApiConfig.getApiService().updateNilaiKeterampilan(
            id_siswa,
            id_mapel,
            id_kd,
            id_kt,
            nilai_kt
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