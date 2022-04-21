package com.yulicahyani.eraport.ui.datautama.datauser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.MapelResponse
import com.yulicahyani.eraport.data.source.remote.response.ResultsAllUser
import com.yulicahyani.eraport.data.source.remote.response.ResultsMapel
import com.yulicahyani.eraport.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataUserViewModel : ViewModel(){
    val listUser = MutableLiveData<ArrayList<ResultsAllUser>>()

    fun findUser(){
        val client = ApiConfig.getApiService().getAllUser()
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        listUser.postValue(response.body()!!.user as ArrayList<ResultsAllUser>?)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getAllUser(): LiveData<ArrayList<ResultsAllUser>> = listUser
}