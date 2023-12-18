package com.salt.apps.moov.data.repository

import com.salt.apps.moov.data.source.local.room.MoovDao
import com.salt.apps.moov.data.source.remote.network.MoovApiService
import javax.inject.Inject

class MoovRepository @Inject constructor(
    private val moovApiService: MoovApiService,
    private val moovDao: MoovDao
)