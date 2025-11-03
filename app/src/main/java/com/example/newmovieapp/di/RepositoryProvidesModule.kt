package com.example.newmovieapp.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.example.newmovieapp.data.domain.repository.PostRepository
import com.example.newmovieapp.data.domain.repository.PostsLocalSource
import com.example.newmovieapp.data.domain.repository.PostsRemoteSource
import com.example.newmovieapp.data.local.model.PostEntity
import com.example.newmovieapp.data.remote.PostsRemoteMediator
import com.example.newmovieapp.data.repository.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvidesModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    fun providePostsRemoteMediator( // ❌ Removed @Singleton
        remoteSource: PostsRemoteSource,
        localSource: PostsLocalSource
    ): RemoteMediator<Int, PostEntity> {
        return PostsRemoteMediator(remoteSource, localSource)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    fun providePostRepository(
        remoteSource: PostsRemoteSource,
        localSource: PostsLocalSource,
        remoteMediator: RemoteMediator<Int, PostEntity>
    ): PostRepository {
        return PostRepositoryImpl(remoteSource, localSource, remoteMediator)
    }
}

