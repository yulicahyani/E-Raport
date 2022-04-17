package com.yulicahyani.eraport.ui.raport

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.ResultsSiswa
import com.yulicahyani.eraport.data.source.remote.response.SiswaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSiswaRaportViewModel : ViewModel() {
    val listSiswa = MutableLiveData<ArrayList<ResultsSiswa>>()

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

    fun getSiswaSekolah(): LiveData<ArrayList<ResultsSiswa>> = listSiswa
}