package com.uppermoon.touristaapp.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("age")
	val age: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("photo_profile")
	val photoProfile: String? = null,

	@field:SerializedName("user_lat")
	val userLat: String? = null,

	@field:SerializedName("user_lot")
	val userLot: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
): Parcelable