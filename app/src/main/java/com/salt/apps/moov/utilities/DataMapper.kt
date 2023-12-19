package com.salt.apps.moov.utilities

import com.salt.apps.moov.data.model.Moov
import com.salt.apps.moov.data.model.MoovType
import com.salt.apps.moov.data.source.local.entity.MoovEntity
import com.salt.apps.moov.data.source.remote.response.MoovItem

object DataMapper {
    fun mapMoovResponseToEntity(input: List<MoovItem>, moovType: MoovType): List<MoovEntity> =
        input.map {
            MoovEntity(
                id = it.id,
                overview = it.overview,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                originalLanguage = it.originalLanguage,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                moovType = moovType.name,
                title = it.title
            )
        }

    fun mapMoovEntitiesToMoovModel(input: List<MoovEntity>): List<Moov> =
        input.map {
            Moov(
                id = it.id,
                overview = it.overview,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                originalLanguage = it.originalLanguage,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                moovType = it.moovType,
                title = it.title,
                isFavorite = it.isFavorite
            )
        }
}