package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SiswaResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("siswa")
    val siswa: List<ResultsSiswa>
)

data class ResultsSiswa(
    @field:SerializedName("id_siswa")
    val id_siswa: String,

    @field:SerializedName("id_sekolah")
    val id_sekolah: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("nis")
    val nis: String,

    @field:SerializedName("nisn")
    val nisn: String,

    @field:SerializedName("nama_siswa")
    val nama_siswa: String,

    @field:SerializedName("nama_panggilan")
    val nama_panggilan: String,

    @field:SerializedName("ttl")
    val ttl: String,

    @field:SerializedName("jenis_kelamin")
    val jenis_kelamin: String,

    @field:SerializedName("agama")
    val agama: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("kelas")
    val kelas: String,

    @field:SerializedName("semester")
    val semester: String,

    @field:SerializedName("tahun_ajaran")
    val tahun_ajaran: String,
)
