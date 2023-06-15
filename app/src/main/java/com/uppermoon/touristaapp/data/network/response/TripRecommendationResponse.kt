package com.uppermoon.touristaapp.data.network.response

import com.google.gson.annotations.SerializedName

data class TripRecommendationResponse(

	@field:SerializedName("TripRecommendationResponse")
	val tripRecommendationResponse: List<TripRecommendationResponseItem>
)

data class UserProfile(

	@field:SerializedName("name")
	val name: String
)

data class Trip(

	@field:SerializedName("trip_name")
	val tripName: String
)

data class TripRecommendationResponseItem(

	@field:SerializedName("name_wisata")
	val nameWisata: String,

	@field:SerializedName("createdAt")
	val createdAt: Any,

	@field:SerializedName("trip")
	val trip: Trip,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("visited")
	val visited: Boolean,

	@field:SerializedName("destination")
	val destination: DestinationResponseItem,

	@field:SerializedName("trip_name_type")
	val tripNameType: String,

	@field:SerializedName("user_profile")
	val userProfile: UserProfile,

	@field:SerializedName("updatedAt")
	val updatedAt: Any
)
