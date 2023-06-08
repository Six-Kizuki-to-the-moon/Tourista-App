package com.uppermoon.touristaapp.data.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val username: String,
    val rating: String,
    val photoUrl: String,
    val review: String
) : Parcelable