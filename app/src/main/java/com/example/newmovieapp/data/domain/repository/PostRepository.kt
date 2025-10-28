package com.example.newmovieapp.data.domain.repository


import androidx.paging.Pager
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow


interface PostRepository {
    fun getPagedPosts(): Pager<Int, PostEntity>
    suspend fun createPost(post: PostEntity): Flow<Resource<PostEntity>>
}

