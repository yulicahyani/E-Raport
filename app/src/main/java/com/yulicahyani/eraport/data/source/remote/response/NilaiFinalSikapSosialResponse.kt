package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName


data class NilaiFinalSikapSosialResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("sikapsosial")
    val sikapsosial: List<ResultsNilaiFinalSikapSosial>
)

data class ResultsNilaiFinalSikapSosial(
    @field:SerializedName("id_sosial")
    val id_sosial: String,

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