package com.decode.firebaselab.data.db

import android.util.Log
import com.decode.firebaselab.data.model.Artist
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DataManager(private val db: FirebaseFirestore) {

    suspend fun getAllArtists(): List<Artist> {
        return try {
            db.collection("artists")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot ->
                    snapshot.toObject(Artist::class.java)
                }
        } catch (e: Exception) {
            Log.e("ArtistRepository", "Error getting artists", e)
            emptyList()
        }
    }

    fun loadData() {
        val songs = listOf(
            mapOf(
                "description" to "Ocean Eyes",
                "image" to "https://picsum.photos/147?random=1",
                "name" to "Billie Eilish"
            ),
            mapOf(
                "description" to "Location",
                "image" to "https://picsum.photos/147?random=2",
                "name" to "Khalid"
            ),
            mapOf(
                "description" to "Sunflower",
                "image" to "https://picsum.photos/147?random=3",
                "name" to "Post Malone & Swae Lee"
            ),
            mapOf(
                "description" to "Circles",
                "image" to "https://picsum.photos/147?random=4",
                "name" to "Post Malone"
            ),
            mapOf(
                "description" to "Someone Like You",
                "image" to "https://picsum.photos/147?random=5",
                "name" to "Adele"
            ),
            mapOf(
                "description" to "Rolling in the Deep",
                "image" to "https://picsum.photos/147?random=6",
                "name" to "Adele"
            ),
            mapOf(
                "description" to "Blinding Lights",
                "image" to "https://picsum.photos/147?random=7",
                "name" to "The Weeknd"
            ),
            mapOf(
                "description" to "Starboy",
                "image" to "https://picsum.photos/147?random=8",
                "name" to "The Weeknd ft. Daft Punk"
            ),
            mapOf(
                "description" to "Believer",
                "image" to "https://picsum.photos/147?random=9",
                "name" to "Imagine Dragons"
            ),
            mapOf(
                "description" to "Radioactive",
                "image" to "https://picsum.photos/147?random=10",
                "name" to "Imagine Dragons"
            )
        )
        for (song in songs) {
            db.collection("artists").add(song)
        }
    }

}