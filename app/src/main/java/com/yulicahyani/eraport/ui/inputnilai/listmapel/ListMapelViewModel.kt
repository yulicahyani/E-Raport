package com.yulicahyani.eraport.ui.inputnilai.listmapel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.MapelResponse
import com.yulicahyani.eraport.data.source.remote.response.ResultsMapel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMapelViewModel : ViewModel(){
    val listMapel = MutableLiveData<ArrayList<ResultsMapel>>()

    fun findMapel(){
        val client = ApiConfig.getApiService().getMapel()
        client.enqueue(object : Callback<MapelResponse> {
            override fun onResponse(call: Call<MapelResponse>, response: Response<MapelResponse>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        listMapel.postValue(response.body()!!.mapels as ArrayList<ResultsMapel>?)
                    }
                }
            }

            override fun onFailure(call: Call<MapelResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getMapel(): LiveData<ArrayList<ResultsMapel>> = listMapel
}