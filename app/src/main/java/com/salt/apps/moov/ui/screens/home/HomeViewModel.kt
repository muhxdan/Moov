package com.salt.apps.moov.ui.screens.home

import androidx.lifecycle.ViewModel
import com.salt.apps.moov.data.repository.MoovRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moovRepository: MoovRepository) : ViewModel()