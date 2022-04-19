package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListNilaiKeterampilanResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("nilaiketerampilan")
    val nilaiketerampilan: List<ResultsListNilaiKeterampilan>
)

data class ResultsListNilaiKeterampilan(
    @field:SerializedName("id_siswa")
    val id_siswa: String,

    @field:SerializedName("nama_siswa")
    val nama_siswa: String,

    @field:SerializedName("id_mapel")
    val id_mapel: String,

    @field:SerializedName("id_kd")
    val id_kd: String,

    @field:SerializedName("kode_kd")
    val kode_kd: String,

    @field:SerializedName("deskripsi_kd")
    val deskripsi_kd: String,

    @field:SerializedName("id_kt")
    val id_kt: String,

    @field:SerializedName("nama_keterampilan")
    val nama_keterampilan: String,

    @field:SerializedName("nilai_kt")
    val nilai_kt: String
)