package com.yulicahyani.eraport.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: List<ResultsAllUser>

)

data class ResultsAllUser(
    @field:SerializedName("id_user")
    val id_user: String,

    @field:SerializedName("id_sekolah")
    val id_sekolah: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("firstname")
    val firstname: String,

    @field:SerializedName("lastname")
    val lastname: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("nama_sekolah")
    val nama_sekolah: String,

    @field:SerializedName("alamat")
    val alamat: String,
)