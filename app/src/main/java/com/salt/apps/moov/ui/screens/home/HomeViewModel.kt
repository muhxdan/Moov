package com.salt.apps.moov.ui.screens.home

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
class HomeViewModel @Inject constructor(private val moovRepository: MoovRepository) : ViewModel() {

    private val _popularMoviesState = MutableStateFlow<Resource<List<Moov>>>(Resource.Loading())
    val popularMoviesState: StateFlow<Resource<List<Moov>>> = _popularMoviesState

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            moovRepository.getPopularMoovs().collect { result ->
                _popularMoviesState.value = result
            }
        }
    }
}