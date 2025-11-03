package com.example.newmovieapp.data.remote

import android.net.Network
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.newmovieapp.data.domain.repository.PostsLocalSource
import com.example.newmovieapp.data.domain.repository.PostsRemoteSource
import com.example.newmovieapp.data.local.dataSource.PostsLocalDataSource
import com.example.newmovieapp.data.local.model.PostEntity
import com.example.newmovieapp.data.mapper.toPostEntity
import com.example.newmovieapp.data.remote.dataSource.PostsRemoteDataSource
import kotlinx.coroutines.delay
import org.chromium.net.NetworkException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
@OptIn(ExperimentalPagingApi::class)
class PostsRemoteMediator @Inject constructor(
    private val remoteSource: PostsRemoteSource,
    private val localSource: PostsLocalSource
) : RemoteMediator<Int, PostEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PostEntity>): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else (lastItem.id / state.config.pageSize) + 1
                }
            }
            Log.e("NetworkConnectionInterceptor", "mediator before get ")

            val remotePosts = remoteSource.getPosts(page, state.config.pageSize)
            Log.e("NetworkConnectionInterceptor", "mediator after get")
            if (remotePosts.isNotEmpty()) {
                localSource.insertPosts(remotePosts.map { it.toPostEntity() })
            }
            Log.e("NetworkConnectionInterceptor", "mediator after insert")
            return MediatorResult.Success(endOfPaginationReached = remotePosts.isEmpty())
        } catch (e: IOException) {
//            delay(30000)
            Log.e("NetworkConnectionInterceptor", "Mediator \n IOException", e)
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("PostsRemoteMediator", "HttpException", e)
            return MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.e("PostsRemoteMediator", "Exception", e)
            return MediatorResult.Error(e)
        }
    }
}
