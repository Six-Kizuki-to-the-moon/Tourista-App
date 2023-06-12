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

    @GET("users/{id}")
    suspend fun getUsersById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DetailUserResponse

    @Multipart
    @POST("users/createProfile")
    suspend fun createProfile(
        @Part file: MultipartBody.Part,
        @Field("name") name: RequestBody,
        @Field("age") age: RequestBody,
        @Field("phone_number") phoneNumber: RequestBody,
        @Field("address") address: RequestBody,
        @Field("user_lat") lat: Int,
        @Field("user_lon") lon: Int,
    ): DetailUserResponse

//    Method for destination

    @GET("/destination")
    suspend fun getPopularDestination(): DestinationResponse

    @FormUrlEncoded
    @POST("recommendContentBased")
    suspend fun userRecommendation(
        @Field("user_id") id: Int,
        @Field("category") category: String,
        @Field("city") city: String,
        @Field("price") price: Int
    ): RecommendationContentResponse
}