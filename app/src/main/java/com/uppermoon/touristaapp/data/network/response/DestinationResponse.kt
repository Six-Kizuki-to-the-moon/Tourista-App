package com.uppermoon.touristaapp.data.network.response

import com.google.gson.annotations.SerializedName

class DestinationResponse : ArrayList<DestinationResponseItem>()

data class DestinationResponseItem(

	@field:SerializedName("name_wisata")
	val nameWisata: String,

	@field:SerializedName("destination_photo")
	val destinationPhoto: String,

	@field:SerializedName("coordinate")
	val coordinate: String,

	@field:SerializedName("time_minutes")
	val timeMinutes: Int,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("description_wisata")
	val descriptionWisata: String,

	@field:SerializedName("rating")
	val rating: Float,

	@field:SerializedName("destination_lat")
	val destinationLat: Any,

	@field:SerializedName("destination_long")
	val destinationLong: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("category")
	val category: String
)
