package com.example.newmovieapp.data.local.dataSource

import androidx.paging.PagingSource
import com.example.newmovieapp.data.domain.repository.PostsLocalSource
import com.example.newmovieapp.data.local.PostDao
import com.example.newmovieapp.data.local.model.PostEntity
import javax.inject.Inject

class PostsLocalDataSource @Inject constructor(
    private val postDao: PostDao
) : PostsLocalSource {

    override fun getAllPosts(): PagingSource<Int, PostEntity> = postDao.getAllPosts()

    override suspend fun insertPosts(posts: List<PostEntity>) {
        try {
            postDao.insertPosts(posts)
        } catch (e: Exception) {
            throw Exception("Database error inserting posts: ${e.localizedMessage}")
        }
    }

    override suspend fun insertPost(post: PostEntity) {
        try {
            postDao.insertPost(post)
        } catch (e: Exception) {
            throw Exception("Database error inserting post: ${e.localizedMessage}")
        }
    }
}

