package com.decode.firebaselab.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.firebaselab.data.db.DataManager
import com.decode.firebaselab.data.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val dataManager: DataManager) : ViewModel() {
    private val _artist = MutableStateFlow<List<Artist>>(emptyList())
    val artist = _artist.asStateFlow()

    init {
        //dataManager.loadData()
        getArtists()
    }

    private fun getArtists() {
        viewModelScope.launch {
            _artist.value = dataManager.getAllArtists()
        }
    }

}