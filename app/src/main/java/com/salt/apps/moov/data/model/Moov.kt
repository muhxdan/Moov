package com.salt.apps.moov.data.model

data class Moov(
    var id: Int,
    val overview: String? = null,
    var backdropPath: String? = null,
    var posterPath: String? = null,
    var originalLanguage: String,
    var releaseDate: String? = null,
    var voteAverage: Double? = null,
    var moovType: String? = null,
    var title: String,
    var isFavorite: Boolean = false
)