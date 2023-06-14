package com.uppermoon.touristaapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val id: Int,
    val name: String,
): Parcelable
