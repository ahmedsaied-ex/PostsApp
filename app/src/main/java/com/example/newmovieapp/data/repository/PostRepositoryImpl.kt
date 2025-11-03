package com.example.newmovieapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newmovieapp.common.Constants
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.data.domain.repository.PostRepository
import com.example.newmovieapp.data.local.dataSource.PostsLocalDataSource
import com.example.newmovieapp.data.local.model.PostEntity
import com.example.newmovieapp.data.mapper.toPostDto
import com.example.newmovieapp.data.mapper.toPostEntity
import com.example.newmovieapp.data.remote.PostsRemoteMediator
import com.example.newmovieapp.data.remote.dataSource.PostsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val remoteDataSource: PostsRemoteDataSource,
    private val localDataSource: PostsLocalDataSource
) : PostRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedPosts(): Pager<Int, PostEntity> {
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGES, enablePlaceholders = false),
            pagingSourceFactory = { localDataSource.getAllPosts() },
            remoteMediator = PostsRemoteMediator(remoteDataSource, localDataSource)
        )
    }

    override suspend fun createPost(post: PostEntity): Flow<Resource<PostEntity>> = flow {
        emit(Resource.Loading())

        try {
            // Create post on server
            val response = remoteDataSource.createPost(post.toPostDto())
            
            // to add it by (-ve )value to by in the first row :)
            val savedPost = response.toPostEntity().copy(id =((System.currentTimeMillis() % Int.MAX_VALUE).toInt()) * -1 )
            localDataSource.insertPost(savedPost)

            emit(Resource.Success(savedPost))
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.localizedMessage ?: "check your connection"}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Server error: ${e.code()} ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.localizedMessage ?: "unknown error"}"))
        }
    }
}