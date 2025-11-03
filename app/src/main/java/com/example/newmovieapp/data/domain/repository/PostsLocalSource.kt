package com.example.newmovieapp.data.domain.repository

import androidx.paging.PagingSource
import com.example.newmovieapp.data.local.model.PostEntity

interface PostsLocalSource {
    fun getAllPosts(): PagingSource<Int, PostEntity>
    suspend fun insertPosts(posts: List<PostEntity>)
    suspend fun insertPost(post: PostEntity)
}
