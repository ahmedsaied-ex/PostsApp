package com.example.newmovieapp.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.newmovieapp.data.local.dataSource.PostsLocalDataSource
import com.example.newmovieapp.data.local.model.PostEntity
import com.example.newmovieapp.data.mapper.toPostEntity
import com.example.newmovieapp.data.remote.dataSource.PostsRemoteDataSource
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PostsRemoteMediator @Inject constructor(
    private val remoteDataSource: PostsRemoteDataSource,
    private val localDataSource: PostsLocalDataSource
) : RemoteMediator<Int, PostEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else (lastItem.id / state.config.pageSize) + 1
                }
            }

            val posts = remoteDataSource.getPosts(page, state.config.pageSize)
            if (loadType == LoadType.REFRESH) localDataSource.clearAll()
            localDataSource.insertPosts(posts.map { it.toPostEntity() })

            MediatorResult.Success(endOfPaginationReached = posts.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
