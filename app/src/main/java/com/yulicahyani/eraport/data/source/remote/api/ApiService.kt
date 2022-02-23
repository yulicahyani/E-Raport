package com.yulicahyani.eraport.data.source.remote.api

import com.yulicahyani.eraport.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //LOGIN
    @FormUrlEncoded
    @POST("eraport.php?function=loginUser")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    //SEKOLAH
    @GET("eraport.php?function=getAllSekolah")
    fun getAllSekolah(): Call<SekolahResponse>

    //USER
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

    //SISWA
    @GET("eraport.php?function=getSiswaSekolah")
    fun getSiswaSekolah(
        @Query("id_user") id_user: Int
    ):Call<SiswaResponse>

    //MAPEL
    @GET("eraport.php?function=getMapel")
    fun getMapel(): Call<MapelResponse>
}