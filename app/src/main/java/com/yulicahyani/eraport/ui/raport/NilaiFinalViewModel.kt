package com.yulicahyani.eraport.ui.raport

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NilaiFinalViewModel : ViewModel() {
    val nilaiFinalSikapSpiritual = MutableLiveData<ArrayList<ResultsNilaiFinalSikapSpiritual>>()
    val nilaiFinalSikapSosial = MutableLiveData<ArrayList<ResultsNilaiFinalSikapSosial>>()
    val nilaiFinalPengetahuan = MutableLiveData<ArrayList<ResultsNilaiFinalPengetahuan>>()
    val nilaiFinalKeterampilan = MutableLiveData<ArrayList<ResultsNilaiFinalKeterampilan>>()

    fun findNilaiFinalSpiritual(id_siswa: Int) {
        val client = ApiConfig.getApiService().getNilaiAkhirSikapSpritual(id_siswa)
        client.enqueue(object : Callback<NilaiFinalSikapSpiritualResponse> {
            override fun onResponse(
                call: Call<NilaiFinalSikapSpiritualResponse>,
                response: Response<NilaiFinalSikapSpiritualResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 1) {
                        nilaiFinalSikapSpiritual.postValue(response.body()?.sikapspiritual as ArrayList<ResultsNilaiFinalSikapSpiritual>?)
                    }
                }
            }

            override fun onFailure(call: Call<NilaiFinalSikapSpiritualResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun findNilaiFinalSosial(id_siswa: Int) {
        val client = ApiConfig.getApiService().getNilaiAkhirSikapSosial(id_siswa)
        client.enqueue(object : Callback<NilaiFinalSikapSosialResponse> {
            override fun onResponse(
                call: Call<NilaiFinalSikapSosialResponse>,
                response: Response<NilaiFinalSikapSosialResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 1) {
                        nilaiFinalSikapSosial.postValue(response.body()?.sikapsosial as ArrayList<ResultsNilaiFinalSikapSosial>?)
                    }
                }
            }

            override fun onFailure(call: Call<NilaiFinalSikapSosialResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun findNilaiFinalPengetahuan(id_siswa: Int){
        val client = ApiConfig.getApiService().getNilaiFinalPengetahuan(id_siswa)
        client.enqueue(object : Callback <NilaiFinalPengetahuanResponse> {
            override fun onResponse(
                call: Call<NilaiFinalPengetahuanResponse>,
                response: Response<NilaiFinalPengetahuanResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        nilaiFinalPengetahuan.postValue(response.body()?.nilaifinalpengetahuan as ArrayList<ResultsNilaiFinalPengetahuan>?)
                    }
                }
            }

            override fun onFailure(call: Call<NilaiFinalPengetahuanResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun findNilaiFinalKeterampilan(id_siswa: Int){
        val client = ApiConfig.getApiService().getNilaiFinalKeterampilan(id_siswa)
        client.enqueue(object : Callback <NilaiFinalKeterampilanResponse> {
            override fun onResponse(
                call: Call<NilaiFinalKeterampilanResponse>,
                response: Response<NilaiFinalKeterampilanResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        nilaiFinalKeterampilan.postValue(response.body()?.nilaifinalketerampilan as ArrayList<ResultsNilaiFinalKeterampilan>?)
                    }
                }
            }

            override fun onFailure(call: Call<NilaiFinalKeterampilanResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getNilaiAkhirKeterampilan(): LiveData<ArrayList<ResultsNilaiFinalKeterampilan>> = nilaiFinalKeterampilan
    fun getNilaiAkhirPengetahuan(): LiveData<ArrayList<ResultsNilaiFinalPengetahuan>> = nilaiFinalPengetahuan
    fun getNilaiAkhirSikapSosial(): LiveData<ArrayList<ResultsNilaiFinalSikapSosial>> = nilaiFinalSikapSosial
    fun getNilaiAkhirSikapSpiritual(): LiveData<ArrayList<ResultsNilaiFinalSikapSpiritual>> = nilaiFinalSikapSpiritual
}