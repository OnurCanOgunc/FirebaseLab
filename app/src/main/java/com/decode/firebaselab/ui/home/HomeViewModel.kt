package com.decode.firebaselab.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.firebaselab.data.model.Artist
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val _artist = MutableStateFlow<List<Artist>>(emptyList())
    val artist = _artist.asStateFlow()

    private var db: FirebaseFirestore = Firebase.firestore

    init {
        //loadData()
        getArtists()
    }

    private fun getArtists() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getAllArtists()
            }
            _artist.value = result
        }
    }

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
            Log.e("HomeViewModel", "Error getting artists", e)
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