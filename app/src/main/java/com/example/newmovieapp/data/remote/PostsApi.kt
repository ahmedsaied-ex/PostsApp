package com.example.newmovieapp.data.remote

import com.example.newmovieapp.common.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostsApi {
    @GET(Constants.POST)
    suspend fun getPosts(@Query(Constants.PAGE) page: Int, @Query(Constants.LIMIT) limit: Int): List<PostDto>


    @POST(Constants.POST)
    suspend fun createPost(@Body post: PostDto): PostDto


}