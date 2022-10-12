package com.yulicahyani.eraport.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.MapelResponse
import com.yulicahyani.eraport.data.source.remote.response.SiswaResponse
import com.yulicahyani.eraport.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {
    val countMapel = MutableLiveData<String>()
    val countUser = MutableLiveData<String>()
    val countSiswa = MutableLiveData<String>()

    fun calculateCountMapel(){
        val  client = ApiConfig.getApiService().getMapel()
        client.enqueue(object : Callback<MapelResponse>{
            override fun onResponse(call: Call<MapelResponse>, response: Response<MapelResponse>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                       countMapel.postValue(response.body()?.mapels?.size.toString())
                    }
                }
            }
            override fun onFailure(call: Call<MapelResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun calculateCountUser(){
        val client = ApiConfig.getApiService().getAllUser()
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        countUser.postValue(response.body()?.user?.size.toString())
                    }
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun calculateCountSiswa(id_sekolah : Int){
        val client = ApiConfig.getApiService().getSiswaSekolah(id_sekolah)
        client.enqueue(object : Callback<SiswaResponse> {
            override fun onResponse(call: Call<SiswaResponse>, response: Response<SiswaResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 1){
                        countUser.postValue(response.body()?.siswa?.size.toString())
                    }
                }
            }

            override fun onFailure(call: Call<SiswaResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getCountMapel(): LiveData<String> = countMapel;
    fun getCountUser(): LiveData<String> = countUser;
    fun getCountSiswa(): LiveData<String> = countSiswa;


}