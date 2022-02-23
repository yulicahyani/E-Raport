package com.yulicahyani.eraport.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GradeTypeEntity(
    var is_nph : Int?,
    var is_npts : Int?,
    var is_npas : Int?
): Parcelable
