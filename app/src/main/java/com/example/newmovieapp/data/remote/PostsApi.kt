package com.example.newmovieapp.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(@Query("_page") page: Int, @Query("_limit") limit: Int): List<PostDto>


    @POST("posts")
    suspend fun createPost(@Body post: PostDto): PostDto

    object BASE_URL{
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    }
}