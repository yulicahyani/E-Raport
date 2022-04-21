package com.yulicahyani.eraport.ui.datautama.datauser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataUserViewModel : ViewModel(){
    val listUser = MutableLiveData<ArrayList<ResultsAllUser>>()
    val responseCreate = MutableLiveData<GeneralResponse>()
    val responseUpdate = MutableLiveData<GeneralResponse>()
    val responseDelete = MutableLiveData<GeneralResponse>()

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

    fun addDataUser(
        id_sekolah: Int,
        email: String,
        username: String,
        password: String,
        firstname: String,
        lastname: String,
        role: String
    ) {
        val client = ApiConfig.getApiService().createUser(
            id_sekolah,
            email,
            username,
            password,
            firstname,
            lastname,
            role
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

    fun updateDataUser(
        id_user: Int,
        id_sekolah: Int,
        email: String,
        username: String,
        password: String,
        firstname: String,
        lastname: String,
        role: String
    ) {
        val client = ApiConfig.getApiService().updateUser(
            id_user,
            id_sekolah,
            email,
            username,
            password,
            firstname,
            lastname,
            role
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

    fun deleteDataUser(id_user: Int) {
        val client = ApiConfig.getApiService().deleteUser(
            id_user
        )
        client.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if (response.isSuccessful) {
                    responseDelete.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getResponseDelete(): LiveData<GeneralResponse> = responseDelete
    fun getResponseUpdate(): LiveData<GeneralResponse> = responseUpdate
    fun getResponseCreate(): LiveData<GeneralResponse> = responseCreate
    fun getAllUser(): LiveData<ArrayList<ResultsAllUser>> = listUser
}