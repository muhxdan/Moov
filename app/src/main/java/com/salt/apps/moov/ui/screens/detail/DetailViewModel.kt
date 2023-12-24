package com.salt.apps.moov.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salt.apps.moov.data.Resource
import com.salt.apps.moov.data.model.Moov
import com.salt.apps.moov.data.repository.MoovRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val moovRepository: MoovRepository) :
    ViewModel() {

    private val _moovDetail = MutableStateFlow<Resource<Moov>>(Resource.Loading())
    val moovDetail: StateFlow<Resource<Moov>> get() = _moovDetail

    fun fetchFavoriteMovies(moovId: Int) {
        viewModelScope.launch {
            moovRepository.getMoovById(moovId).collect {
                _moovDetail.value = it
            }
        }
    }

    fun toggleFavorite(movieId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            moovRepository.updateFavoriteMovie(movieId, isFavorite)
        }
    }
}