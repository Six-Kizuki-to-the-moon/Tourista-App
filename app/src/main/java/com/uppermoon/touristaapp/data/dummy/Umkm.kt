package com.uppermoon.touristaapp.data.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Umkm(
    val umkmName: String,
    val umkmOwner: String,
    val umkmPhoto: String
): Parcelable
