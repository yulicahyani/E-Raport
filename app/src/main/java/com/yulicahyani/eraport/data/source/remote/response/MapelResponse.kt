package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MapelResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("mapels")
    val mapels: List<ResultsMapel>
)

data class ResultsMapel(
    @field:SerializedName("id_mapel")
    val id_mapel: String,

    @field:SerializedName("nama_mapel")
    val nama_mapel: String,

    @field:SerializedName("kkm")
    val kkm: String,

    @field:SerializedName("is_mulok")
    val is_mulok: String
)