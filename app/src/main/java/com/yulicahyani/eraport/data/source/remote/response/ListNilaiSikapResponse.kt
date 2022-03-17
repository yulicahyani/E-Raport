package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListNilaiSikapResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("nilaisikap")
    val nilaisikap: List<ResultsListNilaiSikap>
)

data class ResultsListNilaiSikap(
    @field:SerializedName("id_siswa")
    val id_siswa: String,

    @field:SerializedName("nama_siswa")
    val nama_siswa: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,
)