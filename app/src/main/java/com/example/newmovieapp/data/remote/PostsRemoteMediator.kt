package com.example.newmovieapp.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.newmovieapp.data.local.dataSource.PostsLocalDataSource
import com.example.newmovieapp.data.local.model.PostEntity
import com.example.newmovieapp.data.mapper.toPostEntity
import com.example.newmovieapp.data.remote.dataSource.PostsRemoteDataSource
import retrofit2.HttpException
import java.io.IOException
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

            val remotePosts = remoteDataSource.getPosts(page, state.config.pageSize)
            if (remotePosts.isNotEmpty()) {

                localDataSource.insertPosts(remotePosts.map { it.toPostEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = remotePosts.isEmpty())
        } catch (e: IOException) {
            // network failure (e.g. no internet)
            Log.e("PostsRemoteMediator", "IOException",e)
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("PostsRemoteMediator", "HttpException",e)
            MediatorResult.Error(e)
        }catch (e: Exception) {
            Log.e("PostsRemoteMediator", "Exception",e)
            MediatorResult.Error(e)
        }
    }


}
