package com.example.newmovieapp.di

import com.example.newmovieapp.data.domain.repository.PostRepository
import com.example.newmovieapp.data.local.PostDao
import com.example.newmovieapp.data.local.dataSource.PostsLocalDataSource
import com.example.newmovieapp.data.remote.PostsApi
import com.example.newmovieapp.data.remote.dataSource.PostsRemoteDataSource
import com.example.newmovieapp.data.repository.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: PostsApi): PostsRemoteDataSource {
        return PostsRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: PostDao): PostsLocalDataSource {
        return PostsLocalDataSource(dao)
    }

    @Provides
    @Singleton
    fun providePostRepository(
        remoteDataSource: PostsRemoteDataSource,
        localDataSource: PostsLocalDataSource
    ): PostRepository {
        return PostRepositoryImpl(remoteDataSource, localDataSource)
    }
}
