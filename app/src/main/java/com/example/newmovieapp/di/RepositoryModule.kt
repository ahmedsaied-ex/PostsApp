package com.example.newmovieapp.di

import com.example.newmovieapp.data.domain.repository.PostRepository
import com.example.newmovieapp.data.domain.repository.PostsLocalSource
import com.example.newmovieapp.data.domain.repository.PostsRemoteSource
import com.example.newmovieapp.data.local.PostDao
import com.example.newmovieapp.data.local.dataSource.PostsLocalDataSource
import com.example.newmovieapp.data.remote.PostsApi
import com.example.newmovieapp.data.remote.dataSource.PostsRemoteDataSource
import com.example.newmovieapp.data.repository.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DataSourceBindsModule {

        @Binds
        @Singleton
        abstract fun bindPostsRemoteSource(
            impl: PostsRemoteDataSource
        ): PostsRemoteSource

        @Binds
        @Singleton
        abstract fun bindPostsLocalSource(
            impl: PostsLocalDataSource
        ): PostsLocalSource
    }
}
