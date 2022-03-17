package com.yulicahyani.eraport.ui.inputnilai.listnilaisikap

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulicahyani.eraport.data.source.remote.api.ApiConfig
import com.yulicahyani.eraport.data.source.remote.response.ListNilaiSikapResponse
import com.yulicahyani.eraport.data.source.remote.response.ResultsListNilaiSikap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNilaiSikapViewModel : ViewModel() {
    val listNilaiSpiritual = MutableLiveData<ArrayList<ResultsListNilaiSikap>>()
    val listNilaiSosial = MutableLiveData<ArrayList<ResultsListNilaiSikap>>()

    fun findListNilaiSpritual(id_user : Int){
        val client = ApiConfig.getApiService().getSikapSpiritualSiswa(id_user)
        client.enqueue(object : Callback<ListNilaiSikapResponse> {
            override fun onResponse(
                call: Call<ListNilaiSikapResponse>,
                response: Response<ListNilaiSikapResponse>
            ) {
                if(response.isSuccessful){
                    if (response.body()?.status == 1){
                        listNilaiSpiritual.postValue(response.body()?.nilaisikap as ArrayList<ResultsListNilaiSikap>?)
                    }
                }
            }

            override fun onFailure(call: Call<ListNilaiSikapResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun findListNilaiSosial(id_user: Int){
        val client = ApiConfig.getApiService().getSikapSosialSiswa(id_user)
        client.enqueue(object : Callback <ListNilaiSikapResponse> {
            override fun onResponse(
                call: Call<ListNilaiSikapResponse>,
                response: Response<ListNilaiSikapResponse>
            ) {
                if(response.isSuccessful){
                    if (response.body()?.status == 1){
                        listNilaiSosial.postValue(response.body()?.nilaisikap as ArrayList<ResultsListNilaiSikap>?)
                    }
                }
            }

            override fun onFailure(call: Call<ListNilaiSikapResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getSikapSpiritualSiswa(): LiveData<ArrayList<ResultsListNilaiSikap>> = listNilaiSpiritual
    fun getSikapSosialSiswa(): LiveData<ArrayList<ResultsListNilaiSikap>> = listNilaiSosial
}