package com.example.newmovieapp.data.remote.dataSource

import android.net.http.NetworkException
import com.example.newmovieapp.data.remote.PostDto
import com.example.newmovieapp.data.remote.PostsApi

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostsRemoteDataSource @Inject constructor(
    private val api: PostsApi
) {
    suspend fun getPosts(page: Int, limit: Int): List<PostDto> {
        return try {
            api.getPosts(page, limit)
        } catch (e: IOException) {
            throw IOException("Network error: ${e.localizedMessage ?: "check your connection"}")
        } catch (e: HttpException) {
            throw HttpException(e.response()!!)
        } catch (e: Exception) {
            throw Exception("Unexpected error: ${e.localizedMessage ?: "unknown"}")
        }
    }
    suspend fun createPost(post: PostDto): PostDto {
        return try {
            api.createPost(post)
        } catch (e: IOException) {
            throw IOException("Network error: ${e.localizedMessage ?: "check your connection"}")
        } catch (e: HttpException) {
            throw HttpException(e.response()!!)
        } catch (e: Exception) {
            throw Exception("Unexpected error: ${e.localizedMessage ?: "unknown"}")
        }
    }
}
