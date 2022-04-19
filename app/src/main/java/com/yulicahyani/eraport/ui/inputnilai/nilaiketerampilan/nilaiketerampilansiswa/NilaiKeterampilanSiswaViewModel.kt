package com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan.nilaiketerampilansiswa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.ListNilaiKeterampilanResponse
import com.yulicahyani.eraport.data.source.remote.response.ResultsListNilaiKeterampilan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NilaiKeterampilanSiswaViewModel:ViewModel() {
    val listNilaiKeterampilan = MutableLiveData<ArrayList<ResultsListNilaiKeterampilan>>()

    fun findNilaiPengetahuanSiswa(id_siswa: Int, id_mapel: Int){
        val client = ApiConfig.getApiService().getNilaiKeterampilanSiswa(id_siswa, id_mapel)
        client.enqueue(object : Callback<ListNilaiKeterampilanResponse> {
            override fun onResponse(
                call: Call<ListNilaiKeterampilanResponse>,
                response: Response<ListNilaiKeterampilanResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        listNilaiKeterampilan.postValue(response.body()?.nilaiketerampilan as ArrayList<ResultsListNilaiKeterampilan>?)
                    }
                }
            }

            override fun onFailure(call: Call<ListNilaiKeterampilanResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getNilaiKeterampilanSiswa(): LiveData<ArrayList<ResultsListNilaiKeterampilan>> = listNilaiKeterampilan
}