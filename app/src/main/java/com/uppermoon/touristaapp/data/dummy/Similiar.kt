package com.uppermoon.touristaapp.data.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Similiar(
    val similiarName: String,
    val similiarCity: String,
    val similiarPhoto: String,
) : Parcelable