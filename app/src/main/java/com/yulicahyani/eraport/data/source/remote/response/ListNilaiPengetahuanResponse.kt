package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListNilaiPengetahuanResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("nilaipengetahuan")
    val nilaipengetahuan: List<ResultsListNilaiPengetahuan>
)

data class ResultsListNilaiPengetahuan(
    @field:SerializedName("id_tema")
    val id_tema: String,

    @field:SerializedName("nama_tema")
    val nama_tema: String,

    @field:SerializedName("id_kd")
    val id_kd: String,

    @field:SerializedName("kode_kd")
    val kode_kd: String,

    @field:SerializedName("deskripsi_kd")
    val deskripsi_kd: String,

    @field:SerializedName("id_siswa")
    val id_siswa: String,

    @field:SerializedName("nama_siswa")
    val nama_siswa: String,

    @field:SerializedName("id_mapel")
    val id_mapel: String,

    @field:SerializedName("is_nph")
    val is_nph: String,

    @field:SerializedName("is_npts")
    val is_npts: String,

    @field:SerializedName("is_npas")
    val is_npas: String,

    @field:SerializedName("nilai_kd")
    val nilai_kd: String,
)
