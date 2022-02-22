package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SekolahResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("sekolah")
    val sekolah: List<ResultsSekolah>
)

data class ResultsSekolah(
    @field:SerializedName("id_sekolah")
    val id_sekolah: String,

    @field:SerializedName("nama_sekolah")
    val nama_sekolah: String,

    @field:SerializedName("telp")
    val telp: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("kelurahan")
    val kelurahan: String,

    @field:SerializedName("kecamatan")
    val kecamatan: String,

    @field:SerializedName("kabupaten")
    val kabupaten: String,

    @field:SerializedName("provinsi")
    val provinsi: String,

    @field:SerializedName("logo")
    val logo: String,
)