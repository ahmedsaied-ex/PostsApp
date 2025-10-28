package com.example.newmovieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newmovieapp.data.local.model.PostEntity


@Database(
    entities = [PostEntity::class],
    version = 2
)
abstract class PostDatabase : RoomDatabase() {
    abstract fun dao() : PostDao
}
