package com.example.newmovieapp.data.domain.use_case

import android.util.Log
import androidx.paging.PagingData
import com.example.newmovieapp.data.domain.repository.PostRepository
import com.example.newmovieapp.data.local.model.PostEntity
import com.example.newmovieapp.data.remote.PostDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(  private val repository: PostRepository) {
    operator fun invoke(): Flow<PagingData<PostEntity>> {
        Log.d("NetworkConnectionInterceptor", "invoke: called")
        return repository.getPagedPosts().flow
    }
}