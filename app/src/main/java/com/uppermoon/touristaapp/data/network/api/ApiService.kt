package com.uppermoon.touristaapp.data.network.api

import com.uppermoon.touristaapp.data.network.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun registerUser(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("users/{username}")
    suspend fun getUsersByUsername(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): DetailUserResponse

    @Multipart
    @POST("users/createProfile")
    suspend fun createProfile(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody,
        @Part("address") address: RequestBody,
        @Part("user_lat") lat: RequestBody,
        @Part("user_lon") lon: RequestBody,
    ): DetailUserResponse

//    Method for destination

    @GET("/destination")
    suspend fun getPopularDestination(): DestinationResponse

    @FormUrlEncoded
    @POST("ml/recommendCollab")
    suspend fun nearYou(
        @Field("user_id") id: Int,
        @Field("user_lat") userLat: Float,
        @Field("user_lon") userLon: Float,
    ): SimiliarItemResponse

    @FormUrlEncoded
    @POST("ml/recommendContentBased")
    suspend fun userRecommendation(
        @Field("user_id") id: Int,
        @Field("category") category: String,
        @Field("city") city: String,
        @Field("price") price: Int
    ): RecommendationContentResponse

    @GET("trip/detail/{id}")
    suspend fun getTripDetail(
        @Path("user_id") id: Int
    ): TripRecommendationResponse

    @FormUrlEncoded
    @POST("ml/recommendSimilarItem")
    suspend fun similiarItem(
        @Field("destination_name") destinationName: String
    ): RecommendationResponse
}