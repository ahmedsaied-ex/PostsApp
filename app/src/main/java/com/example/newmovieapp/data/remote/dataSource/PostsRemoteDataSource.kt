package com.example.newmovieapp.data.remote.dataSource

import android.util.Log
import androidx.compose.animation.core.rememberTransition
import com.example.newmovieapp.data.domain.repository.PostsRemoteSource
import com.example.newmovieapp.data.remote.PostDto
import com.example.newmovieapp.data.remote.PostsApi

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
class PostsRemoteDataSource @Inject constructor(
    private val api: PostsApi
) : PostsRemoteSource {
    override suspend fun getPosts(page: Int, limit: Int): List<PostDto> {
        Log.d("NetworkConnectionInterceptor", "Fetching posts from API")
        return  api.getPosts(page, limit)
    }

    override suspend fun createPost(post: PostDto): PostDto {
        try {
            return api.createPost(post)
        } catch (e: IOException) {
            throw IOException("Network error: ${e.localizedMessage ?: "check your connection"}")
        } catch (e: HttpException) {
            throw HttpException(e.response()!!)
        } catch (e: Exception) {
            throw Exception("Unexpected error: ${e.localizedMessage ?: "unknown"}")
        }
    }
}
