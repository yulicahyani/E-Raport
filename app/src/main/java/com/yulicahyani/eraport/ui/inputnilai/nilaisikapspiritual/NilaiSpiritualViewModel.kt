package com.yulicahyani.eraport.ui.inputnilai.nilaisikapspiritual

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.DetailNilaiSikapResponse
import com.yulicahyani.eraport.data.source.remote.response.GeneralResponse
import com.yulicahyani.eraport.data.source.remote.response.ResultsDetailNilaiSikap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NilaiSpiritualViewModel : ViewModel() {
    val detailNilaiSpiritual = MutableLiveData<ResultsDetailNilaiSikap>()
    val responseCreate = MutableLiveData<GeneralResponse>()
    val responseUpdate = MutableLiveData<GeneralResponse>()

    fun findDetailNilaiSpiritual(id_siswa: Int) {
        val client = ApiConfig.getApiService().getDetailSikapSpiritualSiswa(id_siswa)
        client.enqueue(object : Callback<DetailNilaiSikapResponse> {
            override fun onResponse(
                call: Call<DetailNilaiSikapResponse>,
                response: Response<DetailNilaiSikapResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 1) {
                        detailNilaiSpiritual.postValue(response.body()?.nilai?.get(0))
                    }
                }
            }

            override fun onFailure(call: Call<DetailNilaiSikapResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun addNilaiSpiritual(
        id_siswa: Int,
        beribadah: String,
        bersyukur: String,
        berdoa: String,
        toleransi: String
    ) {
        val client = ApiConfig.getApiService()
            .createNilaiSpiritual(id_siswa, beribadah, bersyukur, berdoa, toleransi)
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

    fun updateNilaiSpiritual(
        id_siswa: Int,
        beribadah: String,
        bersyukur: String,
        berdoa: String,
        toleransi: String
    ) {
        val client = ApiConfig.getApiService()
            .updateNilaiSpiritual(id_siswa, beribadah, bersyukur, berdoa, toleransi)
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

    fun getDetailSikapSpiritualSiswa(): LiveData<ResultsDetailNilaiSikap> = detailNilaiSpiritual
    fun getResponseCreate(): LiveData<GeneralResponse> = responseCreate
    fun getResponseUpdate(): LiveData<GeneralResponse> = responseUpdate

}