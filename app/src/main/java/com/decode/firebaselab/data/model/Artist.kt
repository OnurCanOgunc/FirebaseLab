package com.decode.firebaselab.data.model

data class Artist(
    val name: String,
    val description: String,
    val imageUrl: String,
    val songs: List<Song>
)
