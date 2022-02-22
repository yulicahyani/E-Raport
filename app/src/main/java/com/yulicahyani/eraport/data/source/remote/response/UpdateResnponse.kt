package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,
)