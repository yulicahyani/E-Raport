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
    ): Call<GeneralResponse>

    //SISWA
    @GET("eraport.php?function=getSiswaSekolah")
    fun getSiswaSekolah(
        @Query("id_user") id_user: Int
    ):Call<SiswaResponse>

    //MAPEL
    @GET("eraport.php?function=getMapel")
    fun getMapel(): Call<MapelResponse>

    //NILAI SPIRITUAL
    @GET("eraport.php?function=getSikapSpritualSiswa")
    fun getSikapSpiritualSiswa(
        @Query("id_user") id_user: Int
    ): Call<ListNilaiSikapResponse>

    @GET("eraport.php?function=getDetailSikapSpritualSiswa")
    fun getDetailSikapSpiritualSiswa(
        @Query("id_siswa") id_siswa: Int
    ): Call<DetailNilaiSikapResponse>

    @FormUrlEncoded
    @POST("eraport.php?function=createNilaiSpiritual")
    fun createNilaiSpiritual(
        @Field("id_siswa") id_siswa: Int,
        @Field("ketaatan_beribadah") ketaatan_beribadah: String,
        @Field("berprilaku_bersyukur") berprilaku_bersyukur: String,
        @Field("berdoa") berdoa: String,
        @Field("toleransi") toleransi: String
    ): Call<GeneralResponse>

    @FormUrlEncoded
    @POST("eraport.php?function=updateNilaiSpiritual")
    fun updateNilaiSpiritual(
        @Field("id_siswa") id_siswa: Int,
        @Field("ketaatan_beribadah") ketaatan_beribadah: String,
        @Field("berprilaku_bersyukur") berprilaku_bersyukur: String,
        @Field("berdoa") berdoa: String,
        @Field("toleransi") toleransi: String
    ): Call<GeneralResponse>

    //NILAI SOSIAL
    @GET("eraport.php?function=getSikapSosialSiswa")
    fun getSikapSosialSiswa(
        @Query("id_user") id_user: Int
    ): Call<ListNilaiSikapResponse>

    @GET("eraport.php?function=getDetailSikapSosialSiswa")
    fun getDetailSikapSosialSiswa(
        @Query("id_siswa") id_siswa: Int
    ): Call<DetailNilaiSikapSosialResponse>

    @FormUrlEncoded
    @POST("eraport.php?function=createNilaiSosial")
    fun createNilaiSosial(
        @Field("id_siswa") id_siswa: Int,
        @Field("jujur") jujur: String,
        @Field("disiplin") disiplin: String,
        @Field("tanggung_jawab") tanggung_jawab: String,
        @Field("santun") santun: String,
        @Field("peduli") peduli: String,
        @Field("percaya_diri") percaya_diri: String
    ): Call<GeneralResponse>

    @FormUrlEncoded
    @POST("eraport.php?function=updateNilaiSosial")
    fun updateNilaiSosial(
        @Field("id_siswa") id_siswa: Int,
        @Field("jujur") jujur: String,
        @Field("disiplin") disiplin: String,
        @Field("tanggung_jawab") tanggung_jawab: String,
        @Field("santun") santun: String,
        @Field("peduli") peduli: String,
        @Field("percaya_diri") percaya_diri: String
    ): Call<GeneralResponse>



}