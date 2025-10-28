package com.example.newmovieapp.di

import com.example.newmovieapp.data.domain.repository.PostRepository
import com.example.newmovieapp.data.domain.use_case.CreatePostUseCase
import com.example.newmovieapp.data.domain.use_case.GetPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPostsUseCase(repository: PostRepository): GetPostsUseCase {
        return GetPostsUseCase(repository)
    }

    @Provides
    fun provideCreatePostUseCase(repository: PostRepository): CreatePostUseCase {
        return CreatePostUseCase(repository)
    }
}
