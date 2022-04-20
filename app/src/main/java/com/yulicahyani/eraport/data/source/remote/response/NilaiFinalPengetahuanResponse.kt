package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NilaiFinalPengetahuanResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("nilaifinalpengetahuan")
    val nilaifinalpengetahuan: List<ResultsNilaiFinalPengetahuan>
)

data class ResultsNilaiFinalPengetahuan(
    @field:SerializedName("id_mapel")
    val id_mapel: String,

    @field:SerializedName("nama_mapel")
    val nama_mapel: String,

    @field:SerializedName("id_siswa")
    val id_siswa: String,

    @field:SerializedName("semester")
    val semester: String,

    @field:SerializedName("tahun_ajaran")
    val tahun_ajaran: String,

    @field:SerializedName("nilai_akhir")
    val nilai_akhir: String,

    @field:SerializedName("predikat")
    val predikat: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,
)