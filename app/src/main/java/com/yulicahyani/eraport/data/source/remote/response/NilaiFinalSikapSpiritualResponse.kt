package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NilaiFinalSikapSpiritualResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("sikapspiritual")
    val sikapspiritual: List<ResultsNilaiFinalSikapSpiritual>
)

data class ResultsNilaiFinalSikapSpiritual(
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