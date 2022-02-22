package com.yulicahyani.eraport.data.source.remote.api

import com.yulicahyani.eraport.data.source.remote.response.LoginResponse
import com.yulicahyani.eraport.data.source.remote.response.SekolahResponse
import com.yulicahyani.eraport.data.source.remote.response.UpdateResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("eraport.php?function=loginUser")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("eraport.php?function=getAllSekolah")
    fun getAllSekolah(): Call<SekolahResponse>

    @FormUrlEncoded
    @POST("eraport.php?function=updateUser")
    fun updateUser(
        @Field("id_user") id_user: Int,
        @Field("id_sekolah") id_sekolah: Int,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("firstname") firsname: String,
        @Field("lastname") lastname: String,
        @Field("role") role: String,
    ): Call<UpdateResponse>
}