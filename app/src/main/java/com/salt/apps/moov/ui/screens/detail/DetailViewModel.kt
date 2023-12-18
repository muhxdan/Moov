package com.salt.apps.moov.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.salt.apps.moov.data.repository.MoovRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val moovRepository: MoovRepository) :
    ViewModel()