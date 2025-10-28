package com.example.newmovieapp.data.domain.use_case

import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.data.domain.repository.PostRepository
import com.example.newmovieapp.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(post: PostEntity): Flow<Resource<PostEntity>> {
        return repository.createPost(post)
    }
}
