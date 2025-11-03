package com.example.newmovieapp.data.domain.repository

import com.example.newmovieapp.data.remote.PostDto

interface PostsRemoteSource {
    suspend fun getPosts(page: Int, limit: Int): List<PostDto>
    suspend fun createPost(post: PostDto): PostDto
}
