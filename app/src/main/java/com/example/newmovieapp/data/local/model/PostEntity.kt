package com.example.newmovieapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val userId: Int,
    val title: String,
    val body: String
)
