package com.example.newmovieapp.data.domain.use_case

import androidx.paging.PagingData
import com.example.newmovieapp.data.domain.repository.PostRepository
import com.example.newmovieapp.data.local.model.PostEntity
import com.example.newmovieapp.data.remote.PostDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(  private val repository: PostRepository) {
    operator fun invoke(): Flow<PagingData<PostEntity>> {
        return repository.getPagedPosts().flow
    }
}