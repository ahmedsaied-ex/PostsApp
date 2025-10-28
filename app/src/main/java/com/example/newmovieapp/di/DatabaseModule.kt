package com.example.newmovieapp.di

import android.app.Application
import androidx.room.Room
import com.example.newmovieapp.data.local.PostDao
import com.example.newmovieapp.data.local.PostDatabase
import com.example.newmovieapp.data.local.dataSource.PostsLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): PostDatabase {
        return Room.databaseBuilder(
            app, PostDatabase::class.java, "posts_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePostDao(db: PostDatabase): PostDao {
        return db.dao()
    }
}

