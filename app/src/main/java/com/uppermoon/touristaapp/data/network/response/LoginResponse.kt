package com.uppermoon.touristaapp.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("accessToken")
	val accessToken: String
)
