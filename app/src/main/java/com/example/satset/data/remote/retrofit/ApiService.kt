package com.example.satset.data.remote.retrofit

import com.example.satset.data.remote.response.ads.AdsResponse
import com.example.satset.data.remote.response.history.HistoryResponse
import com.example.satset.data.remote.response.LoginResponse
import com.example.satset.data.remote.response.ProfileResponse
import com.example.satset.data.remote.response.RegisterResponse
import com.example.satset.data.remote.response.queue.ViewQueueResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

   @POST("login")
   @FormUrlEncoded
   suspend fun login(
   @Field("email") email: String,
      @Field("password") password: String
   ): LoginResponse


    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ): ProfileResponse
    @GET("ads")
    suspend fun getAds(
        @Header("Authorization") token: String,
    ): AdsResponse

    @POST("queue/join")
    @FormUrlEncoded
    suspend fun join(
        @Header("Authorization") token: String,
        @Field("event_code") eventCode: String
    ): ViewQueueResponse

    @GET("queue/my-queue")
    suspend fun getQueue(
        @Header("Authorization") token: String,
    ): ViewQueueResponse

    @GET("queue/history")
    suspend fun getHistory(
        @Header("Authorization") token: String,
    ): HistoryResponse
}