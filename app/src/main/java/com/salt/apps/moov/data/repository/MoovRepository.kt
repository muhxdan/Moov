package com.salt.apps.moov.data.repository

import com.salt.apps.moov.data.Resource
import com.salt.apps.moov.data.model.Moov
import com.salt.apps.moov.data.model.MoovType
import com.salt.apps.moov.data.moovNetworkBoundResource
import com.salt.apps.moov.data.source.local.LocalDataSource
import com.salt.apps.moov.data.source.remote.RemoteDataSource
import com.salt.apps.moov.utilities.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoovRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) {
    fun getPopularMoovs(): Flow<Resource<List<Moov>>> = moovNetworkBoundResource(
        query = {
            local.getMoovs(MoovType.POPULAR.name).map {
                DataMapper.mapMoovEntitiesToMoovModel(it)
            }
        },
        fetch = {
            remote.getPopularMovies()
        },
        saveFetchResult = {
            val entity = DataMapper.mapMoovResponseToEntity(it, MoovType.POPULAR)
            local.insertMoovs(entity)
        },
        shouldFetch = {
            it.isNullOrEmpty()
        }
    )
}