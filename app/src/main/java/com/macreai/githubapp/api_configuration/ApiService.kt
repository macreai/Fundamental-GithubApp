package com.macreai.githubapp.api_configuration

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //Mengambil list user
    @GET("/search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") q: String
    ): Call<UserResponse>

    //Mengambil detail user
    @GET("/users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    //Mengambil list following
    @GET("/users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    //Mengambil list followers
    @GET("/users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}