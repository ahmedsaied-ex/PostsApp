package com.example.newmovieapp.data.mapper

import com.example.newmovieapp.data.local.model.PostEntity
import com.example.newmovieapp.data.remote.PostDto

fun PostDto.toPostEntity(): PostEntity{
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}


fun PostEntity.toPostDto(): PostDto{
    return PostDto(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}
