package com.yulicahyani.eraport.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    var id_user: Int?,
    var id_sekolah: Int?,
    var username: String = "",
    var password: String = "",
    var firstname: String = "",
    var lastname: String = "",
    var role: String = "",
    var nama_sekolah: String = ""
): Parcelable