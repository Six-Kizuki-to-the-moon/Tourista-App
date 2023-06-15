package com.uppermoon.touristaapp.data.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recresult(
    val recresultName: String,
    val recresultType: String,
    val recresultPhoto: String
): Parcelable
