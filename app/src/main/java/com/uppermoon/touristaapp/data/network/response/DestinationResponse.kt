package com.uppermoon.touristaapp.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class DestinationResponse : ArrayList<DestinationResponseItem>()

@Parcelize
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
	val destinationLat: String,

	@field:SerializedName("destination_long")
	val destinationLong: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("category")
	val category: String
): Parcelable
