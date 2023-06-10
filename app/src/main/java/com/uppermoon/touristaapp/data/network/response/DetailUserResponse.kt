package com.uppermoon.touristaapp.data.network.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("photo_profile")
	val photoProfile: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("user_lat")
	val userLat: Any? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("age")
	val age: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("user_lot")
	val userLot: Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)