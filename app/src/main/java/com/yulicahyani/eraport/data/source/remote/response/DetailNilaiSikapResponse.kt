package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailNilaiSikapResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("nilai")
    val nilai: List<ResultsDetailNilaiSikap>
)

data class ResultsDetailNilaiSikap(
    @field:SerializedName("id_spiritual")
    val id_spiritual: String,

    @field:SerializedName("id_siswa")
    val id_siswa: String,

    @field:SerializedName("semester")
    val semester: String,

    @field:SerializedName("tahun_ajaran")
    val tahun_ajaran: String,

    @field:SerializedName("ketaatan_beribadah")
    val ketaatan_beribadah: String,

    @field:SerializedName("berprilaku_bersyukur")
    val berprilaku_bersyukur: String,

    @field:SerializedName("berdoa")
    val berdoa: String,

    @field:SerializedName("toleransi")
    val toleransi: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,
)

data class DetailNilaiSikapSosialResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("nilai")
    val nilai: List<ResultsDetailNilaiSikapSosial>
)

data class ResultsDetailNilaiSikapSosial(
    @field:SerializedName("id_sosial")
    val id_spiritual: String,

    @field:SerializedName("id_siswa")
    val id_siswa: String,

    @field:SerializedName("semester")
    val semester: String,

    @field:SerializedName("tahun_ajaran")
    val tahun_ajaran: String,

    @field:SerializedName("jujur")
    val jujur: String,

    @field:SerializedName("disiplin")
    val disiplin: String,

    @field:SerializedName("tanggung_jawab")
    val tanggung_jawab: String,

    @field:SerializedName("santun")
    val santun: String,

    @field:SerializedName("peduli")
    val peduli: String,

    @field:SerializedName("percaya_diri")
    val percaya_diri: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,
)