package com.uppermoon.touristaapp.data.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination (
    val name: String,
    val city: String,
    val photo: String,
    val description: String
    ): Parcelable