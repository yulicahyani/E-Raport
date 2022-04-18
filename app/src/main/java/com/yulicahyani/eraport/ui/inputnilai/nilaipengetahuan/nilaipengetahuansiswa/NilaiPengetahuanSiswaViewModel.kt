package com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan.nilaipengetahuansiswa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.ListNilaiPengetahuanResponse
import com.yulicahyani.eraport.data.source.remote.response.ResultsListNilaiPengetahuan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NilaiPengetahuanSiswaViewModel : ViewModel() {

    val listNilaiPengetahuan = MutableLiveData<ArrayList<ResultsListNilaiPengetahuan>>()

    fun findNilaiPengetahuanSiswa(id_siswa: Int, id_mapel: Int, kategori_mapel: String, is_nph: Int, is_npts: Int, is_npas: Int){
        val client = ApiConfig.getApiService().getNilaiPengetahuanSiswa(id_siswa, id_mapel, "pengetahuan", is_nph, is_npts, is_npas)
        client.enqueue(object : Callback <ListNilaiPengetahuanResponse> {
            override fun onResponse(
                call: Call<ListNilaiPengetahuanResponse>,
                response: Response<ListNilaiPengetahuanResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        listNilaiPengetahuan.postValue(response.body()?.nilaipengetahuan as ArrayList<ResultsListNilaiPengetahuan>?)
                    }
                }
            }

            override fun onFailure(call: Call<ListNilaiPengetahuanResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getNilaiPengetahuanSiswa(): LiveData<ArrayList<ResultsListNilaiPengetahuan>> = listNilaiPengetahuan
}