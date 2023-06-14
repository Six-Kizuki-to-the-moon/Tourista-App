package com.uppermoon.touristaapp.data.network.response

import com.google.gson.annotations.SerializedName

class RecommendationResponse: ArrayList<RecommendationsItem>()

data class SimiliarItemResponse(

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem>,

	@field:SerializedName("status")
	val status: String
)

data class RecommendationsItem(

	@field:SerializedName("place_name")
	val placeName: String,

	@field:SerializedName("coordinate")
	val coordinate: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("place_category")
	val placeCategory: String,

	@field:SerializedName("rating")
	val rating: Float,

	@field:SerializedName("destination_photo")
	val destinationPhoto: String,

	@field:SerializedName("time_minutes")
	val timeMinutes: Float,

	@field:SerializedName("price")
	val price: Float,

	@field:SerializedName("description_wisata")
	val descriptionWisata: String,

	@field:SerializedName("destination_lat")
	val destinationLat: Float,

	@field:SerializedName("destination_long")
	val destinationLong: Float,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("category")
	val category: String
)
